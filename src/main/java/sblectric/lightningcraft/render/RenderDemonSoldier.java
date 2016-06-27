package sblectric.lightningcraft.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.entities.EntityDemonSoldier;
import sblectric.lightningcraft.ref.RefStrings;

/** Demon Soldier renderer */
@SideOnly(Side.CLIENT)
public class RenderDemonSoldier extends RenderBiped<EntityDemonSoldier> {
	
	protected ResourceLocation texture;
	
	public RenderDemonSoldier(RenderManager rm) {
		super(rm, new ModelBiped(), 0.5F, 1.0F); // use the player model
		setEntityTexture();
		this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this));
	}
	
	protected void setEntityTexture() {
        texture = new ResourceLocation(RefStrings.MODID, "textures/entities/demon_soldier.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityDemonSoldier e) {
        return texture;
    }

}
