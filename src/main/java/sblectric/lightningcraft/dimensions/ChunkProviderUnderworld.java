package sblectric.lightningcraft.dimensions;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenMinable;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.BlockUnderOre;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.util.LCMisc;
import sblectric.lightningcraft.util.WorldUtils;
import sblectric.lightningcraft.worldgen.MinableHelper;
import sblectric.lightningcraft.worldgen.WorldGenIceShrub;
import sblectric.lightningcraft.worldgen.WorldGenUnderworldLight;
import sblectric.lightningcraft.worldgen.WorldGenUnderworldLiquid;
import sblectric.lightningcraft.worldgen.WorldGenUnderworldTrees;
import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldRampart;
import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldTower;
import sblectric.lightningcraft.worldgen.structure.underworld.MapGenUnderworldWaterTemple;

/** Underworld chunk provider */
public class ChunkProviderUnderworld implements IChunkGenerator {
	
    private Random dimRNG;
    public static final int chunkHeight = 256; // set the height of the world
    /** A NoiseGeneratorOctaves used in generating underworld terrain */
    private NoiseGeneratorOctaves underworldNoiseGen1;
    private NoiseGeneratorOctaves underworldNoiseGen2;
    private NoiseGeneratorOctaves underworldNoiseGen3;
    /** Determines whether grass or stone can be generated at a location */
    private NoiseGeneratorOctaves grassandstoneNoiseGen;
    /** Determines whether something other than understone can be generated at a location */
    private NoiseGeneratorOctaves understoneExculsivityNoiseGen;
    public NoiseGeneratorOctaves underworldNoiseGen6;
    public NoiseGeneratorOctaves underworldNoiseGen7;
    /** Is the world that the underworld is getting generated. */
    private World worldObj;
    private double[] noiseField;
    /** Holds the noise used to determine whether grass and stone can be generated at a location */
    private double[] grassNoise = new double[256];
    private double[] stoneNoise = new double[256];
    /** Holds the noise used to determine whether something other than understone can be generated at a location */
    private double[] understoneExclusivityNoise = new double[256];
    private MapGenBase underworldCaveGenerator = new MapGenCavesHell();
    public MapGenUnderworldWaterTemple genUnderworldWaterTemple = new MapGenUnderworldWaterTemple();
    public MapGenUnderworldTower genUnderworldTower = new MapGenUnderworldTower();
    public MapGenUnderworldRampart genUnderworldRampart = new MapGenUnderworldRampart();
    public static boolean rampartLoadingExtraChunks = false;
        
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    public ChunkProviderUnderworld(World world, long seed)
    {
        this.worldObj = world;
        this.dimRNG = new Random(seed - 1L); // make it different than the Nether
        this.underworldNoiseGen1 = new NoiseGeneratorOctaves(this.dimRNG, 16); // 16
        this.underworldNoiseGen2 = new NoiseGeneratorOctaves(this.dimRNG, 16); // 16
        this.underworldNoiseGen3 = new NoiseGeneratorOctaves(this.dimRNG, 8); // 8
        this.grassandstoneNoiseGen = new NoiseGeneratorOctaves(this.dimRNG, 4); // 4
        this.understoneExculsivityNoiseGen = new NoiseGeneratorOctaves(this.dimRNG, 4); // 4
        this.underworldNoiseGen6 = new NoiseGeneratorOctaves(this.dimRNG, 10); // 10
        this.underworldNoiseGen7 = new NoiseGeneratorOctaves(this.dimRNG, 16); // 16
    }

    public void initBlocks(int chunkX, int chunkZ, IBlockState[] blocks)
    {
        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = chunkHeight / 8 + 1; // 17 or 33
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * b0, 0, chunkZ * b0, k, b2, l);

