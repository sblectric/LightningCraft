package com.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

import com.lightningcraft.registry.IRegistryBlock;

/** Stairs */
public class BlockStairsLC extends BlockStairs implements IRegistryBlock {

	public BlockStairsLC(Block block) {
		super(block.getDefaultState());
		this.useNeighborBrightness = true;
	}
	
	public BlockStairsLC() {
		this(LCBlocks.stoneBlock);
	}

}