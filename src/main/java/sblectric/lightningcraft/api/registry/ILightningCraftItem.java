package sblectric.lightningcraft.api.registry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.registry.ClientRegistryHelper;

/** Interface to help with item registration */
public interface ILightningCraftItem extends ILightningCraftObject {
	
	/** Called to set the item's rarity defaults */
	public default void setRarity() {}
	
	@SideOnly(Side.CLIENT)
	@Override
	public default void registerRender() {
		ClientRegistryHelper.registerModel((Item)this, 0, new ModelResourceLocation(((Item)this).getRegistryName(), "inventory"));
	}
	
}
