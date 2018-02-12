package sblectric.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.blocks.base.BlockContainerLCMeta;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.items.blocks.ItemBlockWireless;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;
import sblectric.lightningcraft.tiles.TileEntityLightningTransmitter;

/** The wireless LE block */
public class BlockWireless extends BlockContainerLCMeta {
	
	public static final int nTransmitters = 3;
	public static final int nReceivers = 3;
	public static final int nSubBlocks = nTransmitters + nReceivers;

	public BlockWireless() {
		super(Blocks.IRON_BLOCK, nSubBlocks, 5, 30);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		WirelessSpecs params = new WirelessSpecs();
		if(getTileEntityPerformance(meta, params)) { // transmitter
			return new TileEntityLightningTransmitter(params.range, params.quality);
		} else { // receiver
			return new TileEntityLightningReceiver(params.rolloff);
		}
	}
	
	/** loads range, quality, and rolloff performance into parameters and returns true if it's a transmitter */
	public boolean getTileEntityPerformance(int meta, WirelessSpecs params) {
		// different qualities for different materials
		if(meta < nTransmitters) { // transmitter
			switch(meta) {
			case 0:
				params.range = 32;
				params.quality = 0.45;
				break;
			case 1:
				params.range = 48;
				params.quality = 0.65;
				break;
			case 2:
				params.range = 64;
				params.quality = 0.95;
				break;
			default:
				params.range = 0;
				params.quality = 1d;
				break;
			}
			return true; // transmitter
		} else { // receiver
			switch(meta - nTransmitters) {
			case 0:
				params.rolloff = 0.00025;
				break;
			case 1:
				params.rolloff = 0.00015;
				break;
			case 2:
				params.rolloff = 0.000075;
				break;
			default:
				params.rolloff = 1d;
				break;
			}
			return false; // receiver
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
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList list) {
	    for (int i = 0; i < nSubBlocks; i++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
	
	/** Get the dropped metadata */
	@Override
	public int damageDropped(IBlockState state) {
		return this.getMetaFromState(state);
	}
	
	/** Open the GUI when appropriate */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(this.getMetaFromState(state) >= nTransmitters) {
			player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningRXGui, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else {
			return false;
		}
	}
	
	/** Tile entity specs */
	public static class WirelessSpecs {
		public double range;
		public double quality;
		public double rolloff;
	}
	
	@Override
	public Class getItemClass() {
		return ItemBlockWireless.class;
	}

}
