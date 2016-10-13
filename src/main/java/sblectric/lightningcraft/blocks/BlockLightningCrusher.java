package sblectric.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IFurnace;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.items.blocks.ItemBlockRarity;
import sblectric.lightningcraft.main.LightningCraft;
import sblectric.lightningcraft.tiles.TileEntityLightningCrusher;

/** The lightning crusher */
public class BlockLightningCrusher extends BlockContainerLC implements IFurnace {
	
	// the blockstate properties
	private static final PropertyDirection dir = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private static final PropertyBool lit = PropertyBool.create("active");
	private static boolean keepInventory = false;

	/** The lightning furnace */
	public BlockLightningCrusher() {
		super(Blocks.IRON_BLOCK, 3.0f, 30.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(dir, EnumFacing.NORTH).withProperty(lit, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, new IProperty[]{dir, lit});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLightningCrusher();
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack s, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningCrusherGui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		int side = Math.max(2, meta % 6);
		boolean on = meta / 6 > 0;
	    return getDefaultState().withProperty(dir, EnumFacing.getFront(side)).withProperty(lit, on);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    EnumFacing type = state.getValue(dir);
	    return type.getIndex() + 6 * (state.getValue(lit) ? 1 : 0);
	}
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(dir, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(dir, placer.getHorizontalFacing().getOpposite()), 2);
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (!keepInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean isBurning(IBlockState state, IBlockAccess world, BlockPos pos) {
		try {
			return state.getValue(lit);
		} catch(IllegalArgumentException e) { // bug fix
			return false;
		}
	}
	
	@Override
	public void setBurning(IBlockState state, IBlockAccess world, BlockPos pos, boolean burning) {
        TileEntity tile = world.getTileEntity(pos);
        keepInventory = true;
		((World)world).setBlockState(pos, state.withProperty(lit, burning));
		keepInventory = false;
		tile.validate();
		((World)world).setTileEntity(pos, tile);
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return isBurning(state, world, pos) ? 12 : 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if(isBurning(state, world, pos)) {
			EnumFacing enumfacing = state.getValue(dir);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			switch (enumfacing) {
			case WEST:
				world.spawnParticle(EnumParticleTypes.CLOUD, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
				world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
				break;
			default:
			}
		}
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
