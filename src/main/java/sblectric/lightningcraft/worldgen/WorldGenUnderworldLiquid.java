package sblectric.lightningcraft.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.util.WorldUtils;

/** Generate underworld liquid falls */
public class WorldGenUnderworldLiquid extends WorldGenerator {
	
    private final Block liquid;
    private final boolean cancel;

    public WorldGenUnderworldLiquid(Block liquid, boolean cancel) {
        this.liquid = liquid;
        this.cancel = cancel;
    }

    @Override
	public boolean generate(World world, Random rand, BlockPos pos) {
        if (!WorldUtils.blockMatches(world, pos.up(), LCBlocks.stoneBlock, BlockStone.UNDER)) {
            return false;
        } else if (world.getBlockState(pos).getBlock().getMaterial(world.getBlockState(pos)) != Material.AIR && 
        		!WorldUtils.blockMatches(world, pos.up(), LCBlocks.stoneBlock, BlockStone.UNDER)) {
            return false;
        } else {
            int i = 0;

            if(WorldUtils.blockMatches(world, pos.west(), LCBlocks.stoneBlock, BlockStone.UNDER))
            {
                ++i;
            }

            if(WorldUtils.blockMatches(world, pos.east(), LCBlocks.stoneBlock, BlockStone.UNDER))
            {
                ++i;
            }

            if(WorldUtils.blockMatches(world, pos.north(), LCBlocks.stoneBlock, BlockStone.UNDER))
            {
                ++i;
            }

            if(WorldUtils.blockMatches(world, pos.south(), LCBlocks.stoneBlock, BlockStone.UNDER))
            {
                ++i;
            }

            if(WorldUtils.blockMatches(world, pos.down(), LCBlocks.stoneBlock, BlockStone.UNDER))
            {
                ++i;
            }

            int j = 0;

            if (world.isAirBlock(pos.west()))
            {
                ++j;
            }

            if (world.isAirBlock(pos.east()))
            {
                ++j;
            }

            if (world.isAirBlock(pos.north()))
            {
                ++j;
            }

            if (world.isAirBlock(pos.south()))
            {
                ++j;
            }

            if (world.isAirBlock(pos.down()))
            {
                ++j;
            }

            if (!this.cancel && i == 4 && j == 1 || i == 5)
            {
            	IBlockState iblockstate = this.liquid.getDefaultState();
                world.setBlockState(pos, iblockstate, 2);
                world.immediateBlockTick(pos, iblockstate, rand);
            }

            return true;
        }
    }
}