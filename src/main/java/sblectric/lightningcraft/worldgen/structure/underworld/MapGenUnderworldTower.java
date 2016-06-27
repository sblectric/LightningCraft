package sblectric.lightningcraft.worldgen.structure.underworld;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.structure.StructureStart;

import sblectric.lightningcraft.entities.EntityUnderworldSkeleton;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.worldgen.structure.LCStructures;
import sblectric.lightningcraft.worldgen.structure.MapGenLCStructure;

/** Tower generation station */
public class MapGenUnderworldTower extends MapGenLCStructure {

	/** Initialize the structure */
    public MapGenUnderworldTower() {
    	super(12, 6);
        this.spawnList.add(new SpawnListEntry(EntityUnderworldSkeleton.class, 150, 3, 5));
    }

    /** Returns the name of the structure */
	@Override
	public String getStructureName() {
		return LCStructures.underworldTowerName;
	}

	/** Get the start of the structure */
	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new MapGenUnderworldTower.Start(this.worldObj, this.rand, x, z);
	}
	
	public static class Start extends StructureStart {

        public Start() {}

        public Start(World world, Random rand, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            
            if(RefMisc.DEBUG) System.out.println("Attempting structure start: " + LCStructures.underworldTowerName);
            UnderworldTower tower = new UnderworldTower(rand, chunkX * 16, chunkZ * 16);
            this.components.add(tower);

            this.updateBoundingBox();
        }
    }

}
