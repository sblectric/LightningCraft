package sblectric.lightningcraft.worldgen.structure;

import net.minecraft.world.gen.structure.MapGenStructureIO;

import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldRampart;
import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldTower;
import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldWaterTemple;
import sblectric.lightningcraft.worldgen.structure.underworld.UnderworldRampart;
import sblectric.lightningcraft.worldgen.structure.underworld.UnderworldTower;
import sblectric.lightningcraft.worldgen.structure.underworld.UnderworldWaterTemple;

public class LCStructures {
	
	public static final String underworldWaterTempleName = "UnderworldWaterTemple";
	public static final String underworldTowerName = "UnderworldTower";
	public static final String underworldRampartName = "UnderworldRampart";
	
	public static final String heavenRuinsName = "HeavenRuins";
	public static final String heavenKeepName = "HeavenKeep";
	
	public static void mainRegistry() {
		registerStructures();
	}
	
	private static void registerStructures() {
		// Underworld Water Temple
		MapGenStructureIO.registerStructure(MapGenUnderworldWaterTemple.Start.class, underworldWaterTempleName);
		MapGenStructureIO.registerStructureComponent(UnderworldWaterTemple.class, underworldWaterTempleName + "pc");
		
		// Underworld Tower
		MapGenStructureIO.registerStructure(MapGenUnderworldTower.Start.class, underworldTowerName);
		MapGenStructureIO.registerStructureComponent(UnderworldTower.class, underworldTowerName + "pc");
		
		// Underworld Rampart
		MapGenStructureIO.registerStructure(MapGenUnderworldRampart.Start.class, underworldRampartName);
		MapGenStructureIO.registerStructureComponent(UnderworldRampart.class, underworldRampartName + "pc");
		
	}

}
