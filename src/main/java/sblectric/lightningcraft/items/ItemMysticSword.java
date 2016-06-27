package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import sblectric.lightningcraft.items.ifaces.IAutoRepair;
import sblectric.lightningcraft.util.SkyUtils;

/** A charged sword that will auto-repair */
public class ItemMysticSword extends ItemSkySword implements IAutoRepair {

	public ItemMysticSword(ToolMaterial mat) {
		super(mat);
	}

}
