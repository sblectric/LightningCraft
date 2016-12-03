package sblectric.lightningcraft.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, this.getMetaFromState(state));
	}

}
