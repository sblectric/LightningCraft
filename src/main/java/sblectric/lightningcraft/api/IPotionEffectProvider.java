package sblectric.lightningcraft.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

/** Interface for items that give potion effects when in a player inventory */
public interface IPotionEffectProvider {
	
	/** Can the potion effect(s) be applied? */
	public boolean canApplyEffect(ItemStack stack, EntityPlayer player, int invPosition);
	
	/** Get the potion effects */
	public List<PotionEffect> getEffects(ItemStack stack, EntityPlayer player, int invPosition);

}
