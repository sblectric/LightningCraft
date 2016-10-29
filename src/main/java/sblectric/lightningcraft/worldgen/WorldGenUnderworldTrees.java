package sblectric.lightningcraft.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import sblectric.lightningcraft.init.LCBlocks;

/** Generate underworld trees */
public class WorldGenUnderworldTrees extends WorldGenAbstractTree {
    
	/** The minimum height of a generated tree. */
    private final int minTreeHeight;
    /** True if this tree should grow Vines. */
    private final boolean vinesGrow;
    /** The metadata value of the wood to use in tree generation. */
    private final IBlockState metaWood;
    /** The metadata value of the leaves to use in tree generation. */
    private final IBlockState metaLeaves;

    public WorldGenUnderworldTrees(boolean setFalse) {
        this(setFalse, 6);
    }

    public WorldGenUnderworldTrees(boolean setFalse, int minTreeHeight) {
        super(setFalse);
        this.minTreeHeight = minTreeHeight;
        this.metaWood = LCBlocks.woodLog.getDefaultState();
        this.metaLeaves = Blocks.LEAVES.getDefaultState();
        this.vinesGrow = true;
    }

    @Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
        int i = rand.nextInt(3) + this.minTreeHeight;
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + i + 1 <= 256)
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                int k = 1;

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            if (!this.isReplaceable(worldIn,blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                BlockPos down = position.down();
                IBlockState state1 = worldIn.getBlockState(down);
                Block block1 = state1.getBlock();
                boolean isSoil = block1.canSustainPlant(state1, worldIn, down, net.minecraft.util.EnumFacing.UP, (IPlantable)Blocks.SAPLING);

                if (isSoil && position.getY() < 256 - i - 1)
                {
                    block1.onPlantGrow(state1, worldIn, down, position);
                    int k2 = 3;
                    int l2 = 0;

                    for (int i3 = position.getY() - k2 + i; i3 <= position.getY() + i; ++i3)
                    {
                        int i4 = i3 - (position.getY() + i);
                        int j1 = l2 + 1 - i4 / 2;

                        for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
                        {
                            int l1 = k1 - position.getX();

                            for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
                            {
                                int j2 = i2 - position.getZ();

                                if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(k1, i3, i2);
                                    IBlockState state = worldIn.getBlockState(blockpos);
                                    Block block = state.getBlock();

                                    if (block.isAir(state, worldIn, blockpos) || block.isLeaves(state, worldIn, blockpos) || block.getMaterial(state) == Material.VINE)
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
                                    }
                                }
                            }
                        }
                    }

                    for (int j3 = 0; j3 < i; ++j3)
                    {
                        BlockPos upN = position.up(j3);
                        IBlockState state2 = worldIn.getBlockState(upN);
                        Block block2 = state2.getBlock();

                        if (block2.isAir(state2, worldIn, upN) || block2.isLeaves(state2, worldIn, upN) || block2.getMaterial(state2) == Material.VINE)
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j3), this.metaWood);

                            if (this.vinesGrow && j3 > 0)
                            {
                                if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, j3, 0)))
                                {
                                    this.func_181651_a(worldIn, position.add(-1, j3, 0), BlockVine.EAST);
                                }

                                if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, j3, 0)))
                                {
                                    this.func_181651_a(worldIn, position.add(1, j3, 0), BlockVine.WEST);
                                }

                                if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, -1)))
                                {
                                    this.func_181651_a(worldIn, position.add(0, j3, -1), BlockVine.SOUTH);
                                }

                                if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, 1)))
                                {
                                    this.func_181651_a(worldIn, position.add(0, j3, 1), BlockVine.NORTH);
                                }
                            }
                        }
                    }

                    if (this.vinesGrow)
                    {
                        for (int k3 = position.getY() - 3 + i; k3 <= position.getY() + i; ++k3)
                        {
                            int j4 = k3 - (position.getY() + i);
                            int k4 = 2 - j4 / 2;
                            BlockPos.MutableBlockPos mbpos = new BlockPos.MutableBlockPos();

                            for (int l4 = position.getX() - k4; l4 <= position.getX() + k4; ++l4)
                            {
                                for (int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; ++i5)
                                {
                                    mbpos.setPos(l4, k3, i5);

                                    if (worldIn.getBlockState(mbpos).getBlock().isLeaves(worldIn.getBlockState(mbpos),worldIn,mbpos))
                                    {
                                        BlockPos blockpos2 = mbpos.west();
                                        BlockPos blockpos3 = mbpos.east();
                                        BlockPos blockpos4 = mbpos.north();
                                        BlockPos blockpos1 = mbpos.south();

                                        if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().isAir(worldIn.getBlockState(blockpos2),worldIn,blockpos2))
                                        {
                                            this.func_181650_b(worldIn, blockpos2, BlockVine.EAST);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().isAir(worldIn.getBlockState(blockpos3),worldIn,blockpos3))
                                        {
                                            this.func_181650_b(worldIn, blockpos3, BlockVine.WEST);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().isAir(worldIn.getBlockState(blockpos4),worldIn,blockpos4))
                                        {
                                            this.func_181650_b(worldIn, blockpos4, BlockVine.SOUTH);
                                        }

                                        if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().isAir(worldIn.getBlockState(blockpos1),worldIn,blockpos1))
                                        {
                                            this.func_181650_b(worldIn, blockpos1, BlockVine.NORTH);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    private void func_181651_a(World p_181651_1_, BlockPos p_181651_2_, PropertyBool p_181651_3_)
    {
        this.setBlockAndNotifyAdequately(p_181651_1_, p_181651_2_, Blocks.VINE.getDefaultState().withProperty(p_181651_3_, Boolean.valueOf(true)));
    }

    private void func_181650_b(World par1, BlockPos par2, PropertyBool par3)
    {
        this.func_181651_a(par1, par2, par3);
        int i = 4;

        for (par2 = par2.down(); par1.getBlockState(par2).getBlock().isAir(par1.getBlockState(par2),par1,par2) && i > 0; --i)
        {
            this.func_181651_a(par1, par2, par3);
            par2 = par2.down();
        }
    }
}