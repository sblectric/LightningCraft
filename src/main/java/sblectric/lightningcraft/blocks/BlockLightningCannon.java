package sblectric.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.blocks.base.BlockContainerLCMeta;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.blocks.ItemBlockLightningCannon;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.tiles.TileEntityLightningCannon;

/** The powerful Underworld / Lightning cannon */
public class BlockLightningCannon extends BlockContainerLCMeta {
	
	public static final int nCannons = 3;

	public BlockLightningCannon() {
		super(Blocks.IRON_BLOCK, nCannons, 5, 150);
		this.setSoundType(SoundType.METAL);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 1.0F, 0.8F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLightningCannon();
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(getMetaFromState(state) > 0) {
			player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningCannonGui, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else {
			return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
		}
	}
	
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	/**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }
    
    /** It drops either a cannon core (wild) or itself (tame) */
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
    	if(getMetaFromState(state) == 0) {
    		return LCItems.material;
    	} else {
    		return Item.getItemFromBlock(this);
    	}
    }
    
	/** Get the dropped metadata */
	@Override
	public int damageDropped(IBlockState state) {
		if(getMetaFromState(state) == 0) {
			return Material.CANNON_CORE;
		} else {
    		return getMetaFromState(state);
    	}
	}
    
    /** And never itself in the wild */
    @Override
    protected boolean canSilkHarvest() {
    	return false;
    }
	
	@Override
	public Class getItemClass() {
		return ItemBlockLightningCannon.class;
	}

}
