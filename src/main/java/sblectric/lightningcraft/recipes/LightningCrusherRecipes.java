package sblectric.lightningcraft.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import sblectric.lightningcraft.blocks.BlockUnderOre;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.util.JointList;
import sblectric.lightningcraft.util.StackHelper;

/** Class for crusher recipes */
public class LightningCrusherRecipes {
	
	/** An individual crusher recipe */
	public static class LightningCrusherRecipe {
		private Object in;
		private List<ItemStack> excluding;
		private List<ItemStack> inCache;
		private ItemStack out;
		
		public LightningCrusherRecipe(ItemStack out, Object in, ItemStack... excluding) {
			if(!(in instanceof List || in instanceof ItemStack || in instanceof String)) {
				throw new IllegalArgumentException("Crusher recipe input invalid.");
			}
			this.in = in;
			this.excluding = new JointList();
			if(excluding != null) {
				for(ItemStack s : excluding) this.excluding.add(s.copy());
			}
			this.inCache = null;
			this.out = out;
		}
		
		/** Get the input as a raw object */
		private Object getInputRaw() {
			return in;
		}
		
		/** Get the input as a list of ItemStacks (no exclusions) */
		private List<ItemStack> getInputBase() {
			if(in instanceof List) { // list of stacks
				return (List<ItemStack>)in;
			} else if(in instanceof ItemStack) { // single item stack
				return new JointList().join(((ItemStack)in).copy());
			} else { // ore dict
				if(OreDictionary.doesOreNameExist((String)in)) {
					return OreDictionary.getOres((String)in);
				} else {
					return new JointList();
				}
			}
		}
		
		/** Get the input as a list of ItemStacks (with exclusions) */
		public List<ItemStack> getInput() {
			if(inCache == null) { // cached for speed
				List<ItemStack> base = new ArrayList(getInputBase());
				for(ItemStack ex : excluding) {
					Iterator<ItemStack> iterator = base.iterator();
					while(iterator.hasNext()) {
						ItemStack in = iterator.next();
						if(StackHelper.areItemStacksEqualForCrafting(in, ex)) {
							iterator.remove();
						}
					}
				}
				return (inCache = base);
			} else {
				return inCache;
			}
		}
		
		/** Get the output */
		public ItemStack getOutput() {
			return out.copy();
		}
		
		/** Get invalid inputs */
		public List<ItemStack> getExcluding() {
			return excluding;
		}
		
	}
	
	/** The static instance */
	private static LightningCrusherRecipes instance = new LightningCrusherRecipes();
	private int recipeIndex;
	private JointList<LightningCrusherRecipe> recipes = new JointList();
	
	/** Get the static instance */
	public static LightningCrusherRecipes instance() {
		return instance;
	}
	
	/** internal constructor */
	private LightningCrusherRecipes() {
		recipeIndex = 0;
		addDefaultRecipes();
	}
	
