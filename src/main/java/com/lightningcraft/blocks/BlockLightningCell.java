package com.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import com.lightningcraft.gui.LCGuiHandler;
import com.lightningcraft.items.LCItems;
import com.lightningcraft.items.blocks.ItemBlockLightningCell;
import com.lightningcraft.main.LightningCraft;
import com.lightningcraft.ref.Material;
import com.lightningcraft.ref.Metal.MBlock;
import com.lightningcraft.tiles.TileEntityLightningCell;

/** The lightning cell */
public class BlockLightningCell extends BlockContainerLCMeta {
	
	public static final int nCells = MBlock.count;
	
	/** The maximum power this lightning cell can hold. */
	private double[] maxPower;

	/** The lightning cell */
	public BlockLightningCell() {
		super(LCBlocks.metalBlock, nCells, 6.5f, 40.0f);
		maxPower = new double[]{1000, 5000, 20000};
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLightningCell(maxPower[meta % nCells], I18n.translateToLocal(getUnlocalizedName() + "_" + meta + ".name"));
	}
	
	/** Get the max power for a specific blockstate */
	public int getMaxPower(IBlockState state) {
		return (int)maxPower[getMetaFromState(state) % nCells];
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack s, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningCellGui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	/** release any upgrades on block break */
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityLightningCell lpcell = (TileEntityLightningCell)world.getTileEntity(pos);
		if(lpcell.isUpgraded) {
			// release the upgrade
			float f = this.random.nextFloat() * 0.6F + 0.1F;
			float f1 = this.random.nextFloat() * 0.6F + 0.1F;
			float f2 = this.random.nextFloat() * 0.6F + 0.1F;
			float f3 = 0.025F;
			EntityItem e = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(LCItems.material, 1, Material.CELL_UPGRADE));
			e.motionX = this.random.nextGaussian() * f3;
			e.motionY = this.random.nextGaussian() * f3 + 0.1F;
			e.motionZ = this.random.nextGaussian() * f3;
			world.spawnEntityInWorld(e);
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public Class getItemClass() {
		return ItemBlockLightningCell.class;
	}

}
