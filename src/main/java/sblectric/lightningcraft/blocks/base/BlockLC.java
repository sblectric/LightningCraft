package sblectric.lightningcraft.blocks.base;

import net.minecraft.block.Block;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;

/** A basic LightningCraft block. */
public class BlockLC extends Block implements ILightningCraftBlock {

	/** A basic LightningCraft block. */
	public BlockLC(Block parent, float hardness, float resistance) {
		super(parent.getMaterial(parent.getDefaultState()));
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(parent.getSoundType());
		this.setLightLevel(parent.getLightValue(parent.getDefaultState()));
	}

}
