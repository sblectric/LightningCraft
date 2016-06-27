package sblectric.lightningcraft.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockLightningCannon extends ItemBlockMeta {

	public ItemBlockLightningCannon(Block block) {
		super(block);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 2:
			return EnumRarity.EPIC;
		default:
			return EnumRarity.RARE;
		}
	}

}
