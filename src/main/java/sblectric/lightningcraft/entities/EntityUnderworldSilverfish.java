package sblectric.lightningcraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.world.World;

/** The nasty demonfish */
public class EntityUnderworldSilverfish extends EntitySilverfish {

	public EntityUnderworldSilverfish(World world) {
		super(world);
		this.setSize(0.6F, 1.4F); // double the size of normal silverfish
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
	}

}
