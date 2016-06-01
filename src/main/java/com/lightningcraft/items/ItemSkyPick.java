package com.lightningcraft.items;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Multimap;
import com.lightningcraft.items.ifaces.IAutoRepair;
import com.lightningcraft.ref.LCText;
import com.lightningcraft.util.SkyUtils;

/** The Skyfather pickaxe */
public class ItemSkyPick extends ItemPickaxeLC {

	public ItemSkyPick(ToolMaterial mat) {
		super(mat);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			list.add(LCText.getAutoSmeltLore());
			if(this instanceof IAutoRepair) list.add(LCText.getFortuneBonusLore());
		}
	}

	// speed modifier
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if(equipmentSlot == EntityEquipmentSlot.MAINHAND) SkyUtils.setToolSpeedModifier(this, multimap, ATTACK_SPEED_MODIFIER, attackSpeed);
		return multimap;
	}
	
}
