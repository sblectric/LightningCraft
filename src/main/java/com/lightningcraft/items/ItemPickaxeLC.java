package com.lightningcraft.items;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.ref.RefStrings;
import com.lightningcraft.registry.IRegistryItem;
import com.lightningcraft.util.LCMisc;

/** A basic LightningCraft pickaxe */
public class ItemPickaxeLC extends ItemPickaxe implements IRegistryItem {

	private EnumRarity rarity;
	private ToolMaterial mat;
	
	public ItemPickaxeLC(ToolMaterial mat, EnumRarity rarity) {
		super(mat);
		this.mat = mat;
		this.rarity = rarity;
	}
	
	public ItemPickaxeLC(ToolMaterial mat) {
		this(mat, DYNAMIC);
	}

	@Override
	public String getShorthandName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender(ItemModelMesher mesher) {
		mesher.register(this, 0, new ModelResourceLocation(RefStrings.MODID + ":" + this.getShorthandName(), "inventory"));
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
