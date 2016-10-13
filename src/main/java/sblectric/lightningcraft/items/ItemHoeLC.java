package sblectric.lightningcraft.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;
import sblectric.lightningcraft.util.LCMisc;

/** A basic LightningCraft hoe */
public class ItemHoeLC extends ItemHoe implements ILightningCraftItem {
	
	private EnumRarity rarity;
	private ToolMaterial mat;
	
	public ItemHoeLC(ToolMaterial mat, EnumRarity rarity) {
		super(mat);
		this.mat = mat;
		this.rarity = rarity;
	}
	
	public ItemHoeLC(ToolMaterial mat) {
		this(mat, DYNAMIC);
	}
	
	@Override
	public void setRarity() {
		if(rarity == DYNAMIC) rarity = LCMisc.getRarityFromStack(mat.getRepairItemStack());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack){
		return rarity;
	}

}
