package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityUnderworldCreeper;
import sblectric.lightningcraft.ref.RefStrings;

/** Underworld Creeper renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldCreeper extends RenderCreeper {
	
	public static final IRenderFactory<EntityUnderworldCreeper> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_creeper.png");
	
	public RenderUnderworldCreeper(RenderManager rm) {
		super(rm);
	}

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper e) {
        return TEXTURE;
    }
    
    /** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityUnderworldCreeper> {

		@Override
		public Render<? super EntityUnderworldCreeper> createRenderFor(RenderManager manager) {
			return new RenderUnderworldCreeper(manager);
		}
    	
    }

}
