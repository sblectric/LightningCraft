package sblectric.lightningcraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.api.IInventoryLEUser;

/** Easier LE user interface */
public interface ISimpleLEUser extends IInventoryLEUser {
	
	@Override
	public default double getAvailablePower(EntityPlayer invOwner) {
		return InventoryLE.getAvailablePower(invOwner);
	}
	
	@Override
	public default boolean hasLESource(EntityPlayer invOwner) {
		return InventoryLE.hasLESource(invOwner);
	}
	
	@Override
	public default ItemStack getLESource(EntityPlayer invOwner, double leNeeded) {
		return InventoryLE.getLESource(invOwner, leNeeded);
	}

}
