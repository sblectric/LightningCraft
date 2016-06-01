package com.lightningcraft.blocks;

import net.minecraft.block.Block;

import com.lightningcraft.registry.IRegistryBlock;

/** A basic LightningCraft block. */
public class BlockLC extends Block implements IRegistryBlock {

	/** A basic LightningCraft block. */
	public BlockLC(Block parent, float hardness, float resistance) {
		super(parent.getMaterial(parent.getDefaultState()));
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(parent.getSoundType());
		this.setLightLevel(parent.getLightValue(parent.getDefaultState()));
	}

}
