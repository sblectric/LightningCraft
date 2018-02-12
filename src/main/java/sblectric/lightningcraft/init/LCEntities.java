package sblectric.lightningcraft.init;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.LightningCraft;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.entities.EntityDemonSoldier;
import sblectric.lightningcraft.entities.EntityLCElectricAttack;
import sblectric.lightningcraft.entities.EntityLCTNTPrimed;
import sblectric.lightningcraft.entities.EntityLCZombie;
import sblectric.lightningcraft.entities.EntityUnderworldCreeper;
import sblectric.lightningcraft.entities.EntityUnderworldGhast;
import sblectric.lightningcraft.entities.EntityUnderworldSilverfish;
import sblectric.lightningcraft.entities.EntityUnderworldSkeleton;
import sblectric.lightningcraft.entities.EntityUnderworldSlime;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.render.RenderDemonSoldier;
import sblectric.lightningcraft.render.RenderLCElectricAttack;
import sblectric.lightningcraft.render.RenderLCTNTPrimed;
import sblectric.lightningcraft.render.RenderLCZombie;
import sblectric.lightningcraft.render.RenderUnderworldCreeper;
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
		registerEntity(EntityLCZombie.class, "helper_zombie", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityDemonSoldier.class, "demon_soldier", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityUnderworldSlime.class, "underworld_slime", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityUnderworldSkeleton.class, "underworld_skeleton", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityUnderworldSilverfish.class, "underworld_silverfish", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityUnderworldGhast.class, "underworld_ghast", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		registerEntity(EntityUnderworldCreeper.class, "underworld_creeper", ++modEntityID, LightningCraft.modInstance, 80, 3, true);
		
		// non-living
		modEntityID = 100;
		registerEntity(EntityLCTNTPrimed.class, "lc_tnt_primed", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
		registerEntity(EntityLCElectricAttack.class, "lc_electric_attack", ++modEntityID, LightningCraft.modInstance, 160, 3, true);
	}
	
	/** Register the spawn eggs */
	private static void registerEggs() {
		registerEgg(EntityDemonSoldier.class, new Color(40, 40, 40), new Color(224, 114, 32));
		registerEgg(EntityUnderworldSlime.class, new Color(0, 32, 64), new Color(32, 160, 211));
		registerEgg(EntityUnderworldSkeleton.class, new Color(0, 97, 97), new Color(64, 211, 211));
		registerEgg(EntityUnderworldSilverfish.class, new Color(64, 0, 0), new Color(192, 32, 32));
		registerEgg(EntityUnderworldGhast.class, new Color(40, 40, 40), new Color(192, 192, 192));
		registerEgg(EntityUnderworldCreeper.class, new Color(0, 60, 80), new Color(64, 160, 175));
	}
	
	// some helper methods
	private static Map<Class, ResourceLocation> entityMap = new HashMap();
	
	private static void registerEntity(Class entityClass, String name, int id, Object mod, int trackingRange, int updateFrequency, boolean velocityUpdates) {
		ResourceLocation res = new ResourceLocation(RefStrings.MODID, name);
		EntityRegistry.registerModEntity(res, entityClass, res.toString(), id, mod, trackingRange, updateFrequency, velocityUpdates);
		entityMap.put(entityClass, res);
	}
	
	private static void registerEgg(Class entityClass, Color color1, Color color2) {
		EntityRegistry.registerEgg(entityMap.get(entityClass), color1.getRGB(), color2.getRGB());
	}
	
	private static void addEntitySpawns() {
		// demons can spawn occasionally in the Nether
		if(LCConfig.demonSoldiersInNether) EntityRegistry.addSpawn(EntityDemonSoldier.class, 15, 1, 2, EnumCreatureType.MONSTER, Biomes.HELL);
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
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderworldCreeper.class, RenderUnderworldCreeper.FACTORY);
		
		// non-living
		RenderingRegistry.registerEntityRenderingHandler(EntityLCTNTPrimed.class, RenderLCTNTPrimed.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityLCElectricAttack.class, RenderLCElectricAttack.FACTORY);
	}

}
