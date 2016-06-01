package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.lightningcraft.ref.LCText;

/** The zombie sword class */
public class ItemZombieSword extends ItemSpecialSword {

	public ItemZombieSword(ToolMaterial mat) {
		super(mat, -0.2);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getZombieSwordLore());
	}

}
