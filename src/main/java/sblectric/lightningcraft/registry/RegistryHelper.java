package sblectric.lightningcraft.registry;

import java.lang.reflect.Constructor;
import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.crafting.LCCraftingManager;
import sblectric.lightningcraft.init.LCBiomes;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCEnchantments;
import sblectric.lightningcraft.init.LCFluids;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.init.LCPotions;
import sblectric.lightningcraft.init.LCSoundEvents;
import sblectric.lightningcraft.integration.LCModIntegration;
import sblectric.lightningcraft.ref.Log;

/** Helps register everything in the game */
public class RegistryHelper {
	
	public static final List<IRecipe> RECIPES_TO_REGISTER = new JointList();
	public static final List<Block> BLOCKS_TO_REGISTER = new JointList();
	public static final List<Block> FLUIDS_TO_REGISTER = new JointList();
	public static final List<Item> ITEMS_TO_REGISTER = new JointList();
	public static final List<Biome> BIOMES_TO_REGISTER = new JointList();
	public static final List<Enchantment> ENCHANTMENTS_TO_REGISTER = new JointList();
	public static final List<Potion> POTIONS_TO_REGISTER = new JointList();
	public static final List<PotionType> POTION_TYPES_TO_REGISTER = new JointList();
	public static final List<SoundEvent> SOUNDS_TO_REGISTER = new JointList();
	
	/** Register a list of blocks at once */
	public static void registerBlocks(Iterable<ILightningCraftBlock> regBlocks) {
		for(ILightningCraftBlock r : regBlocks) registerBlock(r);
	}
	
	/** Register a list of items at once */
	public static void registerItems(Iterable<ILightningCraftItem> regItems) {
		for(ILightningCraftItem r : regItems) {
			r.setRarity();
			registerItem(r);
		}
	}
	 
	/** Register the block correctly */
	public static void registerBlock(ILightningCraftBlock regBlock) {
		Block block = (Block)regBlock;
		ItemBlock item;
		
		// register the block by itself first
		BLOCKS_TO_REGISTER.add(block.setUnlocalizedName(block.getRegistryName().toString()));
        
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
	        ITEMS_TO_REGISTER.add(item.setRegistryName(block.getRegistryName()));
        }
	}
	
	/** Register the item correctly */
	public static void registerItem(ILightningCraftItem regItem) {
		Item item = (Item)regItem;
		ITEMS_TO_REGISTER.add(item.setUnlocalizedName(item.getRegistryName().toString()));
	}
	
	/** Register blocks and fluids */
	@SubscribeEvent
	public void onBlockRegistry(Register<Block> e) {
		LCBlocks.mainRegistry();
		LCFluids.mainRegistry();
		
		for(Block b : new JointList<Block>().join(BLOCKS_TO_REGISTER).join(FLUIDS_TO_REGISTER)) {
			e.getRegistry().register(b);
		}
		BLOCKS_TO_REGISTER.clear();
		FLUIDS_TO_REGISTER.clear();
		
		// execute any integrations that need fluids first
		LCModIntegration.afterFluids();
		
		Log.logger.info("Blocks registered.");
	}
	
	/** Register items */
	@SubscribeEvent
	public void onItemRegistry(Register<Item> e) {
		LCItems.mainRegistry();
		
		for(Item i : ITEMS_TO_REGISTER) {
			e.getRegistry().register(i);
		}
		ITEMS_TO_REGISTER.clear();
		
		// ore dict before recipes!
		LCCraftingManager.setOreDictionary();
		
		LightningCraft.proxy.registerItemModels();
		
		Log.logger.info("Items registered.");
	}
	
	/** Register recipes */
	@SubscribeEvent
	public void onRecipeRegistry(Register<IRecipe> e) {
		LCCraftingManager.addCraftingRecipes();
		
		for(IRecipe r : RECIPES_TO_REGISTER) {
			e.getRegistry().register(r);
		}
		RECIPES_TO_REGISTER.clear();
		
		Log.logger.info("Recipes registered.");
	}
	
	/** Register biomes */
	@SubscribeEvent
	public void onBiomeRegistry(Register<Biome> e) {
		LCBiomes.mainRegistry();
		
		for(Biome b : BIOMES_TO_REGISTER) {
			e.getRegistry().register(b);
		}
		BIOMES_TO_REGISTER.clear();
		
		LCBiomes.setBiomeTypes();
		
		Log.logger.info("Biomes registered.");
	}
	
	/** Register enchantments */
	@SubscribeEvent
	public void onEnchantmentRegistry(Register<Enchantment> e) {
		LCEnchantments.mainRegistry();
		
		for(Enchantment ench : ENCHANTMENTS_TO_REGISTER) {
			e.getRegistry().register(ench);
		}
		ENCHANTMENTS_TO_REGISTER.clear();
		
		Log.logger.info("Enchantments registered.");
	}
	
	/** Register potions */
	@SubscribeEvent
	public void onPotionRegistry(Register<Potion> e) {
		LCPotions.mainRegistry();
		
		for(Potion p : POTIONS_TO_REGISTER) {
			e.getRegistry().register(p);
		}
		POTIONS_TO_REGISTER.clear();
		
		Log.logger.info("Potions registered.");
	}
	
	/** Register potion types */
	@SubscribeEvent
	public void onPotionTypeRegistry(Register<PotionType> e) {
		
		for(PotionType p : POTION_TYPES_TO_REGISTER) {
			e.getRegistry().register(p);
		}
		POTION_TYPES_TO_REGISTER.clear();
		
		Log.logger.info("Potion types registered.");
	}
	
	/** Register sounds */
	@SubscribeEvent
	public void onSoundEventRegistry(Register<SoundEvent> e) {
		LCSoundEvents.mainRegistry();
		
		for(SoundEvent s : SOUNDS_TO_REGISTER) {
			e.getRegistry().register(s);
		}
		SOUNDS_TO_REGISTER.clear();
		
		Log.logger.info("Sounds registered.");
	}

}
