package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.IPotionEffectProvider;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.base.ItemArmorLC;
import sblectric.lightningcraft.ref.LCText;

/** Skyfather armor */
public class ItemSkyArmor extends ItemArmorLC implements IPotionEffectProvider {

	public ItemSkyArmor(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		super(mat, armorType);
	}
	
	// item lore
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		if(stack.getItem() == LCItems.skyHelm) {
			list.add(LCText.getSkyHelmLore());
		} else if(stack.getItem() == LCItems.skyChest) {
			list.add(LCText.getSkyChestLore());
		} else if(stack.getItem() == LCItems.skyLegs) {
			list.add(LCText.getSkyLegsLore());
		} else if(stack.getItem() == LCItems.skyBoots) {
			list.add(LCText.getSkyBootsLore());
		}
	}

	@Override
	public boolean canApplyEffect(ItemStack stack, EntityPlayer player, int invPosition) {
		if(player.world.getTotalWorldTime() % 20 == 0) { // only once a second
			if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == stack || 
					player.getItemStackFromSlot(EntityEquipmentSlot.CHEST) == stack || 
					player.getItemStackFromSlot(EntityEquipmentSlot.LEGS) == stack || 
					player.getItemStackFromSlot(EntityEquipmentSlot.FEET) == stack) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<PotionEffect> getEffects(ItemStack stack, EntityPlayer player, int invPosition) {
		JointList<PotionEffect> list = new JointList();
		if(stack.getItem() == LCItems.skyHelm) {
			list.join(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, false));
		} else if(stack.getItem() == LCItems.skyChest) {
			list.join(new PotionEffect(MobEffects.RESISTANCE, 30, 0, true, false));
		} else if(stack.getItem() == LCItems.skyLegs) {
			list.join(new PotionEffect(MobEffects.FIRE_RESISTANCE, 30, 0, true, false));
		} else if(stack.getItem() == LCItems.skyBoots) {
			list.join(new PotionEffect(MobEffects.JUMP_BOOST, 30, 2, true, false));
		}
		return list;
	}

}
