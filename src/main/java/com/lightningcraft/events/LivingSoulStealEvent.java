package com.lightningcraft.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class LivingSoulStealEvent extends LivingHurtEvent {
	
	public static final DamageSource soulSteal = new DamageSource("soulSteal").setDamageBypassesArmor();
	
	public LivingSoulStealEvent(EntityLivingBase entity, float amount) {
		super(entity, soulSteal, amount);
	}

}
