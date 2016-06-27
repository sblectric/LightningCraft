package sblectric.lightningcraft.registry;

import java.lang.reflect.Constructor;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.ref.Log;

import com.google.common.collect.ObjectArrays;

/** Helps register items and blocks in the game */
public class RegistryHelper {
	
	/** Register a list of blocks at once */
	public static void registerBlocks(Iterable<IRegistryBlock> regBlocks) {
		for(IRegistryBlock r : regBlocks) registerBlock(r);
	}
	
	/** Register a list of items at once */
	public static void registerItems(Iterable<IRegistryItem> regItems) {
		for(IRegistryItem r : regItems) {
			r.setRarity();
			registerItem(r);
		}
	}
	 
	/** Register the block correctly */
	public static void registerBlock(IRegistryBlock regBlock) {
		Block block = (Block)regBlock;
		ItemBlock item;
		
		// register the block by itself first
        GameRegistry.register(block.setUnlocalizedName(block.getRegistryName().getResourcePath()));
        
		// try to get the ItemBlock
        if(regBlock.getItemClass() != null) {
	        try {
				Class<?>[] ctorArgClasses = new Class[regBlock.getItemClassArgs().length + 1];
		        ctorArgClasses[0] = Block.class; // start with the block
		        for (int idx = 1; idx < ctorArgClasses.length; idx++) {
		            ctorArgClasses[idx] = regBlock.getItemClassArgs()[idx - 1].getClass();
		        }
		        Constructor<? extends ItemBlock> itemCtor = regBlock.getItemClass().getConstructor(ctorArgClasses);
		        item = itemCtor.newInstance(ObjectArrays.concat(regBlock, regBlock.getItemClassArgs()));
	        } catch (Exception e) {
	        	Log.logger.error("Unable to register block " + block.getRegistryName());
	        	return;
	        }
	        
	        // register the ItemBlock if there are no errors
	        GameRegistry.register(item.setRegistryName(block.getRegistryName()));
        }
	}
	
	/** Register the item correctly */
	public static void registerItem(IRegistryItem regItem) {
		Item item = (Item)regItem;
		GameRegistry.register(item.setUnlocalizedName(item.getRegistryName().getResourcePath()));
	}
	
	/** Register the entity renderer */
	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderer(Class<? extends Entity> entityClass, Render<? extends Entity> renderer) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
//		RenderingRegistry.registerEntityRenderingHandler(entityClass, new IRenderFactory() {
//			@Override
//			public Render<? extends Entity> createRenderFor(RenderManager manager) {
//				return renderer;
//			}
//		});
	}

}
