package com.lightningcraft.biomes;

import java.awt.Color;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.dimensions.LCDimensions;
import com.lightningcraft.entities.EntityDemonSoldier;
import com.lightningcraft.entities.EntityUnderworldSlime;

/** The Underworld biome */
public class BiomeGenUnderworld extends Biome {
	
	public static final Color natureColor = new Color(44, 90, 128);

	public BiomeGenUnderworld() {
		super(new BiomeProperties(LCDimensions.underworldName).setRainDisabled().setTemperature(2).setRainfall(0).setWaterColor(0));
		
		this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDemonSoldier.class, 100, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityUnderworldSlime.class, 20, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCaveSpider.class, 40, 1, 3));
	}
	
	/** Provides the basic grass color */
    @SideOnly(Side.CLIENT)
    @Override
    public int getModdedBiomeGrassColor(int i) {
        return natureColor.getRGB();
    }
    
    /** Provides the basic foliage color */
    @SideOnly(Side.CLIENT)
    @Override
    public int getModdedBiomeFoliageColor(int i) {
        return natureColor.getRGB();
    }
	
}
