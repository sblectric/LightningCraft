package com.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.util.SkyUtils;

/** A shovel that will auto-repair */
public class ItemMysticSpade extends ItemSkySpade implements IAutoRepair {

	public ItemMysticSpade(ToolMaterial mat) {
		super(mat);
	}

}
