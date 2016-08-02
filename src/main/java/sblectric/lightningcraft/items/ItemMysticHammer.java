package sblectric.lightningcraft.items;

import sblectric.lightningcraft.items.ifaces.IMysticGear;

/** A hammer that will auto-repair */
public class ItemMysticHammer extends ItemSkyHammer implements IMysticGear {

	public ItemMysticHammer(ToolMaterial mat) {
		super(mat);
	}

}
