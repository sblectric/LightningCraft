package com.lightningcraft.render;

import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.ref.RefStrings;

/** Underworld Slime renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSlime extends RenderMagmaCube {
	protected ResourceLocation texture;
	
	public RenderUnderworldSlime(RenderManager rm) {
		super(rm);
		setEntityTexture();
	}
	
	protected void setEntityTexture() {
        texture = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_slime.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMagmaCube e) {
        return texture;
    }

}
