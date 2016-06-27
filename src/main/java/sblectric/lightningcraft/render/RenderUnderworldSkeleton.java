package sblectric.lightningcraft.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.ref.RefStrings;

/** Underworld Skeleton renderer */
@SideOnly(Side.CLIENT)
public class RenderUnderworldSkeleton extends RenderSkeleton {
	
	protected ResourceLocation texture;
	
	public RenderUnderworldSkeleton(RenderManager rm) {
		super(rm);
		setEntityTexture();
	}
	
	protected void setEntityTexture() {
        texture = new ResourceLocation(RefStrings.MODID, "textures/entities/underworld_skeleton.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySkeleton e) {
        return texture;
    }

}
