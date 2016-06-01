package com.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;

import com.google.common.collect.Multimap;
import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.util.SkyUtils;

/** The Skyfather hammer */
public class ItemSkyHammer extends ItemHammer {

	public ItemSkyHammer(ToolMaterial mat) {
		super(mat);
	}
	
	// speed modifier
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if(equipmentSlot == EntityEquipmentSlot.MAINHAND) SkyUtils.setToolSpeedModifier(this, multimap, ATTACK_SPEED_MODIFIER, attackSpeed);
		return multimap;
	}

}
