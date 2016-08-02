package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import sblectric.lightningcraft.entities.EntityLCElectricAttack;

public class RenderLCElectricAttack extends Render<EntityLCElectricAttack> {

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

}
