package sblectric.lightningcraft.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import sblectric.lightningcraft.ref.Metal.Rod;

/** Rods with increasing rarity */
public class ItemMetalRod extends ItemMeta {

	public ItemMetalRod() {
		super(Rod.count);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case Rod.ELEC:
			return EnumRarity.UNCOMMON;
		case Rod.SKY:
			return EnumRarity.RARE;
		case Rod.MYSTIC:
			return EnumRarity.EPIC;
		default:
			return super.getRarity(stack);
		}
	}

}
