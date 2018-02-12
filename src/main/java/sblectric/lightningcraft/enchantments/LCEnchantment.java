package sblectric.lightningcraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import sblectric.lightningcraft.ref.RefStrings;

/** An enchantment for the mod */
public abstract class LCEnchantment extends Enchantment {

	public LCEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(rarityIn, typeIn, slots);
		this.setName(RefStrings.MODID + ":" + getUnlocalizedName());
	}
	
	/** Get the unlocalized name */
	public abstract String getUnlocalizedName();
	
	/** Set the registry name based on the unlocalized name */
	public Enchantment setRegistryNameImplicit() {
		return this.setRegistryName(getUnlocalizedName());
	}

}
