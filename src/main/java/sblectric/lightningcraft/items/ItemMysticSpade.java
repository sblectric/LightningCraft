package sblectric.lightningcraft.items;

import sblectric.lightningcraft.items.ifaces.IMysticGear;

/** A shovel that will auto-repair */
public class ItemMysticSpade extends ItemSkySpade implements IMysticGear {

	public ItemMysticSpade(ToolMaterial mat) {
		super(mat);
	}

}
