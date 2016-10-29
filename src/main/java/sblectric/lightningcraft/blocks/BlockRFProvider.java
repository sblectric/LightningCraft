package sblectric.lightningcraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.blocks.base.BlockCoFH;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.gui.LCGuiHandler;
import sblectric.lightningcraft.tiles.TileEntityRFProvider;

/** The RF provider block */
public class BlockRFProvider extends BlockCoFH {

	public BlockRFProvider() {
		super(Blocks.IRON_BLOCK, 10, 100);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRFProvider();
	}
	
	/** Activate this block */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, 
			EnumHand hand, ItemStack s, EnumFacing side, float hitX, float hitY, float hitZ) {
		player.openGui(LightningCraft.modInstance, LCGuiHandler.lightningLEtoRFGui, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public int getMaxRFPerTick() {
		return TileEntityRFProvider.rfPerTick;
	}

	@Override
	public int getRFLERatio() {
		return LCConfig.RFtoLEConversion;
	}

}
