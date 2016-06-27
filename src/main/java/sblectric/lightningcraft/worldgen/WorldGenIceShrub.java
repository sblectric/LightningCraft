package sblectric.lightningcraft.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.LCBlocks;

/** Generate ice shrubs in the Underworld */
public class WorldGenIceShrub extends WorldGenTrees {
    private final IBlockState iceMetadata;
    private final IBlockState packedIceMetadata;

    public WorldGenIceShrub() {
        super(false);
        this.packedIceMetadata = Blocks.PACKED_ICE.getDefaultState();
        this.iceMetadata = Blocks.PACKED_ICE.getDefaultState();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
    	IBlockState state;
        Block block;
        do {
        	state = worldIn.getBlockState(position);
            block = state.getBlock();
            if (!block.isAir(state, worldIn, position) && !block.isLeaves(state, worldIn, position)) break;
            position = position.down();
        } while (position.getY() > 0);

        Block block1 = worldIn.getBlockState(position).getBlock();
        int meta1 = block1.getMetaFromState(worldIn.getBlockState(position));

        if (block1 == LCBlocks.stoneBlock && meta1 == BlockStone.UNDER) // generate on understone
        {
            position = position.up();
            this.setBlockAndNotifyAdequately(worldIn, position, this.packedIceMetadata);

            for (int i = position.getY(); i <= position.getY() + 2; ++i)
            {
                int j = i - position.getY();
                int k = 2 - j;

                for (int l = position.getX() - k; l <= position.getX() + k; ++l)
                {
                    int i1 = l - position.getX();

                    for (int j1 = position.getZ() - k; j1 <= position.getZ() + k; ++j1)
                    {
                        int k1 = j1 - position.getZ();

                        if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0)
                        {
                            BlockPos blockpos = new BlockPos(l, i, j1);

                            if (worldIn.getBlockState(blockpos).getBlock().canBeReplacedByLeaves(worldIn.getBlockState(blockpos), worldIn, blockpos))
                            {
                                this.setBlockAndNotifyAdequately(worldIn, blockpos, this.iceMetadata);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}