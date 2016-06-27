package sblectric.lightningcraft.potions;

import java.awt.Color;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;

/** Class for custom potion effect registration */
public class LCPotions {
	
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
		demonFriend = new PotionCustom("demonFriend", false, new Color(255,255,0).getRGB()).setPotionName("potion.demonFriend");
		GameRegistry.register(demonFriend);
		demonFriendNormal = new PotionType(new PotionEffect(demonFriend, demonFriendTicks));
		demonFriendExtended = new PotionType(new PotionEffect(demonFriend, demonFriendExtTicks));
		GameRegistry.register(demonFriendNormal.setRegistryName("demonFriendNormal"));
		GameRegistry.register(demonFriendExtended.setRegistryName("demonFriendExtended"));
	}
	
	/** Get a potion with a potion type */
	public static ItemStack getPotionWithType(PotionType type) {
		ItemStack potion = new ItemStack(Items.POTIONITEM);
		return PotionUtils.addPotionToItemStack(potion, type);
	}

}
