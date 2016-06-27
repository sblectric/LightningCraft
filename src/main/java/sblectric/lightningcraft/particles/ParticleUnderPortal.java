package sblectric.lightningcraft.particles;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Just a dark version of the portal effect */
@SideOnly(Side.CLIENT)
public class ParticleUnderPortal extends ParticlePortal {

	/** Just a dark version of the portal effect */
	public ParticleUnderPortal(World world, double x, double y, double z, double velx, double vely, double velz) {
		super(world, x,y,z, velx,vely,velz);
        this.setRBGColorF(0.1F, 0.1F, 0.1F);
	}

}
