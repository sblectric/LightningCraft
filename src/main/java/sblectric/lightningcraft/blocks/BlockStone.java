package sblectric.lightningcraft.blocks;

import net.minecraft.init.Blocks;

/** The class for all of the basic stone block variants in the mod */
public class BlockStone extends BlockMeta {
	
	// number of types / variants
	public static final int nTypes = 3;
	public static final int nVariants = 15;

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
	
	// fancy blocks
	public static final int THUNDER_BRICK_FANCY = 9;
	public static final int DEMON_BRICK_FANCY = 10;
	public static final int UNDER_BRICK_FANCY = 11;
	public static final int THUNDER_BRICK_FANCY_2 = 12;
	public static final int DEMON_BRICK_FANCY_2 = 13;
	public static final int UNDER_BRICK_FANCY_2 = 14;
	
	public BlockStone() {
		super(Blocks.STONE, nVariants, 10, 100, false);
	}

}
