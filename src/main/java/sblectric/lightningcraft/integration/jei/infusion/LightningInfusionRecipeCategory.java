package sblectric.lightningcraft.integration.jei.infusion;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;

/** The category for infusion recipes */
public class LightningInfusionRecipeCategory extends BlankRecipeCategory {
	
	public static final String UID = RefStrings.MODID + ".infusion_recipe_category";
	public static final String NAME = I18n.translateToLocal(UID);
	private static final int INFUSE = 0;
	private static final int[] SURROUND = {1, 2, 3, 4};
	private static final int OUTPUT = 5;
	public static final ResourceLocation location = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpinfuser.png");
	
	private IDrawable background;
	private IDrawableAnimated arrow;
	
	public LightningInfusionRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(location, 8, 20, 128, 60, 0, 0, 0, 0);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, TileEntityLightningInfuser.burnTime, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 68, 20);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INFUSE, true, 25, 20);
		stacks.init(SURROUND[0], true, 25, 0);
		stacks.init(SURROUND[1], true, 45, 20);
		stacks.init(SURROUND[2], true, 25, 40);
		stacks.init(SURROUND[3], true, 5, 20);
		stacks.init(OUTPUT, false, 102, 15);
		
		if (recipeWrapper instanceof LightningInfusionRecipeWrapper) {
			LightningInfusionRecipeWrapper lirWrapper = (LightningInfusionRecipeWrapper)recipeWrapper;
			stacks.set(INFUSE, lirWrapper.getInfuseItem());
			for(int i = 0; i < SURROUND.length; i++) {
				stacks.set(SURROUND[i], lirWrapper.getSurroundingItems().get(i));
			}
			stacks.set(OUTPUT, (ItemStack)lirWrapper.getOutputs().get(0));
		}
	}

}
