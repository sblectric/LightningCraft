package sblectric.lightningcraft.config;

import java.io.File;

import net.minecraftforge.common.config.Property;

import sblectric.lightningcraft.integration.cofh.CoFH;
import sblectric.lightningcraft.ref.Log;

public class LCConfig {
	
	public static boolean portalEnabled;
	public static int portalCooldown;
	public static boolean demonSoldiersAlwaysNeutral, useVanillaGhastSounds;
	public static boolean autoSmelt, autoRepair, mysticGear;
	public static int underworldDimensionID;
	public static boolean JEIIntegration, RFIntegration;
	public static int RFtoLEConversion;
	
	/** Set config defaults */
	private static void setDefaultValues() {
		// general
		portalEnabled = true;
		portalCooldown = 200;
		demonSoldiersAlwaysNeutral = false;
		useVanillaGhastSounds = false;
		
		// tools
		autoSmelt = true;
		autoRepair = true;
		mysticGear = true;
		
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
		portalEnabled = config.getBoolean("Portal Enabled", "General", portalEnabled, "Set to false to disable default portal creation.");
		portalCooldown = config.getInt("Portal Cooldown Time", "General", portalCooldown, 
				"The cooldown time for the underworld portal. Increase if repeated teleporting occurs.");
		demonSoldiersAlwaysNeutral = config.getBoolean("Wuss Mode", "General", demonSoldiersAlwaysNeutral,
				"Set to true if Demon Soldiers should only attack the player if they are attacked first");
		useVanillaGhastSounds = config.getBoolean("Use Vanilla Ghast Sounds", "General", useVanillaGhastSounds,
				"Whether or not to use the vanilla ghast sounds for the Underghast instead of the provided ones");
		
		// Tools
		autoSmelt = config.getBoolean("Enable Skyfather Autosmelting", "Tools and Armor", autoSmelt, 
				"Whether or not Skyfather and Mystic tools will auto-smelt mined blocks.");
		autoRepair = config.getBoolean("Enable Mystic Auto-repair", "Tools and Armor", autoRepair, 
				"Whether or not Mystic tools will auto-repair.");
		mysticGear = config.getBoolean("Enable Mystic Gear", "Tools and Armor", autoRepair, 
				"If false, Mystic gear will not be able to be created.");
		
		// World Gen
		underworldDimensionID = config.getInt("Underworld Dimension ID", "Worldgen", underworldDimensionID, 
				"The ID for the Underworld dimension");
		
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
