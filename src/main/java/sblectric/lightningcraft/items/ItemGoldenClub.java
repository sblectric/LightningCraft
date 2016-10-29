package sblectric.lightningcraft.items;

import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.util.StackHelper;

/** The return of the golden club */
public class ItemGoldenClub extends ItemGolfClub {
	
	public ItemGoldenClub() {
		super();
		this.setMaxDamage(18); // three times the uses
	}
	
	// repair the item with given item
	@Override
	public boolean getIsRepairable(ItemStack a, ItemStack b) {
		ItemStack repairWith = new ItemStack(LCItems.rod, 0, Rod.GOLD);
		if(StackHelper.areItemStacksEqualForCrafting(repairWith, b)){
			return true;
		} else {
			return false;
		}
	}

}
