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
import sblectric.lightningcraft.api.IMysticGear;
import sblectric.lightningcraft.api.IPotionEffectProvider;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.items.base.ItemArmorLC;
import sblectric.lightningcraft.ref.LCText;

/** Mystic armor */
public class ItemMysticArmor extends ItemArmorLC implements IMysticGear, IPotionEffectProvider {

	public ItemMysticArmor(ArmorMaterial mat, EntityEquipmentSlot armorType) {
		super(mat, armorType);
	}
	
	// item lore
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
		if(stack.getItem() == LCItems.mysticHelm) {
			list.add(LCText.getSkyHelmLore());
			list.add(LCText.getMysticHelmLore());
		} else if(stack.getItem() == LCItems.mysticChest) {
			list.add(LCText.getMysticChestLore());
		} else if(stack.getItem() == LCItems.mysticLegs) {
			list.add(LCText.getSkyLegsLore());
			list.add(LCText.getMysticLegsLore());
		} else if(stack.getItem() == LCItems.mysticBoots) {
			list.add(LCText.getMysticBootsLore());
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
		if(stack.getItem() == LCItems.mysticHelm) {
			list.join(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, false));
			list.join(new PotionEffect(MobEffects.WATER_BREATHING, 60, 0, true, false));
		} else if(stack.getItem() == LCItems.mysticChest) {
			list.join(new PotionEffect(MobEffects.RESISTANCE, 30, 1, true, false));
		} else if(stack.getItem() == LCItems.mysticLegs) {
			list.join(new PotionEffect(MobEffects.FIRE_RESISTANCE, 30, 0, true, false));
			list.join(new PotionEffect(MobEffects.SATURATION, 30, 0, true, false));
		} else if(stack.getItem() == LCItems.mysticBoots) {
			list.join(new PotionEffect(MobEffects.JUMP_BOOST, 30, 3, true, false));
		}
		return list;
	}

}
