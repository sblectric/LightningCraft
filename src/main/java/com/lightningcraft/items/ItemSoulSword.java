package com.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import com.lightningcraft.ref.LCText;
import com.lightningcraft.util.SkyUtils;

/** The soul sword class */
public class ItemSoulSword extends ItemSpecialSword {

	public ItemSoulSword(ToolMaterial mat) {
		super(mat, -0.3);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getSoulSwordLore());
	}	

}
