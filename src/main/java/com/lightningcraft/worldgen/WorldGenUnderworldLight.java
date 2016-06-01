package com.lightningcraft.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.lightningcraft.blocks.BlockStone;
import com.lightningcraft.blocks.LCBlocks;

/** Generate Underworld lanterns */
public class WorldGenUnderworldLight extends WorldGenerator {
	
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            Block b = worldIn.getBlockState(blockpos.down()).getBlock();
            int meta = b.getMetaFromState(worldIn.getBlockState(blockpos.down()));
            
            if (worldIn.isAirBlock(blockpos) && (b == LCBlocks.stoneBlock && meta == BlockStone.UNDER) || b == Blocks.STONE || b == Blocks.GRASS)
            {
                worldIn.setBlockState(blockpos.down(), LCBlocks.lightBlock.getDefaultState(), 2);
            }
        }

        return true;
    }
}