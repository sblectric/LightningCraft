package sblectric.lightningcraft.items.base;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;
import sblectric.lightningcraft.util.LCMisc;
import sblectric.lightningcraft.util.ToolHelper;

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
		if(rarity == DYNAMIC) rarity = LCMisc.getRarityFromStack(ToolHelper.getRepairStack(mat));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return rarity;
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        ItemStack matStack = ToolHelper.getRepairStack(mat);
        return (matStack != null && OreDictionary.itemMatches(matStack, repair, false));
    }

}
