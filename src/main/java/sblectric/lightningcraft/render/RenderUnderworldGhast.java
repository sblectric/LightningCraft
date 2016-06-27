package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.RenderGhast;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.ref.RefStrings;

/** Underworld ghast renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldGhast extends RenderGhast {
	
	protected ResourceLocation texture;
	protected ResourceLocation textureShooting;

	public RenderUnderworldGhast(RenderManager renderManagerIn) {
		super(renderManagerIn);
		setEntityTexture();
	}
	
	@Override
    protected void preRenderCallback(EntityGhast entity, float parTick) {
        GL11.glScalef(3F, 3F, 3F);
    }
	
	protected void setEntityTexture() {
        texture = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_ghast.png");
        textureShooting = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_ghast_shooting.png");
    }
	
	@Override
    protected ResourceLocation getEntityTexture(EntityGhast e) {
        return e.isAttacking() ? textureShooting : texture;
    }

}
