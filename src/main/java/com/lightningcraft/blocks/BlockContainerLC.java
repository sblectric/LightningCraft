package com.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Block container class */
public abstract class BlockContainerLC extends BlockLC implements ITileEntityProvider {
	
	/** The render type of this block container. */
	protected EnumBlockRenderType renderType;
	/** A random number generator */
	protected final Random random = new Random();

	/** A custom render type Tile Entity container. */
	public BlockContainerLC(Block parent, float hardness, float resistance, EnumBlockRenderType renderType) {
		super(parent, hardness, resistance);
		this.renderType = renderType;
		this.isBlockContainer = true;
	}

	/** A standard model Tile Entity container. */
	public BlockContainerLC(Block parent, float hardness, float resistance) {
		this(parent, hardness, resistance, EnumBlockRenderType.MODEL);
	}

	protected boolean isNextToCactusSide(World p_181086_1_, BlockPos p_181086_2_, EnumFacing p_181086_3_) {
		return p_181086_1_.getBlockState(p_181086_2_.offset(p_181086_3_)).getBlock().getMaterial(p_181086_1_.getBlockState(p_181086_2_)) == Material.CACTUS;
	}

	protected boolean isNextToCactus(World p_181087_1_, BlockPos p_181087_2_) {
		return this.isNextToCactusSide(p_181087_1_, p_181087_2_, EnumFacing.NORTH) || this.isNextToCactusSide(p_181087_1_, p_181087_2_, EnumFacing.SOUTH) || 
				this.isNextToCactusSide(p_181087_1_, p_181087_2_, EnumFacing.WEST) || this.isNextToCactusSide(p_181087_1_, p_181087_2_, EnumFacing.EAST);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return renderType;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}
	
	@Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }

}
