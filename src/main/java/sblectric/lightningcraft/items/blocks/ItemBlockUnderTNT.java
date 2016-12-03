package sblectric.lightningcraft.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.blocks.BlockUnderTNT;

/** Underworld TNT block */
public class ItemBlockUnderTNT extends ItemBlockMeta {

	public ItemBlockUnderTNT(Block block) {
		super(block);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case BlockUnderTNT.MYSTIC:
			return EnumRarity.EPIC;
		case BlockUnderTNT.LIGHTNING:
			return EnumRarity.RARE;
		default:
			return EnumRarity.UNCOMMON;
		}
	}

}
