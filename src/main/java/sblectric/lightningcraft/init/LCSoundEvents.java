package sblectric.lightningcraft.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import sblectric.lightningcraft.ref.RefStrings;

/** Mod sound events */
public class LCSoundEvents {

	public static void mainRegistry() {
		registerSounds();
	}
	
	// the sound events
	public static SoundEvent demonSoldierIdle;
	public static SoundEvent demonSoldierHurt;
	public static SoundEvent demonSoldierDeath;
	public static SoundEvent underworldGhastMoan;
	public static SoundEvent underworldGhastWarn;
	public static SoundEvent underworldGhastHurt;
	public static SoundEvent underworldGhastDeath;
	public static SoundEvent portalUnderworld;
	
	/** Add the sound events needed */
	private static void registerSounds() {
		demonSoldierIdle = registerSound("mob.demon_soldier.idle");
		demonSoldierHurt = registerSound("mob.demon_soldier.hurt");
		demonSoldierDeath = registerSound("mob.demon_soldier.death");
		underworldGhastMoan = registerSound("mob.underworld_ghast.moan");
		underworldGhastWarn = registerSound("mob.underworld_ghast.warn");
		underworldGhastHurt = registerSound("mob.underworld_ghast.hurt");
		underworldGhastDeath = registerSound("mob.underworld_ghast.death");
		portalUnderworld = registerSound("portal.underworld");
	}

	/** Register the sound event */
	private static SoundEvent registerSound(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(RefStrings.MODID, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundName));
	}

}
