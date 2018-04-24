package sblectric.lightningcraft.integration;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.integration.chisel.ChiselIntegration;
import sblectric.lightningcraft.integration.energy.EnergyApiHelper;
import sblectric.lightningcraft.integration.tconstruct.SmelteryIntegration;
import sblectric.lightningcraft.integration.tconstruct.ToolIntegration;
import sblectric.lightningcraft.integration.waila.WailaTileHandler;
import sblectric.lightningcraft.ref.Log;

/** Class for integration with other mods */
public class LCModIntegration {

	public static void preInit() {
		
		// Tinker's Construct fluids
		if(Loader.isModLoaded("tconstruct") && LCConfig.tinkersIntegration) {
			SmelteryIntegration.registerFluids();
			ToolIntegration.mainRegistry();
			Log.logger.info("Tinker's Construct fluid integration complete.");
		} else {
			if(LCConfig.tinkersIntegration) {
				Log.logger.info("Tinker's Construct not found, fluid integration skipped.");
			} else {
				Log.logger.info("Tinker's Construct fluid integration disabled.");
			}
		}
		
	}
	
	public static void onInit() {
		
		// Tinker's Construct tools
		if(Loader.isModLoaded("tconstruct") && LCConfig.tinkersIntegration) {
			ToolIntegration.registerMaterials();
			Log.logger.info("Tinker's Construct tool integration complete.");
		} else {
			if(LCConfig.tinkersIntegration) {
				Log.logger.info("Tinker's Construct not found, tool integration skipped.");
			} else {
				Log.logger.info("Tinker's Construct tool integration disabled.");
			}
		}

		// Energy APIs
		if(EnergyApiHelper.rfOrTeslaLoaded) {
			if(EnergyApiHelper.rfLoaded) Log.logger.info("Forge Energy compatibility enabled");
			if(EnergyApiHelper.teslaLoaded) Log.logger.info("TESLA API found, compatibility enabled");
		} else {
			if(LCConfig.RFIntegration) {
				Log.logger.info("TESLA not found.");
			} else {
				Log.logger.info("Forge Energy and TESLA disabled in config.");
			}
		}
		
		// Chisel
		if(Loader.isModLoaded("chisel") && LCConfig.chiselIntegration) {
			ChiselIntegration.mainRegistry();
		} else {
			if(LCConfig.chiselIntegration) {
				Log.logger.info("Chisel not found, integration skipped.");
			} else {
				Log.logger.info("Chisel integration disabled.");
			}
		}
		
		// Waila
		FMLInterModComms.sendMessage("waila", "register", WailaTileHandler.callbackRegister);
		
	}
	
	public static void postInit() {}

}
