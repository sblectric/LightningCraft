package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityLCZombie;

/** Helper zombie renderer */
@SideOnly(Side.CLIENT)
public class RenderLCZombie extends RenderZombie {
	
	public static final IRenderFactory<EntityLCZombie> FACTORY = new Factory();

	public RenderLCZombie(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	/** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityLCZombie> {

		@Override
		public Render<? super EntityLCZombie> createRenderFor(RenderManager manager) {
			return new RenderLCZombie(manager);
		}
    	
    }

}
