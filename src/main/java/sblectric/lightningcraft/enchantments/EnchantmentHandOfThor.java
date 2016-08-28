package sblectric.lightningcraft.enchantments;

import sblectric.lightningcraft.ref.RefStrings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

/** The Hand of Thor enchantment */
public class EnchantmentHandOfThor extends Enchantment {

	public EnchantmentHandOfThor(Rarity rarity) {
		super(rarity, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName(RefStrings.MODID + ":handOfThor");
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
