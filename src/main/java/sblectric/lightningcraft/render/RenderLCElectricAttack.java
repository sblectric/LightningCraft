package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.entities.EntityLCElectricAttack;

/** Electic cannon fire (invisible, uses particles instead of renderer) */
@SideOnly(Side.CLIENT)
public class RenderLCElectricAttack extends Render<EntityLCElectricAttack> {
	
	public static final IRenderFactory<EntityLCElectricAttack> FACTORY = new Factory();

	public RenderLCElectricAttack(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityLCElectricAttack e, double x, double y, double z, float par8, float par9) {
		// do nothing
	}

	/** no texture */
	@Override
	protected ResourceLocation getEntityTexture(EntityLCElectricAttack e) {
		return null;
	}
	
	/** Its factory for registration */
    private static class Factory implements IRenderFactory<EntityLCElectricAttack> {

		@Override
		public Render<? super EntityLCElectricAttack> createRenderFor(RenderManager manager) {
			return new RenderLCElectricAttack(manager);
		}
    	
    }

}
