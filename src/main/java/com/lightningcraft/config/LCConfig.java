package com.lightningcraft.config;

import java.io.File;

import net.minecraftforge.common.config.Property;

import com.lightningcraft.integration.cofh.CoFH;
import com.lightningcraft.ref.Log;

public class LCConfig {
	
	public static int portalCooldown;
	public static int thorEnchID, auraEnchID;
	public static int demonFriendPotionID;
	public static int underworldBiomeID, underworldDimensionID;
	public static boolean JEIIntegration, RFIntegration;
	public static int RFtoLEConversion;
	
	/** Set config defaults */
	private static void setDefaultValues() {
		// general
		portalCooldown = 200;
		
		// enchantment IDs
		thorEnchID = 80;
		auraEnchID = 81;
		
		// potion IDs
		demonFriendPotionID = 150;
		
		// worldgen
		underworldBiomeID = 212;
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
		portalCooldown = config.getInt("portal.cooldown", "General", portalCooldown, "The cooldown time for the underworld portal");
		
		// Enchantment IDs
		thorEnchID = config.getInt("handOfThor.ID", "Enchantments", thorEnchID, "The Hand of Thor enchantment ID");
		auraEnchID = config.getInt("elecAura.ID", "Enchantments", auraEnchID, "The Electrostatic Aura enchantment ID");
		
		// Potions
		demonFriendPotionID = config.getInt("demonFriend.ID", "Potions", demonFriendPotionID, "The Demon Warding potion effect ID");
		
		// World Gen
		underworldBiomeID = config.getInt("biomes.underworld.ID", "Worldgen", underworldBiomeID, "The Underworld biome ID");
		underworldDimensionID = config.getInt("dimensions.underworld.ID", "Worldgen", underworldDimensionID, "The Underworld dimension ID");
		
		// Mod Integration
		JEIIntegration = config.getBoolean("JEI.integration", "Mod Integration", RFIntegration, "Enable JEI integration?");
		RFIntegration = config.getBoolean("RF.integration", "Mod Integration", RFIntegration, "Enable LE <-> RF conversion machines?");
		if(!RFIntegration) CoFH.apiLoaded = false; // force unloaded condition
		RFtoLEConversion = config.getInt("RF.LE.conversion", "Mod Integration", RFtoLEConversion,
				"This amount of RF is equal to 1 LE (for 1 LE -> x RF conversion). The reverse will be 10 times costlier.");
		
		// save the config if fields were missing
		if(config.hasChanged()) config.save();
		Log.logger.info("Config loaded.");
	}

}
