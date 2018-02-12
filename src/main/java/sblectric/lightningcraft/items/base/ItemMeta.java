package sblectric.lightningcraft.items.base;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.init.LCCreativeTabs;
import sblectric.lightningcraft.registry.ClientRegistryHelper;

/** An item with metadata */
public class ItemMeta extends ItemLC {
	
	public int nSubItems;
	public boolean sameIcon;
	public boolean[] hasEffect;
	
	/** An item with metadata */
	public ItemMeta(int nSubItems, boolean sameIcon, boolean... hasEffect) {
		super();
		this.nSubItems = nSubItems;
		this.sameIcon = sameIcon;
		this.hasSubtypes = true;
		this.hasEffect = hasEffect;
	}
	
	public ItemMeta(int nSubItems) {
		this(nSubItems, false, false);
	}
	
	public ItemMeta(int nSubItems, EnumRarity rarity) {
		super(rarity);
		this.nSubItems = nSubItems;
		this.sameIcon = false;
		this.hasSubtypes = true;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList sub) {
		if(tab == LCCreativeTabs.items) {
			for(int meta = 0; meta < nSubItems; meta++) {
				sub.add(new ItemStack(this, 1, meta));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		int index = stack.getItemDamage();
		if(hasEffect != null) {
			return hasEffect[Math.min(index, hasEffect.length - 1)];
		} else {
			return false;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerRender() {
		JointList<ResourceLocation> names = new JointList();
		if(sameIcon) {
			for(int meta = 0; meta < nSubItems; meta++) {
				ClientRegistryHelper.registerModel(this, meta, new ModelResourceLocation(this.getRegistryName(), "inventory"));
			}
		} else {
			for(int meta = 0; meta < nSubItems; meta++) {
				ClientRegistryHelper.registerModel(this, meta, new ModelResourceLocation(this.getRegistryName() + "_" + meta, "inventory"));
			}
		}
	}

}
