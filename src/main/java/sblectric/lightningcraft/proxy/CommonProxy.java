package sblectric.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import sblectric.lightningcraft.achievements.LCAchievements;
import sblectric.lightningcraft.biomes.LCBiomes;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.crafting.LCCraftingManager;
import sblectric.lightningcraft.creativetabs.LCCreativeTabs;
import sblectric.lightningcraft.dimensions.LCDimensions;
import sblectric.lightningcraft.enchantments.LCEnchantments;
import sblectric.lightningcraft.entities.LCEntities;
import sblectric.lightningcraft.events.LCEvents;
import sblectric.lightningcraft.fluids.LCFluids;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.integration.LCModIntegration;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.main.LightningCraft;
import sblectric.lightningcraft.network.LCNetwork;
import sblectric.lightningcraft.potions.LCPotions;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.Log;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.sounds.LCSoundEvents;
import sblectric.lightningcraft.tiles.LCTileEntities;
import sblectric.lightningcraft.worldgen.structure.LCStructures;

/** The common proxy (shared server and client code) */
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		Log.logger.info("PreInitializing " + RefStrings.NAME + "...");
		LCText.setupFormatting();
		LCConfig.loadConfig(event.getSuggestedConfigurationFile());
		LCCreativeTabs.mainRegistry();
		LCBlocks.mainRegistry();
		LCItems.mainRegistry();
		LCFluids.mainRegistry();
		LCTileEntities.registerTileEntities();
		LCEntities.mainRegistry();
		LCPotions.mainRegistry();
		LCBiomes.mainRegistry();
		LCEnchantments.mainRegistry();
		LCSoundEvents.mainRegistry();
		LCNetwork.mainRegistry();
		LCModIntegration.preInit();
	}
	
	public void onInit(FMLInitializationEvent event) {
		Log.logger.info("Initializing " + RefStrings.NAME + "...");
		NetworkRegistry.INSTANCE.registerGuiHandler(LightningCraft.modInstance, new LCGuiHandler());
		LCDimensions.mainRegistry();
		LCStructures.mainRegistry();
		LCAchievements.mainRegistry();
		LCEvents.mainRegistry();
		LCCraftingManager.mainRegistry();
		LCCreativeTabs.updateCreativeTabs();
		LCModIntegration.onInit();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		Log.logger.info("PostInitializing " + RefStrings.NAME + "...");
		LCModIntegration.postInit();
	}

}
