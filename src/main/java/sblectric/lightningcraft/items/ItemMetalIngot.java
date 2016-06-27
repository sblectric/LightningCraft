package sblectric.lightningcraft.items;

import sblectric.lightningcraft.ref.Metal.Ingot;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/** Ingots with increasing rarity */
public class ItemMetalIngot extends ItemMeta {

	public ItemMetalIngot() {
		super(Ingot.count);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case Ingot.ELEC:
			return EnumRarity.UNCOMMON;
		case Ingot.SKY:
			return EnumRarity.RARE;
		case Ingot.MYSTIC:
			return EnumRarity.EPIC;
		default:
			return super.getRarity(stack);
		}
	}

}
