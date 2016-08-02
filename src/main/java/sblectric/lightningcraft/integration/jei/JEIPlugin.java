package sblectric.lightningcraft.integration.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.gui.client.GuiLightningCrusher;
import sblectric.lightningcraft.gui.client.GuiLightningFurnace;
import sblectric.lightningcraft.gui.client.GuiLightningInfuser;
import sblectric.lightningcraft.integration.jei.crusher.LightningCrusherRecipeCategory;
import sblectric.lightningcraft.integration.jei.crusher.LightningCrusherRecipeHandler;
import sblectric.lightningcraft.integration.jei.infusion.LightningInfusionRecipeCategory;
import sblectric.lightningcraft.integration.jei.infusion.LightningInfusionRecipeHandler;
import sblectric.lightningcraft.recipes.LightningCrusherRecipes;
import sblectric.lightningcraft.recipes.LightningInfusionRecipes;
import sblectric.lightningcraft.ref.Log;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
	
	/** Register this mod plugin with the mod registry. */
	@Override
	public void register(IModRegistry reg) {
		if(LCConfig.JEIIntegration) {
			// register the categories
			reg.addRecipeCategories(
					new LightningCrusherRecipeCategory(reg.getJeiHelpers().getGuiHelper()),
					new LightningInfusionRecipeCategory(reg.getJeiHelpers().getGuiHelper())
			);
			
			// and the handlers
			reg.addRecipeHandlers(
					new LightningCrusherRecipeHandler(),
					new LightningInfusionRecipeHandler()
			);
			
			// add the recipes
			reg.addRecipes(LightningCrusherRecipes.instance().getRecipeList());
			reg.addRecipes(LightningInfusionRecipes.instance().getRecipeList());
			
			// add the recipe click areas
			reg.addRecipeClickArea(GuiLightningFurnace.class, 79, 34, 24, 16, VanillaRecipeCategoryUid.SMELTING);
			reg.addRecipeClickArea(GuiLightningCrusher.class, 79, 34, 24, 16, LightningCrusherRecipeCategory.UID);
			reg.addRecipeClickArea(GuiLightningInfuser.class, 75, 40, 24, 16, LightningInfusionRecipeCategory.UID);
			
			// set the category icons
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningFurnace), VanillaRecipeCategoryUid.SMELTING);
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningCrusher), LightningCrusherRecipeCategory.UID);
			reg.addRecipeCategoryCraftingItem(new ItemStack(LCBlocks.lightningInfuser), LightningInfusionRecipeCategory.UID);
			
			Log.logger.info("JEI integration complete.");
		} else {
			Log.logger.info("JEI integration disabled.");
		}
	}
 
	@Override
	public void onRuntimeAvailable(IJeiRuntime arg0) {
		
	}

}
