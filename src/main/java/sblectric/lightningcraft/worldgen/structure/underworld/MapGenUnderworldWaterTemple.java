package sblectric.lightningcraft.worldgen.structure.underworld;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;
import sblectric.lightningcraft.init.LCStructures;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.worldgen.structure.MapGenLCStructure;

/** Templegen */
public class MapGenUnderworldWaterTemple extends MapGenLCStructure {

	/** Initialize the structure */
    public MapGenUnderworldWaterTemple()
    {
    	super(10, 7);
    }

    /** Returns the name of the structure */
	@Override
	public String getStructureName() {
		return LCStructures.underworldWaterTempleName;
	}

	/** Get the start of the structure */
	@Override
	protected StructureStart getStructureStart(int x, int z) {
		return new MapGenUnderworldWaterTemple.Start(this.world, this.rand, x, z);
	}
	
	public static class Start extends StructureStart {

        public Start() {}

        public Start(World world, Random rand, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            
            if(RefMisc.DEBUG) System.out.println("Attempting structure start: " + LCStructures.underworldWaterTempleName);
            UnderworldWaterTemple temple = new UnderworldWaterTemple(rand, chunkX * 16, chunkZ * 16);
            this.components.add(temple);

            this.updateBoundingBox();
        }
    }

}
