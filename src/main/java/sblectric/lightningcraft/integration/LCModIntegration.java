package sblectric.lightningcraft.integration;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
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
		
		// Tinker's Construct
		if(Loader.isModLoaded("tconstruct") && LCConfig.tinkersIntegration) {
			SmelteryIntegration.registerFluids();
			ToolIntegration.mainRegistry();
			Log.logger.info("Tinker's Construct integration complete.");
		} else {
			if(LCConfig.tinkersIntegration) {
				Log.logger.info("Tinker's Construct not found, integration skipped.");
			} else {
				Log.logger.info("Tinker's Construct integration disabled.");
			}
		}
	}
	
	public static void onInit() {
		
		// Energy APIs
		if(EnergyApiHelper.rfOrTeslaLoaded) {
			if(EnergyApiHelper.rfLoaded) Log.logger.info("CoFH RF API found, compatibility enabled");
			if(EnergyApiHelper.teslaLoaded) Log.logger.info("TESLA API found, compatibility enabled");
		} else {
			if(LCConfig.RFIntegration) {
				Log.logger.info("CoFH RF API or TESLA not found.");
			} else {
				Log.logger.info("RF and TESLA disabled in config.");
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
