package sblectric.lightningcraft.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

/** Armor helper class */
public class ArmorHelper {

	/** Armor type for boots, used only in ItemArmor's {@link ItemArmor#ItemArmor constructor} */
	public static final int TYPE_BOOTS = 3;
	/** Armor type for leg armor, used only in ItemArmor's {@link ItemArmor#ItemArmor constructor} */
	public static final int TYPE_LEGS = 2;
	/** Armor type for chest armor, used only in ItemArmor's {@link ItemArmor#ItemArmor constructor} */
	public static final int TYPE_CHEST = 1;
	/** Armor type for helms, used only in ItemArmor's {@link ItemArmor#ItemArmor constructor} */
	public static final int TYPE_HELM = 0;
	/** Index for boots when using {@link EntityLivingBase#getEquipmentInSlot} */
	public static final int EQUIPPED_BOOTS = 1;
	/** Index for legs when using {@link EntityLivingBase#getEquipmentInSlot} */
	public static final int EQUIPPED_LEGS = 2;
	/** Index for chest when using {@link EntityLivingBase#getEquipmentInSlot} */
	public static final int EQUIPPED_CHEST = 3;
	/** Index for helm when using {@link EntityLivingBase#getEquipmentInSlot} */
	public static final int EQUIPPED_HELM = 4;
	/** Index for boots when using {@link EntityPlayer#getCurrentArmor} */
	public static final int WORN_BOOTS = 0;
	/** Index for legs when using {@link EntityPlayer#getCurrentArmor} */
	public static final int WORN_LEGS = 1;
	/** Index for chest when using {@link EntityPlayer#getCurrentArmor} */
	public static final int WORN_CHEST = 2;
	/** Index for helm when using {@link EntityPlayer#getCurrentArmor} */
	public static final int WORN_HELM = 3;
	
	private static Map<ArmorMaterial, ItemStack> matMap = new HashMap();
	
	/** Set an armor material's repair stack */
	public static void setRepairStack(ArmorMaterial mat, ItemStack repair) {
		matMap.put(mat, repair);
	}
	
	/** Get an armor material's repair stack */
	public static ItemStack getRepairStack(ArmorMaterial mat) {
		if(matMap.containsKey(mat)) {
			return matMap.get(mat);
		} else {
			return null;
		}
	}
	
	/** get if the entity has full armor of a specified type */
	public static boolean entityHasFullArmor(EntityLivingBase user, Class<? extends ItemArmor> type) {
		boolean flag = false;
		if(user != null) {
			flag = user.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null && user.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().getClass() == type;
			flag &= user.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && user.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem().getClass() == type;
			flag &= user.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null && user.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem().getClass() == type;
			flag &= user.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null && user.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem().getClass() == type;
		}
		return flag;
	}
	
	/** get if the player has full armor of a specified type */
	public static boolean playerHasFullArmor(EntityPlayer user, Class<? extends ItemArmor> type) {
		return entityHasFullArmor(user, type);
	}
	
	/**
	 * Add an {@link ItemArmor.ArmorMaterial}.
	 * <p>
	 * Temporary method until {@link EnumHelper#addArmorMaterial} is fixed.
	 *
	 * @param enumName         The name of the enum value
	 * @param textureName      The name of the armor texture to use
	 * @param durability       The durability
	 * @param reductionAmounts The damage reduction of each armour piece
	 * @param enchantability   The enchantability
	 * @param soundOnEquip     The sound to play when equipped
	 * @param toughness        The armour's toughness
	 * @return The new ArmorMaterial
	 * 
	 * @author Choonster
	 */
	public static ItemArmor.ArmorMaterial addArmorMaterial(String enumName, String textureName, int durability,
			int[] reductionAmounts, int enchantability, SoundEvent soundOnEquip, float toughness) {
		return EnumHelper.addEnum(ItemArmor.ArmorMaterial.class, enumName,
				new Class<?>[]{String.class, int.class, int[].class, int.class, SoundEvent.class, float.class},
				textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
	}

}