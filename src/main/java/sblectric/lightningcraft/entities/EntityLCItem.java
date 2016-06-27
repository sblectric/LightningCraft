package sblectric.lightningcraft.entities;

import sblectric.lightningcraft.ref.RefMisc;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/** An EntityItem that is immune to damage and lightning */
public class EntityLCItem extends EntityItem {

	public EntityLCItem(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
        ReflectionHelper.setPrivateValue(EntityItem.class, this, 500, RefMisc.DEV ? "health" : "field_70291_e"); // virtually invincible
	}
	
	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt) {}

}
