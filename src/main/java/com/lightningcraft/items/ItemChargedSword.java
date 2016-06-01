package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import com.lightningcraft.ref.LCText;

/** A sword that has electrical properties */
public class ItemChargedSword extends ItemSwordLC {

	public ItemChargedSword(ToolMaterial mat) {
		super(mat);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getChargedLore());
	}

}
