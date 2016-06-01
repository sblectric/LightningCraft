package com.lightningcraft.integration.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import com.lightningcraft.blocks.LCBlocks;
import com.lightningcraft.config.LCConfig;
import com.lightningcraft.gui.client.GuiLightningCrusher;
import com.lightningcraft.gui.client.GuiLightningFurnace;
import com.lightningcraft.gui.client.GuiLightningInfuser;
import com.lightningcraft.integration.jei.crusher.LightningCrusherRecipeCategory;
import com.lightningcraft.integration.jei.crusher.LightningCrusherRecipeHandler;
import com.lightningcraft.integration.jei.infusion.LightningInfusionRecipeCategory;
import com.lightningcraft.integration.jei.infusion.LightningInfusionRecipeHandler;
import com.lightningcraft.recipes.LightningCrusherRecipes;
import com.lightningcraft.recipes.LightningInfusionRecipes;
import com.lightningcraft.ref.Log;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
	
	/** Register this mod plugin with the mod registry. */
	@Override
	public void register(IModRegistry reg) {
		if(LCConfig.JEIIntegration) {
			// register the categories
			reg.addRecipeCategories(
					new LightningInfusionRecipeCategory(reg.getJeiHelpers().getGuiHelper()),
					new LightningCrusherRecipeCategory(reg.getJeiHelpers().getGuiHelper())
			);
			
			// and the handlers
			reg.addRecipeHandlers(
					new LightningInfusionRecipeHandler(),
					new LightningCrusherRecipeHandler()
			);
			
			// add the recipes
			reg.addRecipes(LightningInfusionRecipes.instance().getRecipeList());
			reg.addRecipes(LightningCrusherRecipes.instance().getRecipeList());
			
			// add the recipe click areas
			reg.addRecipeClickArea(GuiLightningInfuser.class, 75, 40, 24, 16, LightningInfusionRecipeCategory.UID);
			reg.addRecipeClickArea(GuiLightningCrusher.class, 79, 34, 24, 16, LightningCrusherRecipeCategory.UID);
			reg.addRecipeClickArea(GuiLightningFurnace.class, 79, 34, 24, 16, VanillaRecipeCategoryUid.SMELTING);
			
			// set the category icons
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningInfuser), LightningInfusionRecipeCategory.UID);
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningCrusher), LightningCrusherRecipeCategory.UID);
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningFurnace), VanillaRecipeCategoryUid.SMELTING);
			
			Log.logger.info("JEI integration complete.");
		} else {
			Log.logger.info("JEI integration disabled.");
		}
	}
 
	@Override
	public void onRuntimeAvailable(IJeiRuntime arg0) {
		
	}

}