	/** Add the mod's default recipes */
	private void addDefaultRecipes() {
		// some useful basic recipes
		addRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.STONE, 1, OreDictionary.WILDCARD_VALUE));
		addRecipe(new ItemStack(Blocks.GRAVEL), "cobblestone");
		addRecipe(new ItemStack(Blocks.SAND), new ItemStack(Blocks.GRAVEL));
		addRecipe(new ItemStack(Blocks.SAND), "blockGlass");
		addRecipe(new ItemStack(Blocks.SAND), "sandstone");
		addRecipe(new ItemStack(Items.GLOWSTONE_DUST, 4), "glowstone");
		addRecipe(new ItemStack(Items.STICK, 16), "logWood");
		addRecipe(new ItemStack(Items.STICK, 4), "plankWood");
		addRecipe(new ItemStack(Items.STICK, 2), "treeSapling");
		addRecipe(new ItemStack(Items.SUGAR, 4), new ItemStack(Items.REEDS));
		addRecipe(new ItemStack(Items.LEATHER, 5), new ItemStack(Items.SADDLE));
		addRecipe(new ItemStack(Items.SNOWBALL, 4), new ItemStack(Blocks.SNOW));
		addRecipe(new ItemStack(Items.SNOWBALL, 6), new ItemStack(Blocks.ICE));
		addRecipe(new ItemStack(Items.SNOWBALL, 8), new ItemStack(Blocks.PACKED_ICE));
		addRecipe(new ItemStack(Items.CLAY_BALL, 4), new ItemStack(Blocks.CLAY));
		addRecipe(new ItemStack(Items.PRISMARINE_SHARD, 4), new ItemStack(Blocks.PRISMARINE, 1, 0));
		addRecipe(new ItemStack(Items.PRISMARINE_SHARD, 9), new ItemStack(Blocks.PRISMARINE, 1, 1));
		addRecipe(new ItemStack(Items.PRISMARINE_SHARD, 8), new ItemStack(Blocks.PRISMARINE, 1, 2));
		addRecipe(new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Items.PRISMARINE_SHARD));
		addRecipe(new ItemStack(Items.PRISMARINE_CRYSTALS, 4), new ItemStack(Blocks.SEA_LANTERN));
		addRecipe(new ItemStack(Items.BRICK, 4), new ItemStack(Blocks.BRICK_BLOCK));
		addRecipe(new ItemStack(Items.NETHERBRICK, 4), new ItemStack(Blocks.NETHER_BRICK));
		addRecipe(new ItemStack(Items.GUNPOWDER, 3), new ItemStack(Items.FIRE_CHARGE));
		addRecipe(new ItemStack(Items.BLAZE_POWDER, 3), new ItemStack(Items.BLAZE_ROD));
		addRecipe(new ItemStack(Items.DYE, 6, 15), new ItemStack(Items.BONE));
		
		// special recipes
		addRecipe(new ItemStack(Items.ENDER_EYE), "nuggetSkyfather");
		addRecipe(new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD), "nuggetMystic");
		addRecipe(new ItemStack(LCItems.material, 6, Material.UNDER_MEAL), new ItemStack(LCItems.material, 1, Material.UNDER_BONE));
		addRecipe(new ItemStack(Items.DYE, 2, 15), new ItemStack(LCItems.material, 1, Material.UNDER_MEAL));
		
		// vanilla ore processing recipes
		addRecipe(new ItemStack(Items.REDSTONE, 6), "oreRedstone");
		addRecipe(new ItemStack(Items.DYE, 9, 4), "oreLapis");
		addRecipe(new ItemStack(LCItems.material, 3, Material.DIAMOND_DUST), "oreDiamond", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.DIAMOND));
		addRecipe(new ItemStack(LCItems.material, 3, Material.EMERALD_DUST), "oreEmerald", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.EMERALD));
		addRecipe(new ItemStack(LCItems.material, 4, Material.QUARTZ_DUST), "oreQuartz");
		
		// modded ore processing recipes
		addRecipe(new ItemStack(LCItems.material, 6, Material.DIAMOND_DUST), new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.DIAMOND));
		addRecipe(new ItemStack(LCItems.material, 6, Material.EMERALD_DUST), new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.EMERALD));
		
		// add the dust recipes
		addDustRecipes();
	}
	
	/** search all ores to dusts, ingots to dusts, and gems to dusts */
	private void addDustRecipes() {
		for(String n : OreDictionary.getOreNames()) {
			boolean ore = n.length() >= 3 && n.substring(0, 3).equals("ore");
			boolean ingot = n.length() >= 5 && n.substring(0, 5).equals("ingot");
			boolean gem = n.length() >= 3 && n.substring(0, 3).equals("gem");
			if(ore || ingot || gem) {
				if(recipeExists(n)) continue;
				String type = ore || gem ? n.substring(3, n.length()) : n.substring(5, n.length());
				for(String m : OreDictionary.getOreNames()) {
					if(m.length() >= 5 && m.substring(0, 4).equals("dust") && m.substring(4, m.length()).equals(type)) {
						try { // watch out for entries with no ItemStacks
							ItemStack result = OreDictionary.getOres(m).get(0).copy(); // the first result will be the output
							if(ore) result.stackSize = 2; // ores give double
							addRecipe(result, n); // add the recipe
						} catch(Exception e) {}
					}
				}
			}
		}
	}
	
	/** check if the recipe with specified input exists */
	public boolean recipeExists(Object input) {
		for(LightningCrusherRecipe r : recipes) {
			if(r.getInputRaw().equals(input)) return true;
		}
		return false;
	}
	
	/** Add a recipe */
	public void addRecipe(LightningCrusherRecipe r) {
		recipes.add(r);
		recipeIndex++;
	}
	
	/** Add a recipe */
	public void addRecipe(ItemStack out, Object in) {
		addRecipe(new LightningCrusherRecipe(out, in));
	}
	
	/** Add a recipe */
	public void addRecipe(ItemStack out, Object in, ItemStack... excluding) {
		addRecipe(new LightningCrusherRecipe(out, in, excluding));
	}
	
	/** Get the crushing result for an input */
	public ItemStack getCrushingResult(ItemStack input) {
		for(LightningCrusherRecipe r : recipes) {
			for(ItemStack s : r.getInput()) {
				if(StackHelper.areItemStacksEqualForCrafting(s, input)) {
					return r.getOutput();
				}
			}
		}
		return null;
	}
	
	/** Gets the list of all crusher recipes */
	public List<LightningCrusherRecipe> getRecipeList() {
		return recipes;
	}

}
