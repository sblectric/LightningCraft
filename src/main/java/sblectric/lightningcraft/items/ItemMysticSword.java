package sblectric.lightningcraft.items;

import sblectric.lightningcraft.items.ifaces.IMysticGear;

/** A charged sword that will auto-repair */
public class ItemMysticSword extends ItemSkySword implements IMysticGear {

	public ItemMysticSword(ToolMaterial mat) {
		super(mat);
	}

}
