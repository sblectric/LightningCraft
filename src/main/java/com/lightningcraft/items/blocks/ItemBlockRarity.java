package com.lightningcraft.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/** A block item with rarity */
public class ItemBlockRarity extends ItemBlock {
	
	private EnumRarity rarity;

	public ItemBlockRarity(Block block, EnumRarity rarity) {
		super(block);
		this.rarity = rarity;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return rarity;
	}

}
