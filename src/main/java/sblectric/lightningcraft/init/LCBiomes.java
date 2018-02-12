package sblectric.lightningcraft.init;

import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.biomes.BiomeUnderworld;
import sblectric.lightningcraft.registry.RegistryHelper;

/** mod Biomes */
public class LCBiomes {
	
	private static final List<Biome> BIOMES = RegistryHelper.BIOMES_TO_REGISTER;
	
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
		BIOMES.add(underworld.setRegistryName(LCDimensions.underworldName));
		
	}
	
	public static void setBiomeTypes() {
		BiomeDictionary.addTypes(underworld, Type.SPOOKY);
	}
	
}
