package sblectric.lightningcraft.blocks;

import net.minecraft.init.Blocks;
import sblectric.lightningcraft.blocks.base.BlockMeta;

/** The class for mod light source blocks */
public class BlockLight extends BlockMeta {
	
	// number of variants
	public static final int nVariants = 5;

	// blocks
	public static final int UNDER = 0;
	public static final int UNDER_LAMP = 1;
	public static final int UNDER_LAMP_FANCY = 2;
	
	// fancy variants
	public static final int UNDER_LAMP_FANCY_2 = 3;
	public static final int UNDER_LAMP_FANCY_3 = 4;
	
	public BlockLight() {
		super(Blocks.STONE, nVariants, 10, 100, false);
		this.setLightLevel(1);
	}

}
