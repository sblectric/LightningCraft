package sblectric.lightningcraft.events;

import net.minecraftforge.common.MinecraftForge;

/** Register the mod's event handlers */
public class LCEvents {
	
	public static void mainRegistry() {
		registerEvents();
	}
	
	private static void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new AchievementEvents());
		MinecraftForge.EVENT_BUS.register(new CombatEvents());
		MinecraftForge.EVENT_BUS.register(new ToolEvents());
		MinecraftForge.EVENT_BUS.register(new EntityItemEvents());
	}

}
