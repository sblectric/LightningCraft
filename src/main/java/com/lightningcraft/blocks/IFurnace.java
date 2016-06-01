package com.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/** Furnace-type blocks implement this interface */
public interface IFurnace {

	/** Is this furnace lit? */
	boolean isBurning(IBlockState state, IBlockAccess world, BlockPos pos);
	
	/** Set the furnace as burning */
	void setBurning(IBlockState state, IBlockAccess world, BlockPos pos, boolean burning);

}
