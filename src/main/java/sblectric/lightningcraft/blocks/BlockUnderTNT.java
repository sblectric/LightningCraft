package sblectric.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.entities.EntityLCTNTPrimed;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.blocks.ItemBlockUnderTNT;
import sblectric.lightningcraft.ref.Material;

/** Underworld TNT (VERY short fuse and strong splosion) */
public class BlockUnderTNT extends BlockTNT implements ILightningCraftBlock {

	public static final int nVariants = 3;
	
	public static final int RAMPART = 0;
	public static final int LIGHTNING = 1;
	public static final int MYSTIC = 2;
	
	private static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, nVariants - 1);

	public BlockUnderTNT() {
		super();
		this.setSoundType(SoundType.SAND);
		this.setHardness(10.0f);
		this.setResistance(5.0f);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList list) {
	    for (int i = 0; i < nVariants; i++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, this.getMetaFromState(state));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
	    return getItem(world, pos, state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{EXPLODE, VARIANT});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, meta % 8).withProperty(EXPLODE, meta / 8 > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT) + (state.getValue(EXPLODE) ? 8 : 0);
	}

	/** Go boom */
	@Override
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
		if (!worldIn.isRemote) {
			if(state.getValue(EXPLODE)) {
				EntityLCTNTPrimed entitytntprimed = new EntityLCTNTPrimed(worldIn, state.getValue(VARIANT), 
						pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
				worldIn.spawnEntity(entitytntprimed);
				worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
						SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	/** Go boom when other things go boom */
	@Override
	public void onBlockExploded(World worldIn, BlockPos pos, Explosion explosionIn) {
		int variant = worldIn.getBlockState(pos).getValue(VARIANT);
		worldIn.setBlockToAir(pos);
		if (!worldIn.isRemote) {
			EntityLCTNTPrimed entitytntprimed = new EntityLCTNTPrimed(worldIn, variant, 
					pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, explosionIn.getExplosivePlacedBy());
			entitytntprimed.fuse = worldIn.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
			worldIn.spawnEntity(entitytntprimed);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		switch(state.getValue(VARIANT)) {
		case RAMPART:
			return LCItems.material;
		default:
			return Item.getItemFromBlock(this);
		}
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		switch(state.getValue(VARIANT)) {
		case RAMPART:
			return Material.UNDER_POWDER_2;
		default:
			return this.getMetaFromState(state);
		}
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {
		switch(state.getValue(VARIANT)) {
		case RAMPART:
			return rand.nextInt(3) + 1;
		default:
			return 1;
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public Class getItemClass() {
		return ItemBlockUnderTNT.class;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender() {
		JointList<ResourceLocation> names = new JointList();
		Item item = Item.getItemFromBlock(this);
		for(int meta = 0; meta < nVariants; meta++) {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(this.getRegistryName() + "_" + meta, "inventory"));
		}
	}

}
