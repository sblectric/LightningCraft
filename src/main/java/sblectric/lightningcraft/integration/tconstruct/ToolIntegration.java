package sblectric.lightningcraft.integration.tconstruct;

import java.awt.Color;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCFluids;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.ref.Log;
import sblectric.lightningcraft.ref.Metal.Ingot;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.traits.TraitAutosmelt;

/** Class for integration of tool parts and traits */
public class ToolIntegration {

	private static List<Material> matList;

	/** Main method */
	public static void mainRegistry() {
		matList = new JointList();
		addMaterials();
	}

	public static Material electricium;
	public static Material skyfather;
	public static Material mystic;

	public static AbstractTrait lightning1 = new TraitLightning(1);
	public static AbstractTrait lightning2 = new TraitLightning(2);
	public static AbstractTrait repairTrait = new TraitRepair();

	/** Add the mod's materials */
	private static void addMaterials() {

		// support for both new and old Tinker's versions
		Class traitHolder = null;
		try {
			traitHolder = Class.forName("slimeknights.tconstruct.tools.TinkerTraits");
		} catch (ClassNotFoundException e1) {
			try {
				traitHolder = Class.forName("slimeknights.tconstruct.tools.TinkerMaterials");
			} catch (ClassNotFoundException e2) {}
		}
		AbstractTrait autosmelt = null;
		try {
			autosmelt = (AbstractTrait)traitHolder.getField("autosmelt").get(null);
		} catch (Exception e) {}

		// continue tool integration if there were no errors
		if(autosmelt != null) {
			electricium = addMaterial("electricium", LCFluids.moltenElectricium, "Electricium", lightning1);
			skyfather = addMaterial("skyfather", LCFluids.moltenSkyfather, "Skyfather", lightning2, autosmelt);
			mystic = addMaterial("mystic", LCFluids.moltenMystic, "Mystic", lightning2, autosmelt, repairTrait);
			

			TinkerRegistry.addMaterialStats(electricium,
					new HeadMaterialStats(800, 8.00f, 7.00f, 4),
					new HandleMaterialStats(0.90f, 50),
					new ExtraMaterialStats(100));

			TinkerRegistry.addMaterialStats(skyfather,
					new HeadMaterialStats(1000, 12.00f, 9.00f, 5),
					new HandleMaterialStats(0.90f, 60),
					new ExtraMaterialStats(100));

			TinkerRegistry.addMaterialStats(mystic,
					new HeadMaterialStats(1200, 15.00f, 11.00f, 6),
					new HandleMaterialStats(0.90f, 70),
					new ExtraMaterialStats(100));

		} else {
			Log.logger.error("Unable to find Tinker's Construct trait class, aborting tool integration.");
		}
	}

	/** Makes a material and adds it */
	private static Material addMaterial(String name, Fluid fluid, String oreSuffix, AbstractTrait... traits) {
		Material mat = new Material(name, fluid.getColor());
		mat.setFluid(fluid);
		mat.addCommonItems(oreSuffix);
		mat.setCraftable(false);
		mat.setCastable(true);
		for(AbstractTrait t : traits) { // make sure the config allows this!
			if(t instanceof TraitAutosmelt && !LCConfig.autoSmelt) continue;
			if(t instanceof TraitRepair && !LCConfig.autoRepair) continue;
			mat.addTrait(t);
		}
		TinkerRegistry.addMaterial(mat);
		matList.add(mat);
		return mat;
	}
	
	/** Register the materials later than adding them */
	public static void registerMaterials() {
		for(Material m : matList) {
			TinkerSmeltery.registerToolpartMeltingCasting(m);
		}
	}

}
