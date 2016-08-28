package sblectric.lightningcraft.particles;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Custom particles and particle behavior */
public class LCParticles {
	
	/** sword particle behavior */
	public static final int swordParticleCount = 6;
	public static final float swordParticleSpread = 2.5F;
	public static final float electricAttackScale = 0.7F;
	
	/** particle colors */
	private static final Color soulRGB = new Color(96, 32, 32);
	private static final Color zombieRGB = new Color(64, 128, 64);
	private static final Color featherRGB = new Color(192, 192, 64);
	private static final Color enderRGB = new Color(128, 32, 128);
	private static final Color blazeRGB = new Color(255, 192, 16);
	private static final Color iceRGB = new Color(128, 140, 224);
	private static final Color electricRGB = new Color(192, 250, 255);
	
	/** Spawns both vanilla and LightningCraft particle effects */
	@SideOnly(Side.CLIENT)
	public static void spawnParticle(String name, double x, double y, double z, double vx, double vy, double vz) {
		World world = Minecraft.getMinecraft().theWorld;
		
		// special sword particles
		if(name.equals("soulSteal")) {
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, soulRGB));
		} else if(name.equals("zombieSummon")){
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, zombieRGB));
		} else if(name.equals("featherAttack")) {
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, featherRGB));
		} else if(name.equals("enderAttack")) {
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, enderRGB));
		} else if(name.equals("blazeAttack")) {
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, blazeRGB));
		} else if(name.equals("iceAttack")) {
			addEffect(new ParticleSpecialSword(world, x, y, z, vx, vy, vz, iceRGB));
			
		// electric particle
		} else if(name.equals("electricAttack")) {
			addEffect(new ParticleElectricAttack(world, x, y, z, electricAttackScale, electricRGB));
		
		// Underworld Portal particle
		} else if(name.equals("underPortal")) {
			addEffect(new ParticleUnderPortal(world, x, y, z, vx, vy, vz));
		
		// Vanilla particles
		} else {
			for(EnumParticleTypes p : EnumParticleTypes.values()) {
				if(p.getParticleName().equals(name)) {
					world.spawnParticle(p, x, y, z, vx, vy, vz);
					return;
				}
			}
		}
	}
	
	/** add an effect to the renderer list */
	@SideOnly(Side.CLIENT)
	private static void addEffect(Particle e) {
		Minecraft.getMinecraft().effectRenderer.addEffect(e);
	}

}
