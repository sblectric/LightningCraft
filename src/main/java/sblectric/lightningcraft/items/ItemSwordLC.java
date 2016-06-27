package sblectric.lightningcraft.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import sblectric.lightningcraft.registry.IRegistryItem;
import sblectric.lightningcraft.util.LCMisc;

/** A basic LightningCraft sword */
public class ItemSwordLC extends ItemSword implements IRegistryItem {

	private EnumRarity rarity;
	private ToolMaterial mat;
	
	public ItemSwordLC(ToolMaterial mat, EnumRarity rarity) {
		super(mat);
		this.mat = mat;
		this.rarity = rarity;
	}
	
	public ItemSwordLC(ToolMaterial mat) {
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
