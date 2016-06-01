package com.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/** The class for mod light source blocks */
public class BlockLight extends BlockMeta {
	
	// number of variants
	public static final int nVariants = 2;

	// blocks
	public static final int UNDER = 0;
	public static final int UNDER_LAMP = 1;
	
	public BlockLight() {
		super(Blocks.STONE, nVariants, 10, 100, false);
		this.setLightLevel(1);
	}

}
