package sblectric.lightningcraft.events;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import sblectric.lightningcraft.items.ItemSkyAxe;
import sblectric.lightningcraft.items.ItemSkyPick;
import sblectric.lightningcraft.items.ItemSkySpade;
import sblectric.lightningcraft.items.ifaces.IAutoRepair;
import sblectric.lightningcraft.network.LCNetwork;
import sblectric.lightningcraft.network.MessageSpawnParticle;

/** Tool event handler */
public class ToolEvents {
	
	private static final Random random = new Random();
	public static final int defBonus = 1;
	
	/** Auto-smelt blocks when using a skyfather tool */
	@SubscribeEvent
	public void onBlockDropSmelt(HarvestDropsEvent e) {
		if(!e.getWorld().isRemote && e.getHarvester() != null) {
			int fortuneLevel = e.getFortuneLevel() + defBonus;
			
			// get the held item and make sure it isn't null!
			// 1.1.2 fix
			ItemStack tool = e.getHarvester().inventory.getCurrentItem();
			if(e.getHarvester().inventory.getCurrentItem() == null) {
				return;
			}
			
			Item itool = tool.getItem();
			
			// auto smelt with the pick, axe, and shovel
			// but not when sneaking! Also, not with silk touch
			if((itool instanceof ItemSkyAxe || itool instanceof ItemSkyPick || itool instanceof ItemSkySpade) 
					&& !e.isSilkTouching() && !e.getHarvester().isSneaking()) {
				ItemStack result = FurnaceRecipes.instance().getSmeltingResult(
						new ItemStack(e.getState().getBlock(), 1, e.getState().getBlock().getMetaFromState(e.getState())));
				if (result != null) {
					// depending on fortune, release smelted ores
					// 1.4.1 fix: no fortune on block items!
					int nItems;
					int nParticles = 2;
					if(fortuneLevel > 0 && !(result.getItem() instanceof ItemBlock)) {
						nItems = 1 + random.nextInt(fortuneLevel + (itool instanceof IAutoRepair ? 1 : 0)); // only Mystic has Luck
					} else {
						nItems = 1;
					}
					for(int i = 0; i < nItems; i++) {
						float f = random.nextFloat() * 0.6F + 0.1F;
						float f1 = random.nextFloat() * 0.6F + 0.1F;
						float f2 = random.nextFloat() * 0.6F + 0.1F;
						float f3 = 0.025F;
						EntityItem eitem = new EntityItem(e.getWorld(), e.getPos().getX() + f, e.getPos().getY() + f1, e.getPos().getZ() + f2, result.copy());
						eitem.motionX = random.nextGaussian() * f3;
						eitem.motionY = random.nextGaussian() * f3 + 0.2F;
						eitem.motionZ = random.nextGaussian() * f3;
						e.getWorld().spawnEntityInWorld(eitem);
						tool.damageItem(1, e.getHarvester()); // +1 damage per ore obtained
						// spawn particles at this position
						for(int j = 0; j < nParticles; j++) {
							double rx = eitem.posX + random.nextFloat() - 0.5;
							double ry = eitem.posY + (random.nextFloat() + 0.5) * 0.15;
							double rz = eitem.posZ + random.nextFloat() - 0.5;							
							LCNetwork.net.sendToAll(new MessageSpawnParticle("flame", rx, ry, rz, 0, 0, 0));
						}
					}
					
					// cancel the default drops
					e.setDropChance(0);
				}
				
			}
			
		}
	}

}
