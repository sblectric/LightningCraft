package sblectric.lightningcraft.blocks;

import net.minecraft.init.Blocks;

/** Wooden planks */
public class BlockPlankLC extends BlockLC {

	public BlockPlankLC() {
		super(Blocks.PLANKS, 2, 15);
		this.setHarvestLevel("axe", -1);
	}
}
