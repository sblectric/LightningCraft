package sblectric.lightningcraft.registry;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.ref.Log;

/** Clientside registry things */
public class ClientRegistryHelper {
	
	public static final List<ModelLocation> MODELS_TO_REGISTER = new JointList();
	
	static class ModelLocation {
		Item item;
		int meta;
		ModelResourceLocation location;
		
		public ModelLocation(Item item, int meta, ModelResourceLocation location) {
			this.item = item;
			this.meta = meta;
			this.location = location;
		}
	}
	
	/** Register the model correctly */
	public static void registerModel(Item item, int meta, ModelResourceLocation location) {
		MODELS_TO_REGISTER.add(new ModelLocation(item, meta, location));
	}
	
	@SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
		for(ModelLocation l : MODELS_TO_REGISTER) {
			ModelLoader.setCustomModelResourceLocation(l.item, l.meta, l.location);
		}
		
		MODELS_TO_REGISTER.clear();
		
		Log.logger.info("Models registered.");
	}

}
