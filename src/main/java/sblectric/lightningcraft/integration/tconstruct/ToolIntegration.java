package sblectric.lightningcraft.integration.tconstruct;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.fluids.LCFluids;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.util.JointList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.traits.TraitAutosmelt;

/** Class for integration of tool parts and traits */
public class ToolIntegration {
	
	private static List<Material> matList;

	/** Main method */
	public static void mainRegistry() {
		matList = new JointList();
		registerMaterials();
	}
	
	public static Material electricium;
	public static Material skyfather;
	public static Material mystic;
	
	public static AbstractTrait lightning1 = new TraitLightning(1);
	public static AbstractTrait lightning2 = new TraitLightning(2);
	public static AbstractTrait repairTrait = new TraitRepair();
	
	/** Add the mod's materials */
	private static void registerMaterials() {
		electricium = registerMaterial("electricium", LCFluids.moltenElectricium, 
				"ingotElectricium", new ItemStack(LCItems.ingot, 1, Ingot.ELEC), lightning1);
		skyfather = registerMaterial("skyfather", LCFluids.moltenSkyfather, 
				"ingotSkyfather", new ItemStack(LCItems.ingot, 1, Ingot.SKY), lightning2, TinkerMaterials.autosmelt);
		mystic = registerMaterial("mystic", LCFluids.moltenMystic, 
				"ingotMystic", new ItemStack(LCItems.ingot, 1, Ingot.MYSTIC), lightning2, TinkerMaterials.autosmelt, repairTrait);
		
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
		
		for(Material m : matList) TinkerSmeltery.registerToolpartMeltingCasting(m);
	}
	
	/** Makes a material and registers it */
	private static Material registerMaterial(String name, Fluid fluid, String ingot, ItemStack ingotStack, AbstractTrait... traits) {
		Material mat = new Material(name, fluid.getColor());
		mat.setFluid(fluid);
		mat.addItem(ingot, 1, Material.VALUE_Ingot);
		mat.setCraftable(false);
		mat.setRepresentativeItem(ingotStack);
		for(AbstractTrait t : traits) { // make sure the config allows this!
			if(t instanceof TraitAutosmelt && !LCConfig.autoSmelt) continue;
			if(t instanceof TraitRepair && !LCConfig.autoRepair) continue;
			mat.addTrait(t);
		}
		TinkerRegistry.addMaterial(mat);
		matList.add(mat);
		return mat;
	}
	
}
