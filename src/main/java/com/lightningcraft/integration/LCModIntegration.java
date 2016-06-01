package com.lightningcraft.integration;

import net.minecraftforge.fml.common.event.FMLInterModComms;

import com.lightningcraft.integration.cofh.CoFH;
import com.lightningcraft.integration.waila.WailaTileHandler;
import com.lightningcraft.ref.Log;

/** Class for integration with other mods */
public class LCModIntegration {

	public static void preInit() {}
	
	public static void onInit() {
		
		// RF API
		if(CoFH.apiLoaded) {
			Log.logger.info("CoFH RF API found, compatibility enabled");
		} else {
			Log.logger.info("CoFH RF API not found or RF disabled in config");
		}
		
		// Waila
		FMLInterModComms.sendMessage("Waila", "register", WailaTileHandler.callbackRegister);
		
	}
	
	public static void postInit() {}

}
