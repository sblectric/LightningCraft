package sblectric.lightningcraft.integration.chisel;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import team.chisel.api.carving.ICarvingVariation;

@Optional.Interface(iface = "team.chisel.api.carving.ICarvingVariation", modid = "chisel")
public class CarvingVariation implements ICarvingVariation {
	
	private Block block;
	private int meta;
	
	public CarvingVariation(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public IBlockState getBlockState() {
		return block.getStateFromMeta(meta);
	}

	@Override
	public ItemStack getStack() {
		return new ItemStack(block, 1, meta);
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
