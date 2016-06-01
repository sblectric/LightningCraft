package com.lightningcraft.potions;

import java.awt.Color;

import net.minecraft.potion.Potion;

import com.lightningcraft.config.LCConfig;
import com.lightningcraft.ref.RefStrings;

/** Class for custom potion effect registration */
public class LCPotions {
	
	public static void mainRegistry() {
		registerPotions();
	}
	
	public static final int potionCount = 1;
	public static final int extOffset = 2048;
	
	public static Potion demonFriend;
	public static final int demonFriendMeta = 0, demonFriendExtMeta = demonFriendMeta + extOffset;
	public static final int demonFriendTicks = 18000;
	public static final int demonFriendExtTicks = 30000;
	
	/** Register custom potions */
	private static void registerPotions() {
		demonFriend = new PotionCustom("demonFriend", false, new Color(192,192,0).getRGB()).setPotionName("potion.demonFriend");
		Potion.REGISTRY.register(LCConfig.demonFriendPotionID, demonFriend.getRegistryName(), demonFriend);
	}

}
