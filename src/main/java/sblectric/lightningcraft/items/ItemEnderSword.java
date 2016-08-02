package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.ref.LCText;

/** the ender sword class */
public class ItemEnderSword extends ItemSpecialSword {

	public ItemEnderSword(ToolMaterial mat) {
		super(mat, 0.3);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getEnderSwordLore());
	}
	
}
