package sblectric.lightningcraft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCEntities;
import sblectric.lightningcraft.init.LCFluids;
import sblectric.lightningcraft.init.LCItems;

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
