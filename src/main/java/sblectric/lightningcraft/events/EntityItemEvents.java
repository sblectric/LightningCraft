package sblectric.lightningcraft.events;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.entities.EntityLCItem;
import sblectric.lightningcraft.init.LCCapabilities;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.recipes.LightningTransformRecipes;
import sblectric.lightningcraft.ref.Material;

/** Handles EntityItem events */
public class EntityItemEvents {

	/** Special handler for when an EntityItem is struck by lightning */
	@SubscribeEvent
	public void onEntityItemStruckByLightning(EntityStruckByLightningEvent e) {
		World world = e.getEntity().world;
		if(!world.isRemote && !e.getEntity().isDead && e.getEntity() instanceof EntityItem) {
			// get the EntityItem struck
			EntityItem item = (EntityItem)e.getEntity();
			JointList<ItemStack> input = new JointList().join(item.getItem());
			JointList<EntityItem> activeItems = new JointList().join(item);

			// now get nearby items within a 2 block radius
			for(Entity t : world.loadedEntityList) {
				if(!!t.isDead && t instanceof EntityItem && t != item && t.getDistance(item) <= 2) {
					EntityItem et = (EntityItem)t;
					input.add(et.getItem());
					activeItems.add(et);
				}
			}

			// get the output of the transformation
			ItemStack out = LightningTransformRecipes.instance().getTransformResult(input);
			if(out.isEmpty()) return; // abort processing here if there's no output

			// now remove the items
			for(EntityItem ent : activeItems) ent.setDead();

			// spawn an invincible resulting item at that position
			EntityItem entityitem = new EntityLCItem(world, item.posX, item.posY, item.posZ, out);
			world.spawnEntity(entityitem);
		}
	}

	/** Spawn the lightning upgrade when an upgraded tile is broken */
	@SubscribeEvent
	public void onUpgradedTileBreak(BreakEvent e) {
		if(!e.getWorld().isRemote) {
			TileEntity t = e.getWorld().getTileEntity(e.getPos());
			if(t != null && t.hasCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null) && 
					t.getCapability(LCCapabilities.LIGHTNING_UPGRADABLE, null).isUpgraded()) {
				Block.spawnAsEntity(e.getWorld(), e.getPos(), new ItemStack(LCItems.material, 1, Material.UPGRADE));
			}
		}
	}

}
