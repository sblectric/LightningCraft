package sblectric.lightningcraft.integration.jei.infusion;

import java.util.HashMap;
import java.util.Map;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import sblectric.lightningcraft.recipes.LightningInfusionRecipes.LightningInfusionRecipe;

/** The infusion recipe handler class */
public class LightningInfusionRecipeHandler implements IRecipeHandler<LightningInfusionRecipe> {
	
	private Map<LightningInfusionRecipe, LightningInfusionRecipeWrapper> wrapperMap = new HashMap();

	@Override
	public Class<LightningInfusionRecipe> getRecipeClass() {
		return LightningInfusionRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return LightningInfusionRecipeCategory.UID;
	}
	
	@Override
	public String getRecipeCategoryUid(LightningInfusionRecipe recipe) {
		return getRecipeCategoryUid();
	}

	public LightningInfusionRecipeWrapper wrapUpdate(LightningInfusionRecipe recipe) {
		if(wrapperMap.containsKey(recipe)) {
			return wrapperMap.get(recipe);
		} else {
			LightningInfusionRecipeWrapper wrap = new LightningInfusionRecipeWrapper(recipe);
			wrapperMap.put(recipe, wrap);
			return wrap;
		}
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(LightningInfusionRecipe recipe) {
		return wrapUpdate(recipe);
	}

	@Override
	public boolean isRecipeValid(LightningInfusionRecipe recipe) {
		return !wrapUpdate(recipe).getInfuseItem().isEmpty() && !wrapUpdate(recipe).getSurroundingItems().isEmpty() && !wrapUpdate(recipe).getOutputs().isEmpty();
	}

}
