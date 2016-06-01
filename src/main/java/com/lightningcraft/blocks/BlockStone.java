package com.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/** The class for all of the basic stone block variants in the mod */
public class BlockStone extends BlockMeta {
	
	// number of variants
	public static final int nVariants = 9;

	// blocks
	public static final int THUNDER = 0;
	public static final int THUNDER_BRICK = 1;
	public static final int THUNDER_BRICK_CHISELED = 2;
	public static final int DEMON = 3;
	public static final int DEMON_BRICK = 4;
	public static final int DEMON_BRICK_CHISELED = 5;
	public static final int UNDER = 6;
	public static final int UNDER_BRICK = 7;
	public static final int UNDER_BRICK_CHISELED = 8;
	
	public BlockStone() {
		super(Blocks.STONE, nVariants, 10, 100, false);
	}

}
