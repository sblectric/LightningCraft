package sblectric.lightningcraft.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.biomes.BiomeUnderworld;

public class LCBiomes {
	
	public static void mainRegistry() {
		initializeBiomes();
		registerBiomes();
	}
	
	public static Biome underworld;
	
	private static void initializeBiomes() {
		// the underworld
		underworld = new BiomeUnderworld();
	}

	private static void registerBiomes() {
		// underworld
		GameRegistry.register(underworld.setRegistryName(LCDimensions.underworldName));
		BiomeDictionary.registerBiomeType(underworld, Type.SPOOKY);
	}
	
}
