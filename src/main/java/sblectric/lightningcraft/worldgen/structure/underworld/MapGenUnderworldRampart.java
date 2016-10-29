package sblectric.lightningcraft.worldgen.structure.underworld;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.structure.StructureStart;

import sblectric.lightningcraft.entities.EntityUnderworldSilverfish;
import sblectric.lightningcraft.init.LCStructures;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.worldgen.structure.MapGenLCStructure;

/** The rampart generator */
public class MapGenUnderworldRampart extends MapGenLCStructure {

	/** Initialize the structure */
    public MapGenUnderworldRampart() {
    	super(13, 9);
        this.spawnList.add(new SpawnListEntry(EntityUnderworldSilverfish.class, 100, 1, 1));
    }

    /** Returns the name of the structure */
	@Override
	public String getStructureName() {
		return LCStructures.underworldRampartName;
	}

	/** Get the start of the structure */
	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new MapGenUnderworldRampart.Start(this.worldObj, this.rand, x, z);
	}
	
	public static class Start extends StructureStart {

        public Start() {}

        public Start(World world, Random rand, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            
            if(RefMisc.DEBUG) System.out.println("Attempting structure start: " + LCStructures.underworldRampartName);
            UnderworldRampart rampart = new UnderworldRampart(rand, chunkX * 16, chunkZ * 16);
            this.components.add(rampart);

            this.updateBoundingBox();
        }
    }

}
