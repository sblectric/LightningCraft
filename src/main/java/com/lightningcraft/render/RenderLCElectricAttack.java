package com.lightningcraft.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lightningcraft.entities.EntityLCElectricAttack;

public class RenderLCElectricAttack extends Render<EntityLCElectricAttack> {

	public RenderLCElectricAttack(RenderManager renderManager) {
		super(renderManager);
	}

	public void doRender(EntityLCElectricAttack e, double x, double y, double z, float par8, float par9) {
		// do nothing
	}

	/** no texture */
	@Override
	protected ResourceLocation getEntityTexture(EntityLCElectricAttack e) {
		return null;
	}

}
