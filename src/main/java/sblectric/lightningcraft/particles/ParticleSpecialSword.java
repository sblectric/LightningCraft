package sblectric.lightningcraft.particles;

import java.awt.Color;

import net.minecraft.client.particle.ParticleCrit;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** A special sword strike particle with custom color */
@SideOnly(Side.CLIENT)
public class ParticleSpecialSword extends ParticleCrit {

	/** A new special sword strike particle with custom color */
	public ParticleSpecialSword(World world, double x, double y, double z, double velx, double vely, double velz, Color rgb) {
		super(world, x,y,z, velx,vely,velz);
		this.particleScale = 2.75F;
		this.setParticleTextureIndex(66);
		this.setRBGColorF(rgb.getRed() / 255F, rgb.getGreen() / 255F, rgb.getBlue() / 255F);
	}

}
