package sblectric.lightningcraft.blocks;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sblectric.lightningcraft.items.blocks.ItemBlockRarity;
import sblectric.lightningcraft.tiles.TileEntityLightningBreaker;

import com.google.common.base.Predicate;

/** The Lightning Block Breaker */
public class BlockLightningBreaker extends BlockContainerLC {
	
	// the blockstate properties
	private static final PropertyDirection dir = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
		@Override
		public boolean apply(EnumFacing facing) {
			return facing != null;
		}
	});

	public BlockLightningBreaker() {
		super(Blocks.IRON_BLOCK, 3.0f, 30.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(dir, EnumFacing.NORTH));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[]{dir});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLightningBreaker();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    return getDefaultState().withProperty(dir, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(dir).getIndex();
	}
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		int rotation = BlockPistonBase.getFacingFromEntity(pos, placer).ordinal();
        return this.getStateFromMeta(rotation);
    }

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
        int rotation = BlockPistonBase.getFacingFromEntity(pos, player).ordinal();
        world.setBlockState(pos, this.getStateFromMeta(rotation), 2);
        super.onBlockPlacedBy(world, pos, state, player, stack);
    }
	
	@Override
	public Class getItemClass() {
		return ItemBlockRarity.class;
	}
	
	@Override
	public Object[] getItemClassArgs() {
		return new Object[]{EnumRarity.UNCOMMON};
	}

}
