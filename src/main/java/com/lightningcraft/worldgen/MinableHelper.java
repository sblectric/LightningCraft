package com.lightningcraft.worldgen;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import com.google.common.base.Predicate;

/** A smart block state predicate */
public class MinableHelper implements Predicate<IBlockState> {
	
	private IBlockState state;
	
	public MinableHelper(IBlockState state) {
		this.state = state;
	}

	@Override
	public boolean apply(IBlockState input) {
		if(input.getBlock() == state.getBlock()) {
			for(IProperty p : input.getProperties().keySet()) {
				try {
					if(input.getValue(p) != state.getValue(p)) {
						return false;
					}
				} catch(Exception e) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
