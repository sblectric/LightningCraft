package com.lightningcraft.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.lightningcraft.items.LCItems;
import com.lightningcraft.ref.Metal.Ingot;
import com.lightningcraft.util.JointList;

/** A recipe type that takes input items, and produces a new output, this occurs when the item
 * is struck by lightning */
public class LightningTransformRecipes {
	
	private static LightningTransformRecipes instance = new LightningTransformRecipes();
	private Map<List<ItemStack>, ItemStack> recipeList;
	
	public static LightningTransformRecipes instance() {
		return instance;
	}
	
	private LightningTransformRecipes() {
		this.addDefaultRecipes();
	}
	
	/** Add the default recipes */
	private void addDefaultRecipes() {
		recipeList = new HashMap();
		
		// electricium
		this.addRecipe(new ItemStack(LCItems.ingot, 1, Ingot.ELEC), 
				new JointList().join(new ItemStack(Items.IRON_INGOT)).join(new ItemStack(Items.GOLD_INGOT)).join(new ItemStack(Items.DIAMOND)));
	}
	
	public void addRecipe(ItemStack output, List<ItemStack> input) {
		recipeList.put(input, output);
	}
	
	/** Get the transformation of the input item stacks */
	public ItemStack getTransformResult(List<ItemStack> input) {
		int matches = 0;
		List comp = null;
		ItemStack result = null;
		for(Entry<List<ItemStack>, ItemStack> entry : recipeList.entrySet()) {
			for(ItemStack rIn : entry.getKey()) {
				for(ItemStack iIn : input) {
					if(ItemStack.areItemStacksEqual(rIn, iIn)) {
						matches++;
						result = entry.getValue();
						comp = entry.getKey();
					}
				}
			}
		}
		if(matches == input.size() && matches == comp.size()) {
			return result.copy(); // don't alter this stack!
		} else {
			return null;
		}
	}

}
