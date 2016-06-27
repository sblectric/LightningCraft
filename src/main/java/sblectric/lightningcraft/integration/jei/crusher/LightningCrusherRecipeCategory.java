package sblectric.lightningcraft.integration.jei.crusher;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningCrusher;

/** The category for crusher recipes */
public class LightningCrusherRecipeCategory extends BlankRecipeCategory {
	
	public static final String UID = RefStrings.MODID + ".crusherRecipeCategory";
	public static final String NAME = I18n.translateToLocal(UID);
	private static final int INPUT = 0;
	private static final int OUTPUT = 1;
	public static final ResourceLocation location = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpfurnace.png");
	
	private IDrawable background;
	private IDrawableAnimated flame;
	private IDrawableAnimated arrow;
	
	public LightningCrusherRecipeCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(location, 55, 16, 82, 54);
		IDrawableStatic flameDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, TileEntityLightningCrusher.burnTime, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, TileEntityLightningCrusher.burnTime / 2, IDrawableAnimated.StartDirection.LEFT, false);
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
	public void drawAnimations(Minecraft minecraft) {
		flame.draw(minecraft, 2, 20);
		arrow.draw(minecraft, 24, 18);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(INPUT, true, 0, 0);
		stacks.init(OUTPUT, false, 60, 18);
		
		if (recipeWrapper instanceof LightningCrusherRecipeWrapper) {
			LightningCrusherRecipeWrapper lirWrapper = (LightningCrusherRecipeWrapper)recipeWrapper;
			stacks.set(INPUT, lirWrapper.getInputs());
			stacks.set(OUTPUT, lirWrapper.getOutputs());
		}
	}

}