        for (int i1 = 0; i1 < b0; ++i1)
        {
            for (int j1 = 0; j1 < b0; ++j1)
            {
                for (int k1 = 0; k1 < chunkHeight / 8; ++k1) // k1 < 16
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; ++l1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; ++i2)
                        {
                        	int j2 = i2 + i1 * 4 << (4 + LCMisc.log2(chunkHeight)) | 0 + j1 * 4 << LCMisc.log2(chunkHeight) | k1 * 8 + l1;
                            short short1 = chunkHeight; // 128;
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState block = Blocks.AIR.getDefaultState();

                                if (k1 * 8 + l1 < b1)
                                {
                                    block = Blocks.WATER.getDefaultState();
                                }

                                if (d15 > 0.0D)
                                {
                                    block = LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER);
                                }

                                blocks[j2] = block;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBiomeBlocks(int p_147418_1_, int p_147418_2_, IBlockState[] blocks, Biome[] biomes) {

        short stoneGrassCanGen = chunkHeight - 64;
        double d0 = 0.03125D;
        this.grassNoise = this.grassandstoneNoiseGen.generateNoiseOctaves(this.grassNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.stoneNoise = this.grassandstoneNoiseGen.generateNoiseOctaves(this.stoneNoise, p_147418_1_ * 16, 109, p_147418_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.understoneExclusivityNoise = this.understoneExculsivityNoiseGen.generateNoiseOctaves(this.understoneExclusivityNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                boolean flag = this.grassNoise[k + l * 16] + this.dimRNG.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.stoneNoise[k + l * 16] + this.dimRNG.nextDouble() * 0.4D > 0.0D;
                int i1 = (int)(this.understoneExclusivityNoise[k + l * 16] / 3.0D + 3.0D + this.dimRNG.nextDouble() * 0.25D);
                int j1 = -1;
                IBlockState block = LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER);
                IBlockState block1 = block;

                for (int k1 = chunkHeight - 1; k1 >= 0; --k1)
                {
                    int l1 = (l * 16 + k) * chunkHeight + k1;

                    if (k1 < (chunkHeight - 1) - this.dimRNG.nextInt(5) && k1 > 0 + this.dimRNG.nextInt(5))
                    {
                    	IBlockState block2 = blocks[l1];

                        if (block2 != null && block2.getBlock().getMaterial(block) != Material.AIR)
                        {
                            if (block2 == LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER))
                            {
                                if (j1 == -1)
                                {
                                    if (i1 <= 0)
                                    {
                                        block = Blocks.AIR.getDefaultState();
                                        block1 = LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER);
                                    }
                                    else if (k1 >= stoneGrassCanGen - 4 && k1 <= stoneGrassCanGen + 1)
                                    {
                                        block = LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER);
                                        block1 = block;

                                        if (flag1)
                                        {
                                            block = Blocks.STONE.getDefaultState();
                                            block1 = LCBlocks.corruptStone.getDefaultState();
                                        }

                                        if (flag)
                                        {
                                            block = Blocks.GRASS.getDefaultState();
                                            block1 = Blocks.DIRT.getDefaultState();
                                        }
                                    }

                                    if (k1 < stoneGrassCanGen && (block == null || block.getBlock().getMaterial(block) == Material.AIR))
                                    {
                                        block = Blocks.ICE.getDefaultState();
                                    }

                                    j1 = i1;

                                    if (k1 >= stoneGrassCanGen - 1)
                                    {
                                        blocks[l1] = block;
                                    }
                                    else
                                    {
                                    	if(k1 + 1 < chunkHeight && (blocks[l1 + 1] == null || blocks[l1 + 1].getBlock().getMaterial(block) == Material.AIR)) {
                                    		blocks[l1] = block;
                                    	} else {
                                    		blocks[l1] = block1;
                                    	}
                                    }
                                }
                                else if (j1 > 0)
                                {
                                    --j1;
                                    blocks[l1] = block1;
                                }
                            }
                        }
                        else
                        {
                            j1 = -1;
                        }
                    }
                    else
                    {
                        blocks[l1] = Blocks.BEDROCK.getDefaultState();
                    }
                }
            }
        }
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
	public Chunk provideChunk(int x, int z) {
        this.dimRNG.setSeed(x * 341873128712L + z * 132897987541L);
        ChunkPrimer p = new ChunkPrimer();
        IBlockState[] ablock = new IBlockState[chunkHeight * 256];
        Biome[] abiomegenbase = this.worldObj.getBiomeProvider().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
        this.initBlocks(x, z, ablock);
        this.replaceBiomeBlocks(x, z, ablock, abiomegenbase);
        WorldUtils.primeChunk(p, ablock);
        this.underworldCaveGenerator.generate(this.worldObj, x, z, p);
        this.genUnderworldWaterTemple.generate(this.worldObj, x, z, p);
        this.genUnderworldTower.generate(this.worldObj, x, z, p);
        if(!rampartLoadingExtraChunks) this.genUnderworldRampart.generate(this.worldObj, x, z, p);
        Chunk chunk = new Chunk(this.worldObj, p, x, z);
        byte[] abyte = chunk.getBiomeArray();
        for (int k = 0; k < abyte.length; ++k) {
            abyte[k] = (byte)Biome.getIdForBiome(abiomegenbase[k]);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private double[] initializeNoiseField(double[] par1, int par2, int par3, int par4, int par5, int par6, int par7)
    {

        if (par1 == null)
        {
            par1 = new double[par5 * par6 * par7];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.underworldNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
        this.noiseData5 = this.underworldNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
        this.noiseData1 = this.underworldNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.noiseData2 = this.underworldNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.underworldNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];
        int i2;

        for (i2 = 0; i2 < par6; ++i2)
        {
            adouble1[i2] = Math.cos(i2 * Math.PI * 6.0D / par6) * 2.0D;
            double d2 = i2;

            if (i2 > par6 / 2)
            {
                d2 = par6 - 1 - i2;
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (i2 = 0; i2 < par5; ++i2)
        {
            for (int k2 = 0; k2 < par7; ++k2)
            {
                double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

                if (d3 > 1.0D)
                {
                    d3 = 1.0D;
                }

                double d4 = 0.0D;
                double d5 = this.noiseData5[l1] / 8000.0D;

                if (d5 < 0.0D)
                {
                    d5 = -d5;
                }

                d5 = d5 * 3.0D - 3.0D;

                if (d5 < 0.0D)
                {
                    d5 /= 2.0D;

                    if (d5 < -1.0D)
                    {
                        d5 = -1.0D;
                    }

                    d5 /= 1.4D;
                    d5 /= 2.0D;
                    d3 = 0.0D;
                }
                else
                {
                    if (d5 > 1.0D)
                    {
                        d5 = 1.0D;
                    }

                    d5 /= 6.0D;
                }

                d3 += 0.5D;
                d5 = d5 * par6 / 16.0D;
                ++l1;

                for (int j2 = 0; j2 < par6; ++j2)
                {
                    double d6 = 0.0D;
                    double d7 = adouble1[j2];
                    double d8 = this.noiseData2[k1] / 512.0D;
                    double d9 = this.noiseData3[k1] / 512.0D;
                    double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d10 < 0.0D)
                    {
                        d6 = d8;
                    }
                    else if (d10 > 1.0D)
                    {
                        d6 = d9;
                    }
                    else
                    {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    double d11;

                    if (j2 > par6 - 4)
                    {
                        d11 = (j2 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    if (j2 < d4)
                    {
                        d11 = (d4 - j2) / 4.0D;

                        if (d11 < 0.0D)
                        {
                            d11 = 0.0D;
                        }

                        if (d11 > 1.0D)
                        {
                            d11 = 1.0D;
                        }

                        d6 = d6 * (1.0D - d11) + -10.0D * d11;
                    }

                    par1[k1] = d6;
                    ++k1;
                }
            }
        }

        return par1;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
	public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;

        int k = x * 16;
        int l = z * 16;
        int numGen;
        int j1;
        int k1;
        int l1;
        int i2;
        
        if(RefMisc.DEBUG && rampartLoadingExtraChunks) System.out.println("Loading chunk for rampart");
        
        // gen the structures
	    if(!rampartLoadingExtraChunks) {
	    	try {
		        this.genUnderworldWaterTemple.generateStructure(this.worldObj, this.dimRNG, new ChunkPos(x, z));
		        this.genUnderworldTower.generateStructure(this.worldObj, this.dimRNG, new ChunkPos(x, z));
	    		this.genUnderworldRampart.generateStructure(this.worldObj, this.dimRNG, new ChunkPos(x, z));
	    	} catch(Exception e) {}
        }       
        
        /** generate stuff */
        boolean doGen = true;
        
        // some light sources scattered throughout
        numGen = this.dimRNG.nextInt(this.dimRNG.nextInt(12) + 1) + 4;
        for (j1 = 0; doGen && j1 < numGen; ++j1)
        {
            k1 = k + this.dimRNG.nextInt(16) + 8;
            l1 = this.dimRNG.nextInt(chunkHeight - 8) + 4;
            i2 = l + this.dimRNG.nextInt(16) + 8;
            (new WorldGenUnderworldLight()).generate(this.worldObj, this.dimRNG, new BlockPos(k1, l1, i2));
        }

        // some trees where permitted (with vines!)
        numGen = 96;
        for (j1 = 0; doGen && j1 < numGen; ++j1)
        {
            k1 = k + this.dimRNG.nextInt(16) + 8;
            l1 = this.dimRNG.nextInt(chunkHeight - 8) + 4;
            i2 = l + this.dimRNG.nextInt(16) + 8;
            (new WorldGenUnderworldTrees(false, 6)).generate(this.worldObj, this.dimRNG, new BlockPos(k1, l1, i2));
        }
        
        // some ice shrubs
        numGen = this.dimRNG.nextInt(this.dimRNG.nextInt(3) + 1) + 1;
        for (j1 = 0; doGen && j1 < numGen; ++j1)
        {
            k1 = k + this.dimRNG.nextInt(16) + 8;
            l1 = this.dimRNG.nextInt(chunkHeight - 8) + 4;
            i2 = l + this.dimRNG.nextInt(16) + 8;
            (new WorldGenIceShrub()).generate(this.worldObj, this.dimRNG, new BlockPos(k1, l1, i2));
        }

        // some underworld ores
        WorldGenMinable[] ore = new WorldGenMinable[BlockUnderOre.nVariants];
        ore[BlockUnderOre.IRON] = new WorldGenMinable(LCBlocks.oreBlock.getStateFromMeta(BlockUnderOre.IRON), 20, 
        		new MinableHelper(LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER)));
        ore[BlockUnderOre.GOLD] = new WorldGenMinable(LCBlocks.oreBlock.getStateFromMeta(BlockUnderOre.GOLD), 16, 
        		new MinableHelper(LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER)));
        ore[BlockUnderOre.DIAMOND] = new WorldGenMinable(LCBlocks.oreBlock.getStateFromMeta(BlockUnderOre.DIAMOND), 12, 
        		new MinableHelper(LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER)));
        ore[BlockUnderOre.EMERALD] = new WorldGenMinable(LCBlocks.oreBlock.getStateFromMeta(BlockUnderOre.EMERALD), 8, 
        		new MinableHelper(LCBlocks.stoneBlock.getStateFromMeta(BlockStone.UNDER)));
        
        int frequency[] = new int[BlockUnderOre.nVariants];
        frequency[BlockUnderOre.IRON] = 6;
        frequency[BlockUnderOre.GOLD] = 6;
        frequency[BlockUnderOre.DIAMOND] = 4;
        frequency[BlockUnderOre.EMERALD] = 2;
        int j2;
        int currentOre;
        for(currentOre = 0; currentOre < BlockUnderOre.nVariants; currentOre++) {
            numGen = this.dimRNG.nextInt(this.dimRNG.nextInt((int)(frequency[currentOre] * 3D)) + 1);
	        for (k1 = 0; k1 < numGen; ++k1)
	        {
	            l1 = k + this.dimRNG.nextInt(16);
	            i2 = this.dimRNG.nextInt(chunkHeight - 20) + 10;
	            j2 = l + this.dimRNG.nextInt(16);
	            ore[currentOre].generate(this.worldObj, this.dimRNG, new BlockPos(l1, i2, j2));
	        }
        }

        // some random water
        numGen = this.dimRNG.nextInt(this.dimRNG.nextInt(16) + 1) + 2;
        for (k1 = 0; k1 < numGen; ++k1)
        {
            l1 = k + this.dimRNG.nextInt(16);
            i2 = this.dimRNG.nextInt(chunkHeight - 20) + 10;
            j2 = l + this.dimRNG.nextInt(16);
            (new WorldGenUnderworldLiquid(Blocks.FLOWING_WATER, false)).generate(this.worldObj, this.dimRNG, new BlockPos(l1, i2, j2));
        }

        BlockFalling.fallInstantly = false;
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     * Make sure to wait for the world to load first!
     */
    @Override
	public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
    	boolean canAccessWorld = this.genUnderworldTower.canAccessWorld() && this.genUnderworldWaterTemple.canAccessWorld() && this.genUnderworldRampart.canAccessWorld();
    	   	
    	if(canAccessWorld) {
	        Biome biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
	        return biomegenbase.getSpawnableList(creatureType);
        }
        return null;
    }

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
		this.genUnderworldWaterTemple.generate(this.worldObj, p_180514_2_, p_180514_3_, null);
		this.genUnderworldTower.generate(this.worldObj, p_180514_2_, p_180514_3_, null);
		this.genUnderworldRampart.generate(this.worldObj, p_180514_2_, p_180514_3_, null);
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}
}