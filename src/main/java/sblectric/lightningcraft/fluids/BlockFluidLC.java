package sblectric.lightningcraft.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/** Molten metal fluid block */
public class BlockFluidLC extends BlockFluidClassic {

	public BlockFluidLC(Fluid fluid) {
		super(fluid, Material.LAVA);
	}

}
