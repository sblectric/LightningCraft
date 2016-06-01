package com.lightningcraft.items;

import java.util.List;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.potions.LCPotions;
import com.lightningcraft.render.PotionItemMeshDefinition;

/** The custom potion (no more potion imitators!) */
public class ItemPotionLC extends ItemLC {
	
	public ItemPotionLC() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List sub) {
		for(int p = 0; p < LCPotions.potionCount; p++) {
			sub.add(new ItemStack(item, 1, p));
			sub.add(new ItemStack(item, 1, p + LCPotions.extOffset));
		}
	}
	
	/** Is the potion extended? */
	public boolean isExtended(ItemStack stack) {
		return stack.getItemDamage() >= LCPotions.extOffset;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		switch(stack.getItemDamage() % LCPotions.extOffset) {
		case LCPotions.demonFriendMeta:
			return I18n.translateToLocal("potion.demonFriend.postfix");
		default:
			return super.getItemStackDisplayName(stack);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		switch(stack.getItemDamage() % LCPotions.extOffset) {
		case LCPotions.demonFriendMeta:
			tooltip.add(getPotionString(stack, "potion.demonFriend", LCPotions.demonFriendTicks, LCPotions.demonFriendExtTicks));
		}
	}
	
	/** Get the potion string */
	public String getPotionString(ItemStack stack, String potionName, int timeNormal, int timeExtended) {
		return TextFormatting.BLUE + I18n.translateToLocal(potionName) + " (" + (!isExtended(stack) ? timeNormal : timeExtended) / 1200 + ":00)";
	}
	
	/** Drink the potion */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
	/** Add the potion effect and change the potion into a glass bottle */
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase user) {
		EntityPlayer entityplayer = user instanceof EntityPlayer ? (EntityPlayer)user : null;

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        if (!world.isRemote) {
        	performEffect(stack, user);
        }
        
        if(stack.stackSize <= 0) {
        	return new ItemStack(Items.GLASS_BOTTLE);
        } else {
        	return stack;
        }
    }
	
	/** Perform the potion effect on the player */
	public void performEffect(ItemStack stack, EntityLivingBase user) {
		switch(stack.getItemDamage() % LCPotions.extOffset) {
		case LCPotions.demonFriendMeta:
			user.addPotionEffect(new PotionEffect(LCPotions.demonFriend, !isExtended(stack) ? LCPotions.demonFriendTicks : LCPotions.demonFriendExtTicks));
		}
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender(ItemModelMesher mesher) {
		mesher.register(this, new PotionItemMeshDefinition());
	}

}
