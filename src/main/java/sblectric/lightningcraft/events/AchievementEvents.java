package sblectric.lightningcraft.events;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import sblectric.lightningcraft.achievements.LCAchievements;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.items.ItemBattery;
import sblectric.lightningcraft.items.ItemLightningGuide;
import sblectric.lightningcraft.items.ItemMetalRod;
import sblectric.lightningcraft.items.ItemSpecialSword;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.items.ifaces.IInventoryLEUser;
import sblectric.lightningcraft.ref.Metal.Ingot;

/** Events that give achievements */
public class AchievementEvents {
	
	/** Crafting achievements */
	@SubscribeEvent
	public void onCraftThing(ItemCraftedEvent e) {
		ItemStack output = e.crafting;
		Item item = output.getItem();
		Block block = Block.getBlockFromItem(item);
		int meta = output.getItemDamage();
		
		// items
		
		if(item instanceof ItemMetalRod) {
			e.player.addStat(LCAchievements.craftRod, 1);
		}
		
		if(item instanceof ItemLightningGuide) {
			e.player.addStat(LCAchievements.craftGuide, 1);
		}
		
		if(item instanceof ItemBattery) {
			e.player.addStat(LCAchievements.craftBattery, 1);
		}
		
		if(item instanceof IInventoryLEUser) {
			e.player.addStat(LCAchievements.craftKinetic, 1);
		}
		
		// blocks
		
		if(block == LCBlocks.lightningCell) {
			e.player.addStat(LCAchievements.craftCell, 1);
		}
		
		if(block == LCBlocks.airTerminal) {
			e.player.addStat(LCAchievements.craftTerminal, 1);
		}
		
		if(block == LCBlocks.lightningFurnace) {
			e.player.addStat(LCAchievements.craftFurnace, 1);
		}
		
		if(block == LCBlocks.lightningCrusher) {
			e.player.addStat(LCAchievements.craftCrusher, 1);
		}
		
		if(block == LCBlocks.lightningInfuser) {
			e.player.addStat(LCAchievements.craftInfuser, 1);
		}
		
		if(block == LCBlocks.staticGenerator) {
			e.player.addStat(LCAchievements.craftGenerator, 1);
		}
		
		if(block == LCBlocks.wirelessBlock) {
			e.player.addStat(LCAchievements.craftWireless, 1);
		}
		
		if(block == LCBlocks.enchReallocator) {
			e.player.addStat(LCAchievements.craftReallocator, 1);
		}
		
	}
	
	/** Infusion achievements */
	@SubscribeEvent
	public void onInfuseThing(ItemSmeltedEvent e) {
		ItemStack output = e.smelting;
		Item item = output.getItem();
		Block block = Block.getBlockFromItem(item);
		int meta = output.getItemDamage();
		
		if(item == LCItems.ingot && meta == Ingot.ELEC) {
			e.player.addStat(LCAchievements.getElectricium, 1);
		}
		
		if(item instanceof ItemSpecialSword) {
			e.player.addStat(LCAchievements.infuseSpecialSword, 1);
		}
		
		if(item == LCItems.ingot && meta == Ingot.SKY) {
			e.player.addStat(LCAchievements.infuseSkyfather, 1);
		}
		
		if(item == LCItems.ingot && meta == Ingot.MYSTIC) {
			e.player.addStat(LCAchievements.infuseMystic, 1);
		}
	}
	
	/** Picking up item achievements */
	@SubscribeEvent
	public void onPickUpThing(ItemPickupEvent e) {
		ItemStack got = e.pickedUp.getEntityItem();
		Item item = got.getItem();
		int meta = got.getItemDamage();
		
		if(item == LCItems.ingot && meta == Ingot.ELEC) {
			e.player.addStat(LCAchievements.getElectricium, 1);
		}
		
	}

}
