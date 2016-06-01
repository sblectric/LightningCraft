package com.lightningcraft.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.main.LightningCraft;
import com.lightningcraft.render.RenderDemonSoldier;
import com.lightningcraft.render.RenderLCElectricAttack;
import com.lightningcraft.render.RenderLCTNTPrimed;
import com.lightningcraft.render.RenderUnderworldSilverfish;
import com.lightningcraft.render.RenderUnderworldSkeleton;
import com.lightningcraft.render.RenderUnderworldSlime;

public class LCEntities {
	
	static int modEntityID;
	
	public static void mainRegistry() {
		modEntityID = 0;
		registerEntities();
		addEntitySpawns();
	}
	
	private static void registerEntities() {
		// living
		EntityRegistry.registerModEntity(EntityLCZombie.class, "helperZombie", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDemonSoldier.class, "demonSoldier", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSlime.class, "underworldSlime", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSkeleton.class, "underworldSkeleton", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSilverfish.class, "underworldSilverfish", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		
		// non-living
		EntityRegistry.registerModEntity(EntityLCTNTPrimed.class, "lcTNTPrimed", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
		EntityRegistry.registerModEntity(EntityLCElectricAttack.class, "lcElectricAttack", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
	}
	
	private static void addEntitySpawns() {
		// demons can spawn occasionally in the Nether
		EntityRegistry.addSpawn(EntityDemonSoldier.class, 18, 1, 3, EnumCreatureType.MONSTER, Biomes.HELL);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		RenderManager mgr = Minecraft.getMinecraft().getRenderManager();
		RenderItem itm = Minecraft.getMinecraft().getRenderItem();
		
		// living
		RenderingRegistry.registerEntityRenderingHandler(EntityLCZombie.class, new RenderZombie(mgr));
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonSoldier.class, new RenderDemonSoldier(mgr));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSlime.class, new RenderUnderworldSlime(mgr));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSkeleton.class, new RenderUnderworldSkeleton(mgr));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSilverfish.class, new RenderUnderworldSilverfish(mgr));
		
		// non-living
		RenderingRegistry.registerEntityRenderingHandler(EntityLCTNTPrimed.class, new RenderLCTNTPrimed(mgr));
		RenderingRegistry.registerEntityRenderingHandler(EntityLCElectricAttack.class, new RenderLCElectricAttack(mgr));
	}

}
