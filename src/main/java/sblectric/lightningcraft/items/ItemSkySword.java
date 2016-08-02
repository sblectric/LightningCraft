package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import com.google.common.collect.Multimap;
import sblectric.lightningcraft.util.SkyUtils;

/** The Skyfather sword */
public class ItemSkySword extends ItemChargedSword {

	public ItemSkySword(ToolMaterial mat) {
		super(mat);
	}
	
	// speed modifier
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if(equipmentSlot == EntityEquipmentSlot.MAINHAND) SkyUtils.setToolSpeedModifier(this, multimap, ATTACK_SPEED_MODIFIER, SkyUtils.SWORD_ATTACK_SPEED);
		return multimap;
	}

}
