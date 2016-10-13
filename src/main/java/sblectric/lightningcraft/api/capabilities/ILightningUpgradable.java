package sblectric.lightningcraft.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Capability for tile entities that are upgradable with the Lightning Upgrade */
public interface ILightningUpgradable {
	
	/** Perform the upgrade when it is right-clicked on the related tile's block. Called on both sides */
	public EnumActionResult onLightningUpgrade(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
	
	/** Is the related tile entity currently upgraded with the Lightning Upgrade? */
	public boolean isUpgraded();
	
	/** Set the related tile entity to a specified upgraded state */
	public void setUpgraded(boolean upgraded);

}
