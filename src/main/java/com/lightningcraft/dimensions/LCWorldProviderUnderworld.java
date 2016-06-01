package com.lightningcraft.dimensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.biomes.LCBiomes;

/** The Underworld world provider */
public class LCWorldProviderUnderworld extends WorldProvider {
	
	public static final Vec3d skyFogColor = new Vec3d(0.085D, 0.085D, 0.085D);
	
	@Override
	public void createBiomeProvider() {
		this.biomeProvider = new BiomeProviderSingle(LCBiomes.underworld);
		this.setDimension(LCDimensions.underworldID);
		this.hasNoSky = true;
		this.isHellWorld = false; // place some water bruh
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderUnderworld(this.worldObj, this.worldObj.getSeed());
	}
	
	@Override
	public int getActualHeight() {
        return ChunkProviderUnderworld.chunkHeight;
    }
	
	/** Preload 3x3 chunks on player arrival */
//	@Override
//    public void onPlayerAdded(EntityPlayerMP p) {
//		int cx = ((int)p.posX) >> 4;
//		int cz = ((int)p.posZ) >> 4;
//		for(int x = cx - 1; x <= cx + 1; x++) {
//			for(int z = cz - 1; z <= cz + 1; z++) {
//				this.worldObj.getChunkProvider().provideChunk(x, z);
//			}
//		}
//    }
	
	/**
	 * Sky color: dark
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return skyFogColor;
    }
	
	/**
     * Biome fog color: dark
     */
    @Override
	@SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float par1, float par2) {
    	return skyFogColor;
    }

    /**
     * Creates the light to brightness table
     */
    @Override
    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }
    }
	
    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    @Override
    public float calculateCelestialAngle(long par1, float par3) {
        return 0.5F;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    @Override
    public boolean canRespawnHere() {
        return false;
    }

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int par1, int par2) {
        return true;
    }

	@Override
	public DimensionType getDimensionType() {
		return LCDimensions.UNDERWORLD_TYPE;
	}	
	
}
