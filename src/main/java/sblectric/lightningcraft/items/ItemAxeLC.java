package sblectric.lightningcraft.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;
import sblectric.lightningcraft.util.LCMisc;

/** A basic LightningCraft axe */
public class ItemAxeLC extends ItemAxeNotStupid implements ILightningCraftItem {
	
	private EnumRarity rarity;
	private ToolMaterial mat;

	public ItemAxeLC(ToolMaterial mat, EnumRarity rarity) {
		super(mat);
		this.mat = mat;
		this.rarity = rarity;
	}
	
	public ItemAxeLC(ToolMaterial mat) {
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
