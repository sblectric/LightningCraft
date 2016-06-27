package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

import sblectric.lightningcraft.ref.LCText;

/** the feather sword class */
public class ItemFeatherSword extends ItemSpecialSword {
	
	public ItemFeatherSword(ToolMaterial mat) {
		super(mat, 0.5);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getFeatherSwordLore());
	}
	
}
