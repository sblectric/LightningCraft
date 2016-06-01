package com.lightningcraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.util.ResourceLocation;

import com.lightningcraft.config.LCConfig;

/** Enchantment class */
public class LCEnchantments {
	
	public static void mainRegistry() {
		addEnchantments();
		registerEnchantments();
	}
	
	public static Enchantment handOfThor;
	public static Enchantment elecAura;
	
	private static void addEnchantments() {
		handOfThor = new EnchantmentHandOfThor(Rarity.RARE);
		elecAura  = new EnchantmentElectrostaticAura(Rarity.RARE);
	}
	
	private static void registerEnchantments() {
		Enchantment.REGISTRY.register(LCConfig.thorEnchID, new ResourceLocation("handOfThor"), handOfThor);
		Enchantment.REGISTRY.register(LCConfig.auraEnchID, new ResourceLocation("elecAura"), elecAura);
	}

}
