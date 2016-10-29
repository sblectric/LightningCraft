package sblectric.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.blocks.base.BlockContainerLCMeta;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.items.blocks.ItemBlockLightningCell;
import sblectric.lightningcraft.ref.Metal.MBlock;
import sblectric.lightningcraft.tiles.TileEntityLightningCell;

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
	
	@Override
	public Class getItemClass() {
		return ItemBlockLightningCell.class;
	}

}
