package sblectric.lightningcraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sblectric.lightningcraft.config.LCConfig;

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
	
	/** Now with correct (non-ID) registration! */
	private static void registerEnchantments() {
		GameRegistry.register(handOfThor.setRegistryName("handOfThor"));
		GameRegistry.register(elecAura.setRegistryName("elecAura"));
	}

}
