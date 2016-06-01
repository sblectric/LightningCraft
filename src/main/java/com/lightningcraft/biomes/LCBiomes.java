package com.lightningcraft.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import com.lightningcraft.config.LCConfig;
import com.lightningcraft.dimensions.LCDimensions;

public class LCBiomes {
	
	public static void mainRegistry() {
		initializeBiomes();
		registerBiomes();
	}
	
	public static Biome underworld;
	public static int underworldID;
	
	private static void initializeBiomes() {
		// the underworld
		underworldID = LCConfig.underworldBiomeID;
		underworld = new BiomeGenUnderworld();
	}

	private static void registerBiomes() {
		// underworld
		Biome.registerBiome(underworldID, LCDimensions.underworldName, underworld);
		BiomeDictionary.registerBiomeType(underworld, Type.SPOOKY);
	}
	
}
