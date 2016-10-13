package sblectric.lightningcraft.entities;

import java.awt.Color;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.main.LightningCraft;
import sblectric.lightningcraft.render.RenderDemonSoldier;
import sblectric.lightningcraft.render.RenderLCElectricAttack;
import sblectric.lightningcraft.render.RenderLCTNTPrimed;
import sblectric.lightningcraft.render.RenderLCZombie;
import sblectric.lightningcraft.render.RenderUnderworldGhast;
import sblectric.lightningcraft.render.RenderUnderworldSilverfish;
import sblectric.lightningcraft.render.RenderUnderworldSkeleton;
import sblectric.lightningcraft.render.RenderUnderworldSlime;

public class LCEntities {
	
	static int modEntityID;
	
	public static void mainRegistry() {
		registerEntities();
		registerEggs();
		addEntitySpawns();
	}
	
	private static void registerEntities() {
		// living
		modEntityID = 0;
		EntityRegistry.registerModEntity(EntityLCZombie.class, "helperZombie", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityDemonSoldier.class, "demonSoldier", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSlime.class, "underworldSlime", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSkeleton.class, "underworldSkeleton", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldSilverfish.class, "underworldSilverfish", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityUnderworldGhast.class, "underworldGhast", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		
		// non-living
		modEntityID = 100;
		EntityRegistry.registerModEntity(EntityLCTNTPrimed.class, "lcTNTPrimed", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
		EntityRegistry.registerModEntity(EntityLCElectricAttack.class, "lcElectricAttack", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
	}
	
	/** Register the spawn eggs */
	private static void registerEggs() {
		EntityRegistry.registerEgg(EntityDemonSoldier.class, new Color(40, 40, 40).getRGB(), new Color(224, 114, 32).getRGB());
		EntityRegistry.registerEgg(EntityUnderworldSlime.class, new Color(0, 32, 64).getRGB(), new Color(32, 160, 211).getRGB());
		EntityRegistry.registerEgg(EntityUnderworldSkeleton.class, new Color(0, 97, 97).getRGB(), new Color(64, 211, 211).getRGB());
		EntityRegistry.registerEgg(EntityUnderworldSilverfish.class, new Color(64, 0, 0).getRGB(), new Color(192, 32, 32).getRGB());
		EntityRegistry.registerEgg(EntityUnderworldGhast.class, new Color(40, 40, 40).getRGB(), new Color(192, 192, 192).getRGB());
	}
	
	private static void addEntitySpawns() {
		// demons can spawn occasionally in the Nether
		if(LCConfig.demonSoldiersInNether) EntityRegistry.addSpawn(EntityDemonSoldier.class, 18, 1, 3, EnumCreatureType.MONSTER, Biomes.HELL);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		
		// living
		RenderingRegistry.registerEntityRenderingHandler(EntityLCZombie.class, RenderLCZombie.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityDemonSoldier.class, RenderDemonSoldier.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSlime.class, RenderUnderworldSlime.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSkeleton.class, RenderUnderworldSkeleton.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldSilverfish.class, RenderUnderworldSilverfish.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldGhast.class, RenderUnderworldGhast.FACTORY);
		
		// non-living
		RenderingRegistry.registerEntityRenderingHandler(EntityLCTNTPrimed.class, RenderLCTNTPrimed.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityLCElectricAttack.class, RenderLCElectricAttack.FACTORY);
	}

}
