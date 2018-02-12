package sblectric.lightningcraft.worldgen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;

import sblectric.lightningcraft.ref.RefMisc;

public abstract class MapGenLCStructure extends MapGenStructure {
	
	protected List spawnList;
	protected int maxDistanceBetweenStructures;
	protected int minDistanceBetweenStructures;
	protected int seedDistance;

	/** Initialize the structure */
    public MapGenLCStructure(int maxDistanceBetweenStructures, int minDistanceBetweenStructures, int seedModifier) {
    	this.spawnList = new ArrayList();
        this.maxDistanceBetweenStructures = maxDistanceBetweenStructures;
        this.minDistanceBetweenStructures = minDistanceBetweenStructures;
        this.seedDistance = 14357617 + seedModifier;
    }
    
    /** Initialize the structure */
    public MapGenLCStructure(int maxDistanceBetweenStructures, int minDistanceBetweenStructures) {
    	this(maxDistanceBetweenStructures, minDistanceBetweenStructures, 0);
    }
	
	public List getSpawnList() {
		return this.spawnList;
	}

	/** Can the structure be spawned here? */
	@Override
	protected boolean canSpawnStructureAtCoords(int x, int z) {
        int k = x;
        int l = z;

        if (x < 0)
        {
            x -= this.maxDistanceBetweenStructures - 1;
        }

        if (z < 0)
        {
            z -= this.maxDistanceBetweenStructures - 1;
        }

        int i1 = x / this.maxDistanceBetweenStructures;
        int j1 = z / this.maxDistanceBetweenStructures;
        Random random = this.world.setRandomSeed(i1, j1, this.seedDistance);
        i1 *= this.maxDistanceBetweenStructures;
        j1 *= this.maxDistanceBetweenStructures;
        i1 += random.nextInt(this.maxDistanceBetweenStructures - this.minDistanceBetweenStructures);
        j1 += random.nextInt(this.maxDistanceBetweenStructures - this.minDistanceBetweenStructures);

        if (k == i1 && l == j1) {
        	if(RefMisc.DEBUG) System.out.println("Structure gen possible for chunk ("+k+","+l+")");
            return true;
        } else {
        	return false;
        }
    }
	
	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		return null;
	}
	
	/** Wrapper */
	public boolean hasStructureAt(BlockPos pos) {
		return isInsideStructure(pos);
	}
	
	/** more safety */
	public boolean canAccessWorld() {
		return this.world != null;
	}

    /** Added for safety */
	@Override
    public boolean isInsideStructure(BlockPos pos) {
        if(this.canAccessWorld()) {
        	return super.isInsideStructure(pos);
        } else {
        	return false;
        }
    }
}
