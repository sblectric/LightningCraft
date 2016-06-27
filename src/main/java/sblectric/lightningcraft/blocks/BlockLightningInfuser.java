package sblectric.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.items.blocks.ItemBlockRarity;
import sblectric.lightningcraft.main.LightningCraft;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;

/** The lightning infusion table */
public class BlockLightningInfuser extends BlockContainerLC implements IFurnace {
	
	// block property
	private static final PropertyBool lit = PropertyBool.create("active");
	private static boolean keepInventory = false;

	/** The lightning infusion table */
	public BlockLightningInfuser() {
		super(Blocks.FURNACE, 10, 100);
		this.setDefaultState(this.blockState.getBaseState().withProperty(lit, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[]{lit});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLightningInfuser();
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack s, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningInfuserGui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(lit, meta > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(lit) ? 1 : 0;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if(!keepInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean isBurning(IBlockState state, IBlockAccess world, BlockPos pos) {
		try {
			return state.getValue(lit);
		} catch(IllegalArgumentException e) { // bug fix
			return false;
		}
	}
	
	@Override
	public void setBurning(IBlockState state, IBlockAccess world, BlockPos pos, boolean burning) {
        TileEntity tile = world.getTileEntity(pos);
        keepInventory = true;
		((World)world).setBlockState(pos, state.withProperty(lit, burning));
		keepInventory = false;
		tile.validate();
		((World)world).setTileEntity(pos, tile);
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return isBurning(state, world, pos) ? 4 : 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if(isBurning(state, world, pos)) {
			double d0 = (double)pos.getX() + 0.5D;
			double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = (double)pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public Class getItemClass() {
		return ItemBlockRarity.class;
	}
	
	@Override
	public Object[] getItemClassArgs() {
		return new Object[]{EnumRarity.UNCOMMON};
	}

}
