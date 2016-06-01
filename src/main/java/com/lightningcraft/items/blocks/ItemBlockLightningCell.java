package com.lightningcraft.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.lightningcraft.blocks.BlockLightningCell;

/** The Lightning Cell block item */
public class ItemBlockLightningCell extends ItemBlockMetal {

	private BlockLightningCell block;
	
	public ItemBlockLightningCell(Block block) {
		super(block);
		this.block = (BlockLightningCell)block;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Capacity: " + block.getMaxPower(block.getStateFromMeta(stack.getItemDamage())) + " LE");
	}

}
