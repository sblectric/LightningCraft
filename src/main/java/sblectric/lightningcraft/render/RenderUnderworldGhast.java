package sblectric.lightningcraft.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityUnderworldGhast;
import sblectric.lightningcraft.ref.RefStrings;

/** Underworld ghast renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldGhast extends RenderGhast {
	
	public static final IRenderFactory<EntityUnderworldGhast> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_ghast.png");
	private static final ResourceLocation TEXTURE_SHOOTING = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_ghast_shooting.png");

	public RenderUnderworldGhast(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
    protected void preRenderCallback(EntityGhast entity, float parTick) {
        GL11.glScalef(3F, 3F, 3F);
    }
	
	@Override
    protected ResourceLocation getEntityTexture(EntityGhast e) {
        return e.isAttacking() ? TEXTURE_SHOOTING : TEXTURE;
    }
	
	/** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityUnderworldGhast> {

		@Override
		public Render<? super EntityUnderworldGhast> createRenderFor(RenderManager manager) {
			return new RenderUnderworldGhast(manager);
		}
    	
    }

}
