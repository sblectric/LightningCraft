package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityUnderworldSlime;
import sblectric.lightningcraft.ref.RefStrings;

/** Underworld Slime renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSlime extends RenderMagmaCube {
	
	public static final IRenderFactory<EntityUnderworldSlime> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_slime.png");
	
	public RenderUnderworldSlime(RenderManager rm) {
		super(rm);
	}

    @Override
    protected ResourceLocation getEntityTexture(EntityMagmaCube e) {
        return TEXTURE;
    }
    
    /** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityUnderworldSlime> {

		@Override
		public Render<? super EntityUnderworldSlime> createRenderFor(RenderManager manager) {
			return new RenderUnderworldSlime(manager);
		}
    	
    }

}
