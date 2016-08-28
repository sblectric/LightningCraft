package sblectric.lightningcraft.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ModFluid extends Fluid {

	private final int color;
	
	public ModFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int tintARGB) {
		super(fluidName, still, flowing);
		this.color = tintARGB;
	}

	@Override
	public int getColor() {
		return color;
	}

}
