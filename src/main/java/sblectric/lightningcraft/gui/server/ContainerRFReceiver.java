package sblectric.lightningcraft.gui.server;

import net.minecraft.entity.player.InventoryPlayer;

import sblectric.lightningcraft.tiles.TileEntityEnergy;

/** RF receiver container */
public class ContainerRFReceiver extends ContainerRFProvider {

	public ContainerRFReceiver(InventoryPlayer player, TileEntityEnergy tile) {
		super(player, tile);
	}
	
}
