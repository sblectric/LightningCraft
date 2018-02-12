package sblectric.lightningcraft.blocks.base;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.items.blocks.ItemBlockMeta;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.registry.ClientRegistryHelper;

/** A block that has subblocks / metadata */
public abstract class BlockMeta extends BlockLC { 
	
	/** Do some reflection trickery to get non-static variants */
	private static final PropertyInteger DEFAULT = PropertyInteger.create("variant", 0, 1);
	protected int nSubBlocks;
	public boolean sameIcon;
	private JointList<IProperty> variants = new JointList();

	/** A block that has subblocks / metadata */
	public BlockMeta(Block parent, int nSubBlocks, float hardness, float resistance, boolean sameIcon) {
		super(parent, hardness, resistance);
		if(nSubBlocks > 16) throw new IllegalArgumentException("More than 16 metadata states is unsupported!");
		this.nSubBlocks = nSubBlocks;
		this.sameIcon = sameIcon;
		ReflectionHelper.setPrivateValue(Block.class, this, createBlockState(), RefMisc.DEV ? "blockState" : "field_176227_L");
		setDefaultState();
	}
	
	/** Get the properties for this block */
	protected List<IProperty> getProperties() {
		return variants = new JointList().join(PropertyInteger.create("variant", 0, nSubBlocks - 1));
	}
	
	/** Set the default block state */
	protected void setDefaultState() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(variants.get(0), 0));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList list) {
	    for (int i = 0; i < nSubBlocks; i++) {
	        list.add(new ItemStack(this, 1, i));
	    }
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		if(variants == null) {
			return new BlockStateContainer(this, new IProperty[] { DEFAULT });
		} else {
			List<IProperty> list = this.getProperties();
			return new BlockStateContainer(this, list.toArray(new IProperty[list.size()]));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(variants.get(0), meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    int meta = (int)state.getValue(variants.get(0));
	    return meta;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
	    return getMetaFromState(state);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
	    return new ItemStack(this, 1, this.getMetaFromState(state));
	}
	
	@Override
	public Class getItemClass() {
		return ItemBlockMeta.class;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender() {
		JointList<ResourceLocation> names = new JointList();
		Item item = Item.getItemFromBlock(this);
		if(sameIcon) {
			for(int meta = 0; meta < nSubBlocks; meta++) {
				ClientRegistryHelper.registerModel(item, meta, new ModelResourceLocation(this.getRegistryName(), "inventory"));
			}
		} else {
			for(int meta = 0; meta < nSubBlocks; meta++) {
				ClientRegistryHelper.registerModel(item, meta, new ModelResourceLocation(this.getRegistryName() + "_" + meta, "inventory"));
			}
		}
	}

}
