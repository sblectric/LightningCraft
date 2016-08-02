package sblectric.lightningcraft.items.blocks;

import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.Metal.MBlock;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/** Blocks with increasing rarity */
public class ItemBlockMetal extends ItemBlockMeta {

	public ItemBlockMetal(Block block) {
		super(block);
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
