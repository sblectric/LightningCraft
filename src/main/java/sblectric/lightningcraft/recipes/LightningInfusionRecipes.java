package sblectric.lightningcraft.recipes;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.recipes.LightningInfusionRecipe;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.api.util.StackHelper;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.init.LCPotions;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.util.LCMisc;

/** Infusion recipes rewritten from scratch */
public class LightningInfusionRecipes {
	
	/** The static instance */
	private static LightningInfusionRecipes instance = new LightningInfusionRecipes();
	private int recipeIndex;
	private JointList<LightningInfusionRecipe> recipes = new JointList();
	private int lastResultCost = -2;
	
	/** Get the static instance */
	public static LightningInfusionRecipes instance() {
		return instance;
	}
	
	/** internal constructor */
	private LightningInfusionRecipes() {
		recipeIndex = 0;
		addDefaultRecipes();
	}
	
	private static final int ANY = OreDictionary.WILDCARD_VALUE;
	
	/** Add the mod's default recipes */
	private void addDefaultRecipes() {
		ItemStack thunderstone = new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER);
		ItemStack demonBlood = new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD);
		ItemStack ichor = new ItemStack(LCItems.material, 1, Material.ICHOR);
		ItemStack prismarine = new ItemStack(Blocks.PRISMARINE, 1, ANY);
		ItemStack wool = new ItemStack(Blocks.WOOL, 1, ANY);
		String dust = "dustMystic";
		
		// useful general recipes
		addRecipe(new ItemStack(Blocks.DEADBUSH), 10, "treeSapling", Blocks.SAND);
		addRecipe(new ItemStack(Blocks.CLAY, 3), 15, Blocks.DIRT, Blocks.GRAVEL, Blocks.SAND, Items.POTIONITEM);
		addRecipe(new ItemStack(Blocks.SPONGE), 10, wool, "slimeball");
		addRecipe(new ItemStack(Blocks.PACKED_ICE), 15, Blocks.ICE, LCMisc.makeArray(Blocks.ICE, 3));
		addRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE), 10, "cobblestone", Blocks.VINE);
		addRecipe(new ItemStack(Blocks.BOOKSHELF, 2), 10, "logWood", LCMisc.makeArray(Items.BOOK, 4));
		addRecipe(new ItemStack(Items.QUARTZ), 40, new ItemStack(Blocks.STONE, 1, 1), LCMisc.makeArray(new ItemStack(Blocks.STONE, 1, 3), 2));
		addRecipe(new ItemStack(Items.DIAMOND), 50, Blocks.COAL_BLOCK, LCMisc.makeArray(Items.COAL, 4));
		addRecipe(new ItemStack(Items.EMERALD), 75, prismarine, LCMisc.makeArray(Items.PRISMARINE_CRYSTALS, 4));
		
		// electricium
		addRecipe(new ItemStack(LCItems.ingot, 1, Ingot.ELEC), 30, "ingotIron", "ingotGold", "gemDiamond");
		addRecipe(new ItemStack(LCBlocks.metalBlock, 1, Ingot.ELEC), 270, "blockIron", "blockGold", "blockDiamond");
		
		// special swords
		int spCost = 90;
		addRecipe(new ItemStack(LCItems.soulSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Blocks.SOUL_SAND, 4));
		addRecipe(new ItemStack(LCItems.zombieSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Items.ROTTEN_FLESH, 4));
		addRecipe(new ItemStack(LCItems.featherSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Items.FEATHER, 4));
		addRecipe(new ItemStack(LCItems.enderSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Items.ENDER_PEARL, 4));
		addRecipe(new ItemStack(LCItems.blazeSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Items.BLAZE_ROD, 4));
		addRecipe(new ItemStack(LCItems.iceSword), spCost, Items.STONE_SWORD, LCMisc.makeArray(Blocks.PACKED_ICE, 4));
		
		// skyfather ingot
		addRecipe(new ItemStack(LCItems.ingot, 1, Ingot.SKY), 85, "ingotElectricium", thunderstone, Items.ENDER_EYE, thunderstone, Items.ENDER_EYE);
		
		// demon blood things
		addRecipe(new ItemStack(Items.LAVA_BUCKET), 40, Items.WATER_BUCKET, LCMisc.makeArray(demonBlood, 4));
		addRecipe(new ItemStack(Blocks.NETHERRACK, 2), 15, "cobblestone", demonBlood);
		addRecipe(new ItemStack(Blocks.NETHER_BRICK), 25, Blocks.BRICK_BLOCK, demonBlood);
		addRecipe(new ItemStack(Items.NETHER_WART, 2), 20, Items.WHEAT_SEEDS, demonBlood);
		
		// demonstone
		addRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON), 35, new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER), demonBlood, demonBlood);
		
		// underworld charge
		addRecipe(new ItemStack(LCItems.material, 1, Material.UNDER_CHARGE), 100, Items.FIRE_CHARGE, "dustDiamond", demonBlood, Blocks.OBSIDIAN);
		addRecipe(new ItemStack(LCItems.material, 1, Material.UNDER_CHARGE), 40, Blocks.OBSIDIAN, 
				demonBlood, new ItemStack(LCItems.material, 1, Material.UNDER_POWDER_2));
		
		// ensorcelled core
		addRecipe(new ItemStack(LCItems.material, 1, Material.ENSORCELLED), 250, new ItemStack(LCItems.material, 1, Material.UPGRADE), 
				"dustDiamond", demonBlood, new ItemStack(LCItems.material, 1, Material.UNDER_MEAL), demonBlood);
		
		// underpowder
		addRecipe(new ItemStack(LCItems.material, 4, Material.UNDER_POWDER_2), 50, "dustElectricium", 
				LCMisc.makeArray(new ItemStack(LCItems.material, 1, Material.UNDER_POWDER), 4));
		addRecipe(new ItemStack(LCItems.material, 4, Material.DIVINE_DUST), 100, "dustMystic", 
				LCMisc.makeArray(new ItemStack(LCItems.material, 1, Material.UNDER_POWDER_2), 4));
		
		// potions
		ItemStack mundane = LCPotions.getPotionWithType(PotionTypes.MUNDANE);
		ItemStack normalDemon = LCPotions.getPotionWithType(LCPotions.demonFriendNormal);
		ItemStack extendedDemon = LCPotions.getPotionWithType(LCPotions.demonFriendExtended);
		addRecipe(normalDemon, 50, mundane, demonBlood, Items.GOLDEN_CARROT, demonBlood, Items.GOLDEN_CARROT).setNBTSensitive();
		addRecipe(extendedDemon, 25, normalDemon, LCMisc.makeArray("dustRedstone", 4)).setNBTSensitive();
		
		// mystic ingot
		addRecipe(new ItemStack(LCItems.ingot, 2, Ingot.MYSTIC), 160, "ingotSkyfather", "nuggetNetherStar", demonBlood, ichor, demonBlood);
		
		if(LCConfig.mysticGear) {
			
			// mystic tool recipes
			addRecipe(new ItemStack(LCItems.mysticHammer), 360, LCItems.skyHammer, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticSword), 300, LCItems.skySword, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticPick), 240, LCItems.skyPick, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticAxe), 240, LCItems.skyAxe, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticShovel), 240, LCItems.skyShovel, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticHoe), 200, LCItems.skyHoe, LCMisc.makeArray(dust, 4));
			
			// mystic armor recipes
			addRecipe(new ItemStack(LCItems.mysticHelm), 240, LCItems.skyHelm, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticChest), 360, LCItems.skyChest, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticLegs), 300, LCItems.skyLegs, LCMisc.makeArray(dust, 4));
			addRecipe(new ItemStack(LCItems.mysticBoots), 240, LCItems.skyBoots, LCMisc.makeArray(dust, 4));
		}
	}
	
	/** Add an infusion recipe */
	public LightningInfusionRecipe addRecipe(LightningInfusionRecipe r) {
		recipes.add(r);
		recipeIndex++;
		return r;
	}
	
	/** Adds a new infusion recipe with Items, Blocks, ItemStacks, or oreDict entries */
	public LightningInfusionRecipe addRecipe(ItemStack output, int cost, Object infuse, Object... surrounding) {
		return addRecipe(new LightningInfusionRecipe(output, cost, infuse, surrounding));
	}
	
	/** Helper method for hasBaseResult and such */
	private boolean hasResult(ItemStack infuse, String item, List<String> itemOres, boolean nbtSensitive) {
		String useItem = StackHelper.makeStringFromItemStack(infuse);
		
		// ignore NBT for the infused item?
		if(!nbtSensitive) {
			useItem = StackHelper.stripNBTFromString(useItem);
			item = StackHelper.stripNBTFromString(item);
		}
		
		// check the vanilla case and simple oredict case
		if(item.equals(useItem) || itemOres.contains(useItem)) {
			return true;
		}
		
		// check the simple wildcard case
		if(StackHelper.getMetaFromString(item) == OreDictionary.WILDCARD_VALUE) {
			String newInfuse = StackHelper.stripMetaFromString(item);
			if(newInfuse.equals(StackHelper.stripMetaFromString(useItem))) return true;
		}
		
		// check the oredict entry wildcard case
		for(String s : itemOres) {
			if(StackHelper.getMetaFromString(s) == OreDictionary.WILDCARD_VALUE) {
				String cutInfuseOre = StackHelper.stripMetaFromString(s);
				if(cutInfuseOre.equals(StackHelper.stripMetaFromString(useItem))) return true;
			}
		}
		
		// false if nothing above worked
		return false;
	}
	
	/** Is there any infusion recipe which takes the specified infusion item? */
	public boolean hasBaseResult(ItemStack infuse1) {
		if(infuse1 == null) return false;
		ItemStack infuse = infuse1.copy(); infuse.stackSize = 1;
		for(LightningInfusionRecipe recipe : recipes) {
			if(hasResult(infuse, recipe.getInfuseItem(), recipe.getInfuseItemAsOre(), recipe.getNBTSensitive())) return true;
		}
		return false;
	}
	
	/** Get the infusion result for a specified grid */
	public ItemStack getInfusionResult(ItemStack infuse1, ItemStack... stacks1) {
		// make sure the item grid is the correct size!
		if(stacks1.length != LightningInfusionRecipe.itemsNeeded) return null;
		
		// normalize the stack sizes to 1
		ItemStack infuse = infuse1.copy(); infuse.stackSize = 1;
		List<ItemStack> stacks = new JointList();
		for(int s = 0; s < stacks1.length; s++) {
			if(stacks1[s] != null) {
				ItemStack current = stacks1[s].copy(); current.stackSize = 1;
				stacks.add(current);
			} else {
				stacks.add(null);
			}
		}
		
		// now iterate through the recipes
		for(LightningInfusionRecipe recipe : recipes) {
			// how many valid matches there are
			int valid = 0;
			
			// build a copy of the item lists
			List<String> itemList = new JointList();
			List<List<String>> oreList = new JointList();
			for(String item : recipe.getItems()) {
				item = StackHelper.stripNBTFromString(item);
				itemList.add(item);
			}
			for(List list : recipe.getItemsAsOres()) {
				oreList.add(list);
			}
			
			// now check to see if the infused item matches the recipe
			if(hasResult(infuse, recipe.getInfuseItem(), recipe.getInfuseItemAsOre(), recipe.getNBTSensitive())) {
				valid++;
			}
			
			// now check the surrounding items
			for(ItemStack stack : stacks) {
				String stackString;
				List matchList;
				if(stack == null) {
					stackString = LightningInfusionRecipe.nullIdentifier;
				} else {
					stackString = StackHelper.makeStringFromItemStack(stack);
				}
				String modStackString = StackHelper.changeStringMeta(stackString, OreDictionary.WILDCARD_VALUE);
				modStackString = StackHelper.stripNBTFromString(modStackString); // NBT-insensitive
				if(itemList.contains(stackString)) {
					itemList.remove(stackString);
					valid++;
				} else if(itemList.contains(modStackString)) {
					itemList.remove(modStackString);
					valid++;
				} else if((matchList = LCMisc.listListContains(oreList, stackString)) != null) {
					oreList.remove(matchList);
					valid++;
				} else if((matchList = LCMisc.listListContains(oreList, modStackString)) != null) {
					oreList.remove(matchList);
					valid++;
				}
			}
			
			// success
			if(valid == 1 + LightningInfusionRecipe.itemsNeeded) {
				lastResultCost = recipe.getCost(); // store the cost
				return recipe.getOutput().copy(); // return a copy of the stack
			}
		}
		lastResultCost = -2;
		return null;
	}
	
	/** Gets the infusion cost for the last result acquired */
	public int getLastResultCost() {
		return lastResultCost;
	}
	
	/** Gets the list of all infusion recipes */
	public final List<LightningInfusionRecipe> getRecipeList() {
		return recipes;
	}
	
}
