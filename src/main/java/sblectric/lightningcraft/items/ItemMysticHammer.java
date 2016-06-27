package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import sblectric.lightningcraft.items.ifaces.IAutoRepair;
import sblectric.lightningcraft.util.SkyUtils;

/** A hammer that will auto-repair */
public class ItemMysticHammer extends ItemSkyHammer implements IAutoRepair {

	public ItemMysticHammer(ToolMaterial mat) {
		super(mat);
	}

}
