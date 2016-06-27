package sblectric.lightningcraft.config;

import java.io.File;

import net.minecraftforge.common.config.Property;

import sblectric.lightningcraft.integration.cofh.CoFH;
import sblectric.lightningcraft.ref.Log;

public class LCConfig {
	
	public static int portalCooldown;
	public static int underworldDimensionID;
	public static boolean JEIIntegration, RFIntegration;
	public static int RFtoLEConversion;
	
	/** Set config defaults */
	private static void setDefaultValues() {
		// general
		portalCooldown = 200;
		
		// worldgen
		underworldDimensionID = -9;
		
		// mod integration
		JEIIntegration = true;
		RFIntegration = true;
		RFtoLEConversion = 50;
	}
	
	/** Load the mod config */
	public static void loadConfig(File f) {
		// load the config file and default values
		ConfigurationCustom config = new ConfigurationCustom(f);
		setDefaultValues();
		config.load();
		Property p;
		
		// General
		portalCooldown = config.getInt("Portal Cooldown Time", "General", portalCooldown, "The cooldown time for the underworld portal");
		
		// World Gen
		underworldDimensionID = config.getInt("Underworld Dimension ID", "Worldgen", underworldDimensionID, "The ID for the Underworld dimension");
		
		// Mod Integration
		JEIIntegration = config.getBoolean("JEI integration", "Mod Integration", RFIntegration, "Enable JEI integration?");
		RFIntegration = config.getBoolean("RF integration", "Mod Integration", RFIntegration, "Enable LE <-> RF conversion machines?");
		if(!RFIntegration) CoFH.apiLoaded = false; // force unloaded condition
		RFtoLEConversion = config.getInt("RF to LE conversion", "Mod Integration", RFtoLEConversion,
				"This amount of RF is equal to 1 LE (for 1 LE -> x RF conversion). The reverse will be 10 times costlier.");
		
		// save the config if fields were missing
		if(config.hasChanged()) config.save();
		Log.logger.info("Config loaded.");
	}

}
