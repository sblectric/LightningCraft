package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import sblectric.lightningcraft.ref.LCText;

/** The blaze sword class */
public class ItemBlazeSword extends ItemSpecialSword {

	public ItemBlazeSword(ToolMaterial mat) {
		super(mat, 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getBlazeSwordLore());
	}

}
