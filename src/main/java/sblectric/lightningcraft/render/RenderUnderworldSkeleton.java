package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityUnderworldSkeleton;
import sblectric.lightningcraft.ref.RefStrings;

/** Underworld Skeleton renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSkeleton extends RenderSkeleton {
	
	public static final IRenderFactory<EntityUnderworldSkeleton> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_skeleton.png");
	
	public RenderUnderworldSkeleton(RenderManager rm) {
		super(rm);
	}

    @Override
    protected ResourceLocation getEntityTexture(AbstractSkeleton e) {
        return TEXTURE;
    }
    
    /** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityUnderworldSkeleton> {

		@Override
		public Render<? super EntityUnderworldSkeleton> createRenderFor(RenderManager manager) {
			return new RenderUnderworldSkeleton(manager);
		}
    	
    }

}
