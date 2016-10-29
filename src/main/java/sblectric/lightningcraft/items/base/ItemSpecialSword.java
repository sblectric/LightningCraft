package sblectric.lightningcraft.items.base;

import com.google.common.collect.Multimap;

import sblectric.lightningcraft.util.SkyUtils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;

/** The superclass for all special swords */
public abstract class ItemSpecialSword extends ItemSwordLC {
	
	protected double attackSpeed;

	public ItemSpecialSword(ToolMaterial mat, double attackSpeedOffset) {
		super(mat, EnumRarity.RARE);
		this.attackSpeed = SkyUtils.SWORD_ATTACK_SPEED + attackSpeedOffset;
	}
	
	// support for custom attack speeds
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName()); // clear the attack speed
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), 
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
		}

		return multimap;
	}

}
