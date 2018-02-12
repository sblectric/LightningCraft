package sblectric.lightningcraft.integration.jei.infusion;

import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.recipes.LightningInfusionRecipe;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.api.util.StackHelper;

/** The wrapper for infusion recipes */
public class LightningInfusionRecipeWrapper extends BlankRecipeWrapper {
	
	private final LightningInfusionRecipe recipe;
	
	public LightningInfusionRecipeWrapper(LightningInfusionRecipe recipe) {
		this.recipe = recipe;
	}

	/** Get ALL the inputs */
	public List getInputs() {
		return new JointList<List<ItemStack>>().join(getInfuseItem()).join(getSurroundingItems());
	}
	
	/** Draw the LP cost */
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String need = recipe.getCost() + " LE needed";
		minecraft.fontRenderer.drawString(need, 100 - minecraft.fontRenderer.getStringWidth(need) / 2, 48, 4210752);
	}
	
	/** Get the infuse item */
	public List<ItemStack> getInfuseItem() {
		JointList<ItemStack> result = new JointList();
		String infuse = recipe.getInfuseItem();
		if(OreDictionary.doesOreNameExist(infuse)) {
			result.join(OreDictionary.getOres(infuse));
		} else {
			result.join(StackHelper.makeItemStackFromString(infuse));
		}
		return result;
	}
	
	/** Gets the surrounding items */
	public List<List<ItemStack>> getSurroundingItems() {
		JointList<List<ItemStack>> result = new JointList();
		List<String> surround = recipe.getItems();
		for(String item : surround) {
			JointList<ItemStack> current = new JointList();
			if(OreDictionary.doesOreNameExist(item)) {
				current.join(OreDictionary.getOres(item));
			} else {
				current.join(StackHelper.makeItemStackFromString(item));
			}
			result.add(current);
		}
		return result;
	}

	/** Get the output as a singleton list */
	public List getOutputs() {
		return new JointList().join(recipe.getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}

}
