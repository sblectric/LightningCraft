package sblectric.lightningcraft.items;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import com.google.common.collect.Multimap;
import sblectric.lightningcraft.items.ifaces.IMysticGear;
import sblectric.lightningcraft.util.SkyUtils;

/** The Mystic pickaxe */
public class ItemMysticPick extends ItemSkyPick implements IMysticGear {

	public ItemMysticPick(ToolMaterial mat) {
		super(mat);
	}

}
