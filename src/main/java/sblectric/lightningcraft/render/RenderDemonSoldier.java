package sblectric.lightningcraft.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityDemonSoldier;
import sblectric.lightningcraft.ref.RefStrings;

/** Demon Soldier renderer */
@SideOnly(Side.CLIENT)
public class RenderDemonSoldier extends RenderBiped<EntityDemonSoldier> {
	
	public static final IRenderFactory<EntityDemonSoldier> FACTORY = new Factory();
	private static final ResourceLocation TEXTURE = new ResourceLocation(RefStrings.MODID, "textures/entities/demon_soldier.png");
	
	public RenderDemonSoldier(RenderManager rm) {
		super(rm, new ModelBiped(), 0.5F, 1.0F); // use the player model
		this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this));
	}

    @Override
    protected ResourceLocation getEntityTexture(EntityDemonSoldier e) {
        return TEXTURE;
    }
    
    /** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityDemonSoldier> {

		@Override
		public Render<? super EntityDemonSoldier> createRenderFor(RenderManager manager) {
			return new RenderDemonSoldier(manager);
		}
    	
    }

}
