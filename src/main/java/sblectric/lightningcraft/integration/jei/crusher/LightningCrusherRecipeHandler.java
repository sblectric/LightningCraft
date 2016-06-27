package sblectric.lightningcraft.integration.jei.crusher;

import java.util.HashMap;
import java.util.Map;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import sblectric.lightningcraft.recipes.LightningCrusherRecipes.LightningCrusherRecipe;

/** The crusher recipe handler class */
public class LightningCrusherRecipeHandler implements IRecipeHandler<LightningCrusherRecipe> {
	
	private Map<LightningCrusherRecipe, LightningCrusherRecipeWrapper> wrapperMap = new HashMap();

	@Override
	public Class<LightningCrusherRecipe> getRecipeClass() {
		return LightningCrusherRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return LightningCrusherRecipeCategory.UID;
	}
	

	@Override
	public String getRecipeCategoryUid(LightningCrusherRecipe recipe) {
		return getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(LightningCrusherRecipe recipe) {
		if(wrapperMap.containsKey(recipe)) {
			return wrapperMap.get(recipe);
		} else {
			LightningCrusherRecipeWrapper wrap = new LightningCrusherRecipeWrapper(recipe);
			wrapperMap.put(recipe, wrap);
			return wrap;
		}
	}

	@Override
	public boolean isRecipeValid(LightningCrusherRecipe recipe) {
		return !recipe.getInput().isEmpty() && recipe.getOutput() != null;
	}

}
