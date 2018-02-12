package sblectric.lightningcraft.api.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.registry.ClientRegistryHelper;

/** Interface to help with block registration */
public interface ILightningCraftBlock extends ILightningCraftObject {
	
	/** Gets the block's item class to use when registering */
	public default Class<? extends ItemBlock> getItemClass() {
		return ItemBlock.class;
	}
	
	/** Gets additional arguments to pass through to the ItemBlock constructor */
	public default Object[] getItemClassArgs() {
		return new Object[0];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public default void registerRender() {
		ClientRegistryHelper.registerModel(Item.getItemFromBlock((Block)this), 0, new ModelResourceLocation(((Block)this).getRegistryName(), "inventory"));
	}

}
