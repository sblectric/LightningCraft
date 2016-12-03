package sblectric.lightningcraft.blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;

/** Underworld sand */
public class BlockUnderSand extends BlockFalling implements ILightningCraftBlock {

	public BlockUnderSand() {
		this.setHardness(1F);
		this.setSoundType(SoundType.SAND);
	}
	
	@Override
	public Material getMaterial(IBlockState state) {
		return Material.SAND;
	}
	
}
