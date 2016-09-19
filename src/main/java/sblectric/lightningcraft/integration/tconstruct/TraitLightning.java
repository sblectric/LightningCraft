package sblectric.lightningcraft.integration.tconstruct;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import sblectric.lightningcraft.fluids.LCFluids;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.util.Effect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

/** A trait that has a 25% chance to strike the target with lightning, up to 35% with a critical hit and 10% with each level increase */
public class TraitLightning extends AbstractTraitLeveled {

	public TraitLightning(int levels) {
		super(RefStrings.MODID + ":lightning", LCFluids.moltenElectricium.getColor(), 3, levels);
	}

	@Override
	public void afterHit(ItemStack tool,  EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
		if(wasHit) {
			if(!player.isSneaking() && random.nextDouble() < 0.25 + (wasCritical ? 0.1 : 0) + (0.1 * data.level)) {
				Effect.lightning(target, false);
				tool.damageItem(2, player);
			}
		}
	}

}
