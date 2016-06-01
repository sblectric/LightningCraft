package com.lightningcraft.integration.jei.crusher;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.lightningcraft.recipes.LightningCrusherRecipes.LightningCrusherRecipe;
import com.lightningcraft.tiles.TileEntityLightningCrusher;
import com.lightningcraft.util.JointList;
import com.lightningcraft.util.StackHelper;

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

	@Override
	public List getInputs() {
		return new JointList().join(recipe.getInput());
	}

	@Override
	public List getOutputs() {
		return new JointList().join(recipe.getOutput());
	}

}
