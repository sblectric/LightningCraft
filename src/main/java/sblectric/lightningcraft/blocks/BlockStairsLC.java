package sblectric.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;

/** Stairs */
public class BlockStairsLC extends BlockStairs implements ILightningCraftBlock {

	public BlockStairsLC(Block block) {
		super(block.getDefaultState());
		this.useNeighborBrightness = true;
	}
	
	public BlockStairsLC() {
		this(LCBlocks.stoneBlock);
	}

}
