package sblectric.lightningcraft.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

/** Tool helper class */
public class ToolHelper {
	
	private static Map<ToolMaterial, ItemStack> matMap = new HashMap();
	
	/** Set a tool material's repair stack safely */
	public static void setRepairStack(ToolMaterial mat, ItemStack repair) {
		mat.setRepairItem(repair);
		matMap.put(mat, repair);
	}
	
	/** Get a tool material's repair stack safely */
	public static ItemStack getRepairStack(ToolMaterial mat) {
		if(matMap.containsKey(mat)) {
			return matMap.get(mat);
		} else {
			try {
				return mat.getRepairItemStack();
			} catch(Exception e) {
				return null;
			}
		}
	}

}
