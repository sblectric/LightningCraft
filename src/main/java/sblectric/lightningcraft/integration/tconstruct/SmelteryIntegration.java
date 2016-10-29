package sblectric.lightningcraft.integration.tconstruct;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import sblectric.lightningcraft.init.LCFluids;

/** Class for Tinker's Construct smeltery integration */
public class SmelteryIntegration {
	
	/** Register the fluids with the smeltery */
	public static void registerFluids() {
		registerFluid(LCFluids.moltenElectricium, "Electricium");
		registerFluid(LCFluids.moltenSkyfather, "Skyfather");
		registerFluid(LCFluids.moltenMystic, "Mystic");
	}
	
	/** Register a single fluid with the smeltery */
	private static void registerFluid(Fluid fluid, String oreSuffix) {
		// create NBT for the IMC
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", fluid.getName()); // name of the fluid
		tag.setString("ore", oreSuffix); // ore-suffix: ingotFoo, blockFoo, oreFoo,...
		tag.setBoolean("toolforge", true); // if set to true, blockFoo can be used to build a toolforge

		// send the NBT to TCon
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
	}

}
