package sblectric.lightningcraft.crafting;

import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/** Helps with crafting recipe management */
public class RecipeHelper {
	
	/** Add a shaped ore recipe */
	public static void addShapedOreRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
	}
	
	/** Add a shapeless ore recipe */
	public static void addShapelessOreRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
	}
	
	/** Remove all recipes that give 'stackResult' */
	public static void removeRecipes(ItemStack stackResult) {
		List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> remover = allRecipes.iterator();
		while(remover.hasNext()) {
			IRecipe current = remover.next();
			if(ItemStack.areItemStacksEqual(stackResult, current.getRecipeOutput())) {
				remover.remove(); // get rid of it
			}
		}
	}
	
	/** Remove all recipes that give 'stackResult' and can be crafted with 'inventory' */
	public static void removeRecipes(ItemStack stackResult, ItemStack... inventory) {
		List<IRecipe> allRecipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> remover = allRecipes.iterator();
		InventoryCrafting inv = new LCInventoryCrafting(3, 3, inventory);
		while(remover.hasNext()) {
			IRecipe current = remover.next();
			try {
				if(ItemStack.areItemStacksEqual(stackResult, current.getCraftingResult(inv))) {
					remover.remove(); // get rid of it
				}
			} catch(Exception e) {}
		}
	}

}
