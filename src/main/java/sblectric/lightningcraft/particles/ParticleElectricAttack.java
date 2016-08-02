package sblectric.lightningcraft.particles;

import java.awt.Color;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** A special electric particle with custom size and color */
@SideOnly(Side.CLIENT)
public class ParticleElectricAttack extends ParticleRedstone {

	/** A special electric particle with custom size and color */
	public ParticleElectricAttack(World world, double x, double y, double z, float scale, Color rgb) {
		super(world, x,y,z, scale, 0, 0, 0);
        this.setRBGColorF(rgb.getRed() / 255F, rgb.getGreen() / 255F, rgb.getBlue() / 255F);
	}

}
