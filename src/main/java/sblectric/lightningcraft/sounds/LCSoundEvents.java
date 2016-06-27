package sblectric.lightningcraft.sounds;

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
	public static SoundEvent underworldGhastHurt;
	public static SoundEvent underworldGhastDeath;
	public static SoundEvent portalUnderworld;
	
	/** Add the sound events needed */
	private static void registerSounds() {
		demonSoldierIdle = registerSound("mob.demonSoldier.idle");
		demonSoldierHurt = registerSound("mob.demonSoldier.hurt");
		demonSoldierDeath = registerSound("mob.demonSoldier.death");
		underworldGhastMoan = registerSound("mob.underworldGhast.moan");
		underworldGhastHurt = registerSound("mob.underworldGhast.hurt");
		underworldGhastDeath = registerSound("mob.underworldGhast.death");
		portalUnderworld = registerSound("portal.underworld");
	}

	/** Register the sound event */
	private static SoundEvent registerSound(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(RefStrings.MODID, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundName));
	}

}
