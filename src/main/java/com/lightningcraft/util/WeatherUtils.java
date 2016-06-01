package com.lightningcraft.util;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/** Helper methods associated with weather effects */
public class WeatherUtils {
	
	/** gets a list of lightning bolts within a box */
	public static List<EntityLightningBolt> getLightningBoltsWithinAABB(World world, AxisAlignedBB box) {
		List<EntityLightningBolt> bolts = new LinkedList<EntityLightningBolt>();
		for(Entity i : (List<Entity>) world.weatherEffects) {
			if(i instanceof EntityLightningBolt) {
				if(i.posX >= box.minX && i.posX <= box.maxX		&& i.posY >= box.minY && i.posY <= box.maxY		&& i.posZ >= box.minZ && i.posZ <= box.maxZ)
					bolts.add((EntityLightningBolt) i);
			}
		}
		return bolts;
	}

}
