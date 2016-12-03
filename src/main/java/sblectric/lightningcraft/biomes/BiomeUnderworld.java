package sblectric.lightningcraft.biomes;

import java.awt.Color;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityDemonSoldier;
import sblectric.lightningcraft.entities.EntityUnderworldCreeper;
import sblectric.lightningcraft.entities.EntityUnderworldGhast;
import sblectric.lightningcraft.entities.EntityUnderworldSlime;
import sblectric.lightningcraft.init.LCDimensions;

/** The Underworld biome */
public class BiomeUnderworld extends Biome {
	
	public static final Color natureColor = new Color(44, 90, 128);

	public BiomeUnderworld() {
		super(new BiomeProperties(LCDimensions.underworldName).setRainDisabled().setTemperature(2).setRainfall(0).setWaterColor(0));
		
		this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDemonSoldier.class, 100, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityUnderworldCreeper.class, 30, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityUnderworldSlime.class, 20, 1, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCaveSpider.class, 40, 1, 3));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityUnderworldGhast.class, 20, 1, 1));
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
