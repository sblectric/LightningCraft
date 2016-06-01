package com.lightningcraft.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.lightningcraft.ref.RefStrings;

/** Underworld Silverfish renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSilverfish extends RenderSilverfish {
	protected ResourceLocation texture;

	public RenderUnderworldSilverfish(RenderManager rm) {
		super(rm);
		this.shadowSize = 0.6F;
		setEntityTexture();
	}
	
	@Override
    protected void preRenderCallback(EntitySilverfish entity, float parTick) {
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }
	
	protected void setEntityTexture() {
        texture = new ResourceLocation(RefStrings.MODID + ":textures/entities/underworld_silverfish.png");
    }
	
	@Override
    protected ResourceLocation getEntityTexture(EntitySilverfish e) {
        return texture;
    }
	
}
