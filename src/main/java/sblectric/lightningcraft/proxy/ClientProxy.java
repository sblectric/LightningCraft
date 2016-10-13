package sblectric.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.entities.LCEntities;
import sblectric.lightningcraft.fluids.LCFluids;
import sblectric.lightningcraft.items.LCItems;

/** The client proxy (rendering and such) */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		LCBlocks.registerRendering();
		LCItems.registerRendering();
		LCFluids.registerRendering();
		LCEntities.registerRendering();
	}
	
	@Override
	public void onInit(FMLInitializationEvent event) {
		super.onInit(event);
		LCBlocks.registerBlockColors();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}
