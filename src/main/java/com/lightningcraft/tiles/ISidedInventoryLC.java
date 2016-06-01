package com.lightningcraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.util.text.ITextComponent;

/** Sided inventory interface with Java 8 helpers */
public interface ISidedInventoryLC extends ISidedInventory {
	
	@Override
	public default boolean isUseableByPlayer(EntityPlayer player) {
		TileEntityBase tile = (TileEntityBase)this;
		return tile.getWorld().getTileEntity(tile.getPos()) != tile ? false : 
			player.getDistanceSq(tile.getX() + 0.5D, tile.getY() + 0.5D, tile.getZ() + 0.5D) <= 64.0D;
	}	
	
	@Override
	public default void openInventory(EntityPlayer p) {}

	@Override
	public default void closeInventory(EntityPlayer p) {}
	
	@Override
	public default int getField(int id) {
		return 0;
	}

	@Override
	public default void setField(int id, int value) {}

	@Override
	public default int getFieldCount() {
		return 0;
	}

	@Override
	public default void clear() {}

	@Override
	public default ITextComponent getDisplayName() {
		return null;
	}

}
