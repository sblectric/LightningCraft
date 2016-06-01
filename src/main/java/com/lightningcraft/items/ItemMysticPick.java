package com.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.util.SkyUtils;

/** The Mystic pickaxe */
public class ItemMysticPick extends ItemSkyPick implements IAutoRepair {

	public ItemMysticPick(ToolMaterial mat) {
		super(mat);
	}

}
