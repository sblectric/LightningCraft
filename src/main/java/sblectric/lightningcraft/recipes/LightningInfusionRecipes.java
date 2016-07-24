package sblectric.lightningcraft.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.potions.LCPotions;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.ref.Metal;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.Metal.MBlock;
import sblectric.lightningcraft.util.JointList;
import sblectric.lightningcraft.util.LCMisc;
import sblectric.lightningcraft.util.StackHelper;

/** Infusion recipes rewritten from scratch */
public class LightningInfusionRecipes {
	
	public static final String nullIdentifier = "NULL";
	private static final int itemsNeeded = 4;
	
	/** A single infusion recipe */
	public static class LightningInfusionRecipe {
		
		private ItemStack output;
		private String infuse;
		private List<String> items;
		private int cost;
		
		/** A new infusion recipe with Items, Blocks, ItemStacks, or oreDict entries */
		public LightningInfusionRecipe(ItemStack output, int cost, Object infuse, Object... surrounding) {
			List items = new ArrayList(Arrays.asList(surrounding));
			
			// make sure the size is exact
			if(items.size() > itemsNeeded) {
				throw new IllegalArgumentException("There must be at most " + itemsNeeded + " surrounding items in an infusion recipe.");
			}
			while(items.size() < itemsNeeded) {
				items.add(null); // pad the infusion recipe with nulls
			}
			
			// set the output and cost
			this.output = output;
			this.cost = cost;
			
			// set the infused item
			if(infuse == null) {
				throw new IllegalArgumentException("The infused item cannot be null!");
			} else if(infuse instanceof Block) {
				this.infuse = StackHelper.makeStringFromItemStack(new ItemStack((Block)infuse));
			} else if(infuse instanceof Item) {
				this.infuse = StackHelper.makeStringFromItemStack(new ItemStack((Item)infuse));
			} else {
				this.infuse = StackHelper.makeStringFromItemStack(infuse);
			}
			
			// set the surrounding items
			this.items = new JointList();
			for(Object o : items) {
				if(o == null) {
					this.items.add(nullIdentifier);
				} else if(o instanceof Block) {
					this.items.add(StackHelper.makeStringFromItemStack(new ItemStack((Block)o)));
				} else if(o instanceof Item) {
					this.items.add(StackHelper.makeStringFromItemStack(new ItemStack((Item)o)));
				} else {
					this.items.add(StackHelper.makeStringFromItemStack(o));
				}
			}
		}
		
		/** Gets the ItemStack output of this recipe */
		public ItemStack getOutput() {
			return output;
		}
		
		/** Gets the cost of this infusion */
		public int getCost() {
			return cost;
		}
		
		/** Gets the item to be infused */
		public String getInfuseItem() {
			return infuse;
		}
		
		/** Gets the item to be infused as a list of possible ItemStack strings */
		public List<String> getInfuseItemAsOre() {
			JointList<String> list = new JointList();
			for(ItemStack s : OreDictionary.getOres(infuse)) {
				list.add(StackHelper.makeStringFromItemStack(s));
			}
			return list;
		}
		
		/** Get the items around the item to be infused */
		public List<String> getItems() {
			return items;
		}
		
		/** Get the items around the item to be infused as a list of list of possible ItemStack strings */
		public List<List<String>> getItemsAsOres() {
			JointList<List<String>> list = new JointList();
			for(String name : items) {
				JointList<String> list2 = new JointList();
				for(ItemStack s : OreDictionary.getOres(name)) {
					list2.add(StackHelper.makeStringFromItemStack(s));
				}
				list.add(list2);
			}
			return list;
		}
		
	}
	
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
		addRecipe(new ItemStack(LCBlocks.metalBlock, 1, MBlock.ELEC), 270, "blockIron", "blockGold", "blockDiamond");
		
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
		
		// ensorcelled core
		addRecipe(new ItemStack(LCItems.material, 1, Material.ENSORCELLED), 250, new ItemStack(LCItems.material, 1, Material.CELL_UPGRADE), 
			"dustDiamond", demonBlood, new ItemStack(LCItems.material, 1, Material.UNDER_MEAL), demonBlood);
		
		// potions
		ItemStack normal = LCPotions.getPotionWithType(LCPotions.demonFriendNormal);
		ItemStack extended = LCPotions.getPotionWithType(LCPotions.demonFriendExtended);
		addRecipe(normal, 50, Items.POTIONITEM, demonBlood, Items.GOLDEN_CARROT, demonBlood, Items.GOLDEN_CARROT);
		addRecipe(extended, 25, "potionDemonWarding", LCMisc.makeArray("dustRedstone", 4));
		
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
	public void addRecipe(LightningInfusionRecipe r) {
		recipes.add(r);
		recipeIndex++;
	}
	
	/** Adds a new infusion recipe with Items, Blocks, ItemStacks, or oreDict entries */
	public void addRecipe(ItemStack output, int cost, Object infuse, Object... surrounding) {
		addRecipe(new LightningInfusionRecipe(output, cost, infuse, surrounding));
	}
	
	/** Helper method for hasBaseResult and such */
	private boolean hasResult(ItemStack infuse, String item, List<String> itemOres) {
		String useItem = StackHelper.makeStringFromItemStack(infuse);
		
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
			if(hasResult(infuse, recipe.getInfuseItem(), recipe.getInfuseItemAsOre())) return true;
		}
		return false;
	}
	
	/** Get the infusion result for a specified grid */
	public ItemStack getInfusionResult(ItemStack infuse1, ItemStack... stacks1) {
		// make sure the item grid is the correct size!
		if(stacks1.length != itemsNeeded) return null;
		
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
				itemList.add(item);
			}
			for(List list : recipe.getItemsAsOres()) {
				oreList.add(list);
			}
			
			// now check to see if the infused item matches the recipe
			if(hasResult(infuse, recipe.getInfuseItem(), recipe.getInfuseItemAsOre())) {
				valid++;
			}
			
			// now check the surrounding items
			for(ItemStack stack : stacks) {
				String stackString;
				List matchList;
				if(stack == null) {
					stackString = nullIdentifier;
				} else {
					stackString = StackHelper.makeStringFromItemStack(stack);
				}
				String modStackString = StackHelper.changeStringMeta(stackString, OreDictionary.WILDCARD_VALUE);
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
			if(valid == 1 + itemsNeeded) {
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
	public List<LightningInfusionRecipe> getRecipeList() {
		return recipes;
	}
	
}
