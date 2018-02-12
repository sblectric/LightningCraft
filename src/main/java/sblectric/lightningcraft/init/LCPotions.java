package sblectric.lightningcraft.init;

import java.awt.Color;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.potions.PotionCustom;
import sblectric.lightningcraft.registry.RegistryHelper;

/** Class for custom potion effect registration */
public class LCPotions {
	
	private static final List<Potion> POTIONS = RegistryHelper.POTIONS_TO_REGISTER;
	private static final List<PotionType> POTION_TYPES = RegistryHelper.POTION_TYPES_TO_REGISTER;
	
	public static void mainRegistry() {
		registerPotions();
	}
	
	public static Potion demonFriend;
	public static PotionType demonFriendNormal, demonFriendExtended;
	public static final int demonFriendTicks = 18000;
	public static final int demonFriendExtTicks = 30000;
	
	/** Register custom potions */
	private static void registerPotions() {
		
		// register the demon friend potion with two types
		demonFriend = new PotionCustom("demon_warding", false, new Color(255,255,0).getRGB()).setPotionName("potion.demon_warding");
		POTIONS.add(demonFriend);
		demonFriendNormal = new PotionType(new PotionEffect(demonFriend, demonFriendTicks));
		demonFriendExtended = new PotionType(new PotionEffect(demonFriend, demonFriendExtTicks));
		POTION_TYPES.add(demonFriendNormal.setRegistryName("demon_warding_normal"));
		POTION_TYPES.add(demonFriendExtended.setRegistryName("demon_warding_extended"));
	}
	
	/** Get a potion with a potion type */
	public static ItemStack getPotionWithType(PotionType type) {
		ItemStack potion = new ItemStack(Items.POTIONITEM);
		return PotionUtils.addPotionToItemStack(potion, type);
	}

}
