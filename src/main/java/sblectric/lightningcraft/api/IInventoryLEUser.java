package sblectric.lightningcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

/** Interface for items that consume inventory LE */
public interface IInventoryLEUser {

	/** the item rarity to show */
	public static final EnumRarity ILERarity = EnumHelper.addRarity("LEUser", TextFormatting.GOLD, "LE User");
	
	/** the default repair cost (for tools / armor) */
	public static final double repairCost = 0.1;
	
	/** get the available power this item can use */
	public double getAvailablePower(EntityPlayer invOwner);
	
	/** is there an LE source? */
	public boolean hasLESource(EntityPlayer invOwner);
	
	/** get the LE source */
	public ItemStack getLESource(EntityPlayer invOwner, double leNeeded);

}
