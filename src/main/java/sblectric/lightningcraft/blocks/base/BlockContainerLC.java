package sblectric.lightningcraft.blocks.base;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/** Block container class */
public abstract class BlockContainerLC extends BlockLC {
	
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
	
	// it definitely has a tile entity
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return createNewTileEntity(world, this.getMetaFromState(state));
	}
	
	/** Create a new tile entity (wrapper method) */
	public abstract TileEntity createNewTileEntity(World world, int meta);

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
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
	    return new ItemStack(this);
	}

}
