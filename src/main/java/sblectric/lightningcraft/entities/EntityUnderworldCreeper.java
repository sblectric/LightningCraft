package sblectric.lightningcraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sblectric.lightningcraft.ref.RefStrings;

/** The nasty Undercreep */
public class EntityUnderworldCreeper extends EntityCreeper {
	
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(RefStrings.MODID, "entities/underworld_creeper");

	public EntityUnderworldCreeper(World worldIn) {
		super(worldIn);
		
		// these creepers have a bigger explosion radius than normies
		NBTTagCompound tag = new NBTTagCompound();
		byte radius = 6;
		tag.setByte("ExplosionRadius", radius);
		this.readEntityFromNBT(tag);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
	}
	
	/** Underworld creeper loot table */
	@Override
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

}
