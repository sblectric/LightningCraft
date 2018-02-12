package sblectric.lightningcraft.integration.jei.crusher;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.api.recipes.LightningCrusherRecipe;
import sblectric.lightningcraft.api.util.JointList;

/** The wrapper for crusher recipes */
public class LightningCrusherRecipeWrapper extends BlankRecipeWrapper {
	
	private final LightningCrusherRecipe recipe;
	
	public LightningCrusherRecipeWrapper(LightningCrusherRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		// hide the LE bar
		minecraft.getTextureManager().bindTexture(LightningCrusherRecipeCategory.location);
		minecraft.currentScreen.drawTexturedModalRect(0, 36, 64, 6, 27, 8);
	}

	public List getInputs() {
		return new JointList().join(recipe.getInput());
	}

	public List getOutputs() {
		return new JointList().join(recipe.getOutput());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}

}
