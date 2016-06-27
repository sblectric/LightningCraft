package sblectric.lightningcraft.dimensions;

import sblectric.lightningcraft.config.LCConfig;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

/** Dimension registry */
public class LCDimensions {
	
	public static void mainRegistry() {
		registerDimensions();
	}
	
	public static final String underworldName = "Underworld";
	public static final int underworldID = LCConfig.underworldDimensionID;
	public static DimensionType UNDERWORLD_TYPE;
	
	private static void registerDimensions() {
		// the underworld
		UNDERWORLD_TYPE = DimensionType.register(underworldName, "_under", underworldID, LCWorldProviderUnderworld.class, false);
		DimensionManager.registerDimension(underworldID, UNDERWORLD_TYPE);
	}

}
