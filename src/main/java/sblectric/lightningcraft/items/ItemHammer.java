package sblectric.lightningcraft.items;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import sblectric.lightningcraft.init.LCEnchantments;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.util.Effect;

/** LightningCraft hammer */
public class ItemHammer extends ItemChargedSword {
	
	// attack speed setting
	protected double attackSpeed;

	public ItemHammer(ToolMaterial mat) {
		super(mat);
		this.attackSpeed = -3.5;
	}

	// item lore
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LCText.getChargedLore());
		list.add(LCText.getSummonerLore());
	}
	
	// set the item in use (only in main hand, though!)
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(hand == EnumHand.MAIN_HAND) {
			player.setActiveHand(hand);
			ItemStack stack = player.getHeldItem(hand);
	        return new ActionResult(EnumActionResult.SUCCESS, stack);
		} else {
			return super.onItemRightClick(world, player, hand);
		}
	}

	// this hammer has special properties... (THOR!?)
	// make it summon lightning randomly around you while right-clicking!
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {

		// Hand of Thor enchantment
		int j = EnchantmentHelper.getEnchantmentLevel(LCEnchantments.handOfThor, stack);

		if(random.nextDouble() <= (0.05 + j * 0.02)) { // 5% chance per tick + 2% per Hand of Thor level
			if(Effect.lightning(player, true));
			stack.damageItem(10 - j * 2, player); // the functionality comes at a price though
		}
	}
	
	@Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

	// make the attack speed quite slow for the hammer (new)
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName()); // clear the attack speed
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), 
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
		}

		return multimap;
	}

}
