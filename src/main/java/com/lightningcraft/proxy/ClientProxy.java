package com.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.lightningcraft.blocks.LCBlocks;
import com.lightningcraft.entities.LCEntities;
import com.lightningcraft.items.LCItems;

/** The client proxy (rendering and such) */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void onInit(FMLInitializationEvent event) {
		super.onInit(event);
		LCBlocks.registerRendering();
		LCItems.registerRendering();
		LCEntities.registerRendering();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}
