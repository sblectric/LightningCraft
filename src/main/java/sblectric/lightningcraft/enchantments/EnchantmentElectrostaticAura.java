package sblectric.lightningcraft.enchantments;

import sblectric.lightningcraft.ref.RefStrings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

/** The Electrostatic Aura enchantment */
public class EnchantmentElectrostaticAura extends LCEnchantment {

	public EnchantmentElectrostaticAura(Rarity rarity) {
		super(rarity, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]
				{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET});
	}
	
	@Override
	public String getUnlocalizedName() {
		return "elec_aura";
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		switch(par1) {
		case 0:
			return 15;
		case 1:
			return 22;
		default:
			return 30;
		}
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		switch(par1) {
		case 0:
			return 21;
		case 1:
			return 29;
		default:
			return 30;
		}
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
}
