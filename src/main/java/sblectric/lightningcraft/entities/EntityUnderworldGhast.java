package sblectric.lightningcraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.world.World;

/** The frightening Underworld ghast */
public class EntityUnderworldGhast extends EntityGhast {

	public EntityUnderworldGhast(World worldIn) {
		super(worldIn);
		this.setSize(3.0F, 3.0F);
	}
	
	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(150);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.4);
    }

}
