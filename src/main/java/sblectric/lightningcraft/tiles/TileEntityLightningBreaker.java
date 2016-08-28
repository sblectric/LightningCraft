package sblectric.lightningcraft.tiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

/** The Lightning block breaker tile entity */
public class TileEntityLightningBreaker extends TileEntityLightningUser {
	
	public static final int breakModTime = 10;
	private static final double breakLE = 0.3;

	// simply break the block in front of it every 0.5s
	@Override
	public void update() {
		if(!worldObj.isRemote && !worldObj.isBlockPowered(pos) && worldObj.getTotalWorldTime() % breakModTime == 0 && 
				this.hasLPCell() && this.canDrawCellPower(breakLE)) {
			BlockPos check = this.pos.offset(EnumFacing.getFront(this.getBlockMetadata()));
			
			// make sure the block is valid and isn't unbreakable
			IBlockState checkState = worldObj.getBlockState(check);
			Block checkBlock = checkState.getBlock();
			if(checkBlock != null && !checkBlock.isAir(checkState, worldObj, check) && checkState.getBlockHardness(worldObj, check) >= 0) {
				List<ItemStack> drops = checkBlock.getDrops(this.worldObj, check, checkState, 0);
	            float chance = ForgeEventFactory.fireBlockHarvesting(drops, this.worldObj, check, 
	            		this.worldObj.getBlockState(check), 0, 1, false, null);
	            if(random.nextFloat() <= chance) {
	            	this.drawCellPower(breakLE);
	            	worldObj.destroyBlock(check, true);
	            }
			}
		}
	}

}
