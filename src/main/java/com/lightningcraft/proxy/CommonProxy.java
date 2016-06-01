package com.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.lightningcraft.achievements.LCAchievements;
import com.lightningcraft.biomes.LCBiomes;
import com.lightningcraft.blocks.LCBlocks;
import com.lightningcraft.config.LCConfig;
import com.lightningcraft.crafting.LCCraftingManager;
import com.lightningcraft.creativetabs.LCCreativeTabs;
import com.lightningcraft.dimensions.LCDimensions;
import com.lightningcraft.enchantments.LCEnchantments;
import com.lightningcraft.entities.LCEntities;
import com.lightningcraft.events.LCEvents;
import com.lightningcraft.gui.LCGuiHandler;
import com.lightningcraft.integration.LCModIntegration;
import com.lightningcraft.items.LCItems;
import com.lightningcraft.main.LightningCraft;
import com.lightningcraft.network.LCNetwork;
import com.lightningcraft.potions.LCPotions;
import com.lightningcraft.ref.LCText;
import com.lightningcraft.ref.Log;
import com.lightningcraft.sounds.LCSoundEvents;
import com.lightningcraft.worldgen.structure.LCStructures;

/** The common proxy (shared server and client code) */
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		Log.logger.info("Initializing LightningCraft...");
		LCText.setupFormatting();
		LCConfig.loadConfig(event.getSuggestedConfigurationFile());
		LCNetwork.mainRegistry();
		LCCreativeTabs.mainRegistry();
		LCEnchantments.mainRegistry();
		LCSoundEvents.mainRegistry();
		LCModIntegration.preInit();
	}
	
	public void onInit(FMLInitializationEvent event) {
		Log.logger.info("Loading LightningCraft...");
		LCBlocks.mainRegistry();
		LCItems.mainRegistry();
		NetworkRegistry.INSTANCE.registerGuiHandler(LightningCraft.modInstance, new LCGuiHandler());
		LCEntities.mainRegistry();
		LCPotions.mainRegistry();
		LCBiomes.mainRegistry();
		LCDimensions.mainRegistry();
		LCStructures.mainRegistry();
		LCAchievements.mainRegistry();
		LCModIntegration.onInit();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		Log.logger.info("Finalizing LightningCraft...");
		LCEvents.mainRegistry();
		LCCraftingManager.mainRegistry();
		LCCreativeTabs.updateCreativeTabs();
		LCModIntegration.postInit();
	}

}
