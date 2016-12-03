package sblectric.lightningcraft.api;

import java.lang.reflect.Method;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import sblectric.lightningcraft.api.recipes.LightningCrusherRecipe;
import sblectric.lightningcraft.api.recipes.LightningInfusionRecipe;

/** The main API interface class */
public class LightningCraftAPI {
	
	public static final String MODID = "lightningcraft";
	public static final String NAME = "LightningCraft";
	
	/** Adds a crusher recipe via reflection, and returns true if the addition succeeded.
	 *  Should be called in or after the {@link FMLInitializationEvent} phase, and before {@link FMLLoadCompleteEvent}. */
	public static boolean addLightningCrusherRecipe(LightningCrusherRecipe recipe) {
		try {
			Class crusherContainer = Class.forName("sblectric.lightningcraft.recipes.LightningCrusherRecipes");
			Method crusherInstance = crusherContainer.getMethod("instance");
			Method recipeAdder = crusherContainer.getMethod("addRecipe", LightningCrusherRecipe.class);
			recipeAdder.invoke(crusherInstance.invoke(null), recipe);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	/** Adds an infusion recipe via reflection, and returns true if the addition succeeded.
	 *  Should be called in or after the {@link FMLInitializationEvent} phase, and before {@link FMLLoadCompleteEvent}. */
	public static boolean addLightningInfusionRecipe(LightningInfusionRecipe recipe) {
		try {
			Class infusionContainer = Class.forName("sblectric.lightningcraft.recipes.LightningInfusionRecipes");
			Method infusionInstance = infusionContainer.getMethod("instance");
			Method recipeAdder = infusionContainer.getMethod("addRecipe", LightningInfusionRecipe.class);
			recipeAdder.invoke(infusionInstance.invoke(null), recipe);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}
