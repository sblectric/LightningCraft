package sblectric.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.crafting.LCCraftingManager;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.init.LCAchievements;
import sblectric.lightningcraft.init.LCBiomes;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.init.LCCreativeTabs;
import sblectric.lightningcraft.init.LCDimensions;
import sblectric.lightningcraft.init.LCEnchantments;
import sblectric.lightningcraft.init.LCEntities;
import sblectric.lightningcraft.init.LCEvents;
import sblectric.lightningcraft.init.LCFluids;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.init.LCNetwork;
import sblectric.lightningcraft.init.LCPotions;
import sblectric.lightningcraft.init.LCSoundEvents;
import sblectric.lightningcraft.init.LCStructures;
import sblectric.lightningcraft.init.LCTileEntities;
import sblectric.lightningcraft.integration.LCModIntegration;
import sblectric.lightningcraft.ref.Log;
import sblectric.lightningcraft.ref.RefStrings;

/** The common proxy (shared server and client code) */
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		Log.logger.info("PreInitializing " + RefStrings.NAME + "...");
		LCConfig.loadConfig(event.getSuggestedConfigurationFile());
		LCCreativeTabs.mainRegistry();
		LCBlocks.mainRegistry();
		LCItems.mainRegistry();
		LCFluids.mainRegistry();
		LCTileEntities.mainRegistry();
		LCEntities.mainRegistry();
		LCPotions.mainRegistry();
		LCBiomes.mainRegistry();
		LCEnchantments.mainRegistry();
		LCSoundEvents.mainRegistry();
		LCNetwork.mainRegistry();
		LCCapabilities.mainRegistry();
		LCModIntegration.preInit();
	}
	
	public void onInit(FMLInitializationEvent event) {
		Log.logger.info("Initializing " + RefStrings.NAME + "...");
		NetworkRegistry.INSTANCE.registerGuiHandler(LightningCraft.modInstance, new LCGuiHandler());
		LCDimensions.mainRegistry();
		LCStructures.mainRegistry();
		LCAchievements.mainRegistry();
		LCEvents.mainRegistry();
		LCCreativeTabs.updateCreativeTabs();
		LCCraftingManager.onInit();
		LCModIntegration.onInit();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		Log.logger.info("PostInitializing " + RefStrings.NAME + "...");
		LCCraftingManager.postInit();
		LCModIntegration.postInit();
	}

}
