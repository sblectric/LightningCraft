package sblectric.lightningcraft.tiles.ifaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Interface for tile entities that are upgradable with the Lightning Upgrade */
public interface ILightningUpgradable {
	
	/** Perform the upgrade when it is right-clicked on this tile's block. Called on both sides */
	public EnumActionResult onLightningUpgrade(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
	
	/** Is this tile entity currently upgraded with the Lightning Upgrade? */
	public boolean isUpgraded();
	
	/** Set this tile entity to an upgraded state (mostly for client syncing) */
	public void setUpgraded(boolean upgraded);

}
