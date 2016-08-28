package sblectric.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.main.LightningCraft;
import sblectric.lightningcraft.tiles.TileEntityLightningMiner;

/** The Lightning Miner (an upgraded breaker, basically) */
public class BlockLightningMiner extends BlockLightningBreaker {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLightningMiner();
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack s, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningMinerGui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	/** This block has an inventory, so bust it open */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
		worldIn.updateComparatorOutputLevel(pos, this);

		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public Object[] getItemClassArgs() {
		return new Object[]{EnumRarity.RARE};
	}

}
