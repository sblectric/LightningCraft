package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import sblectric.lightningcraft.items.ifaces.IMysticGear;
import sblectric.lightningcraft.util.SkyUtils;

/** A charged sword that will auto-repair */
public class ItemMysticSword extends ItemSkySword implements IMysticGear {

	public ItemMysticSword(ToolMaterial mat) {
		super(mat);
	}

}
