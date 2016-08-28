package sblectric.lightningcraft.integration.tconstruct;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import sblectric.lightningcraft.events.PlayerEvents;
import sblectric.lightningcraft.fluids.LCFluids;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.util.SkyUtils;
import slimeknights.tconstruct.library.traits.AbstractTrait;

/** A trait that slowly repairs the tool */
public class TraitRepair extends AbstractTrait {

	public TraitRepair() {
		super(RefStrings.MODID + ":repair", LCFluids.moltenMystic.getColor());
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(entity instanceof EntityPlayer && SkyUtils.canWriteItemNBT(tool, (EntityPlayer)entity)) {
			int damage = tool.getItemDamage();

			if(damage > 0) {
				if(entity.ticksExisted % MathHelper.floor_double(PlayerEvents.repairTime) == 0) {
					tool.setItemDamage(damage - 1);
				}
			}
		}
	}

}
