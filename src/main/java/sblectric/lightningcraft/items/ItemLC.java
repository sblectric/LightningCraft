package sblectric.lightningcraft.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;

/** A basic LightningCraft item. */
public class ItemLC extends Item implements ILightningCraftItem {
	
	private EnumRarity rarity;
	
	/** A basic LightningCraft item. */
	public ItemLC(EnumRarity rarity) {
		this.rarity = rarity;
	}
	
	public ItemLC() {
		this(EnumRarity.COMMON);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack){
		return rarity;
	}

}
