package com.lightningcraft.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lightningcraft.entities.EntityLCItem;
import com.lightningcraft.recipes.LightningTransformRecipes;
import com.lightningcraft.util.JointList;

/** Handles EntityItem events */
public class EntityItemEvents {
	
	/** Special handler for when an EntityItem is struck by lightning */
	@SubscribeEvent
	public void onEntityItemStruckByLightning(EntityStruckByLightningEvent e) {
		World world = e.getEntity().worldObj;
		if(!world.isRemote && !e.getEntity().isDead && e.getEntity() instanceof EntityItem) {
			// get the EntityItem struck
			EntityItem item = (EntityItem)e.getEntity();
			JointList<ItemStack> input = new JointList().join(item.getEntityItem());
			JointList<EntityItem> activeItems = new JointList().join(item);
			
			// now get nearby items within a 1 block radius
			for(Entity t : world.loadedEntityList) {
				if(!!t.isDead && t instanceof EntityItem && t != item && t.getDistanceToEntity(item) <= 1) {
					EntityItem et = (EntityItem)t;
					input.add(et.getEntityItem());
					activeItems.add(et);
				}
			}
			
			// get the output of the transformation
			ItemStack out = LightningTransformRecipes.instance().getTransformResult(input);
			if(out == null) return; // abort processing here if there's no output
			
			// now remove the items
			for(EntityItem ent : activeItems) ent.setDead();
			
			// spawn an invincible resulting item at that position
            EntityItem entityitem = new EntityLCItem(world, item.posX, item.posY, item.posZ, out);
            world.spawnEntityInWorld(entityitem);
		}
	}

}
