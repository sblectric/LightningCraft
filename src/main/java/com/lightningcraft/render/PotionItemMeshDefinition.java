package com.lightningcraft.render;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.lightningcraft.items.ItemLC;
import com.lightningcraft.items.LCItems;
import com.lightningcraft.ref.RefStrings;

/** Potion model definitions */
public class PotionItemMeshDefinition implements ItemMeshDefinition {
	
	public static final ItemLC item = LCItems.potion;
	public static final String normal = RefStrings.MODID + ":" + item.getShorthandName();
	
	public PotionItemMeshDefinition() {
		ModelBakery.registerItemVariants(item, new ResourceLocation(normal));
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {
        return new ModelResourceLocation(normal, "inventory");
	}

}
