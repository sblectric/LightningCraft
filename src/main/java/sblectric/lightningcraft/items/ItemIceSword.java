package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.items.base.ItemSpecialSword;
import sblectric.lightningcraft.ref.LCText;

/** The ice sword class */
public class ItemIceSword extends ItemSpecialSword {

	public ItemIceSword(ToolMaterial mat) {
		super(mat, -0.5);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getIceSwordLore());
	}

}
