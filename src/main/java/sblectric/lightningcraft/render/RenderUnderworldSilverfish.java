package sblectric.lightningcraft.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityUnderworldSilverfish;
import sblectric.lightningcraft.ref.RefStrings;

/** Underworld Silverfish renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSilverfish extends RenderSilverfish {
	
	public static final IRenderFactory<EntityUnderworldSilverfish> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_silverfish.png");

	public RenderUnderworldSilverfish(RenderManager rm) {
		super(rm);
		this.shadowSize = 0.6F;
	}
	
	@Override
    protected void preRenderCallback(EntitySilverfish entity, float parTick) {
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }
	
	@Override
    protected ResourceLocation getEntityTexture(EntitySilverfish e) {
        return TEXTURE;
    }
	
	/** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityUnderworldSilverfish> {

		@Override
		public Render<? super EntityUnderworldSilverfish> createRenderFor(RenderManager manager) {
			return new RenderUnderworldSilverfish(manager);
		}
    	
    }
	
}
