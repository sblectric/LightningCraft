package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import sblectric.lightningcraft.items.ifaces.IMysticGear;
import sblectric.lightningcraft.util.SkyUtils;

/** A shovel that will auto-repair */
public class ItemMysticSpade extends ItemSkySpade implements IMysticGear {

	public ItemMysticSpade(ToolMaterial mat) {
		super(mat);
	}

}
