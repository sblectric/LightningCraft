package com.lightningcraft.items;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.util.SkyUtils;

/** The Mystic axe */
public class ItemMysticAxe extends ItemSkyAxe implements IAutoRepair {

	public ItemMysticAxe(ToolMaterial mat) {
		super(mat);
	}

}
