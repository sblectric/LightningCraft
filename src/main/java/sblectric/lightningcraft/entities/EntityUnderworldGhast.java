package sblectric.lightningcraft.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCSoundEvents;
import sblectric.lightningcraft.ref.Log;

/** The frightening Underworld ghast */
public class EntityUnderworldGhast extends EntityGhast {

	public EntityUnderworldGhast(World worldIn) {
		super(worldIn);
		this.setSize(3.0F, 3.0F);
	}
	
	// Remove the default Ghast fireball AI and add our own
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		EntityAIBase remove = null;
		for(EntityAITaskEntry task : this.tasks.taskEntries) {
			if(task.action.getClass().getSimpleName().equals("AIFireballAttack")) {
				remove = task.action;
				break;
			}
		}
		if(remove != null) {
			this.tasks.removeTask(remove);
			this.tasks.addTask(7, new AIUnderworldFireballAttack(this));
		} else {
			Log.logger.error(this.getName() + " failed to override Ghast fireball AI.");
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(150);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.4);
	}
	
	// now twice as strong as normal ghasts
	@Override
	public int getFireballStrength() {
		return super.getFireballStrength() * 2;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if(!LCConfig.useVanillaGhastSounds) {
			return LCSoundEvents.underworldGhastMoan;
		} else {
			return super.getAmbientSound();
		}
	}

	@Override
	protected SoundEvent getHurtSound() {
		if(!LCConfig.useVanillaGhastSounds) {
			return LCSoundEvents.underworldGhastHurt;
		} else {
			return super.getHurtSound();
		}
	}

	@Override
	protected SoundEvent getDeathSound() {
		if(!LCConfig.useVanillaGhastSounds) {
			return LCSoundEvents.underworldGhastDeath;
		} else {
			return super.getDeathSound();
		}
	}

	@Override
	protected float getSoundVolume() {
		return super.getSoundVolume() * 0.75F;
	}

	@Override
	protected float getSoundPitch() {
		if(!LCConfig.useVanillaGhastSounds) {
			return super.getSoundPitch() * 1.125F;
		} else {
			return super.getSoundPitch() * 1.25F;
		}
	}

	/** Customized fireball AI to play the correct sound */
	public static class AIUnderworldFireballAttack extends EntityAIBase {
		
		private EntityUnderworldGhast parentEntity;
		public int attackTimer;

		public AIUnderworldFireballAttack(EntityUnderworldGhast ghast) {
			this.parentEntity = ghast;
		}

		@Override
		public boolean shouldExecute() {
			return this.parentEntity.getAttackTarget() != null;
		}

		@Override
		public void startExecuting() {
			this.attackTimer = 0;
		}

		@Override
		public void resetTask() {
			this.parentEntity.setAttacking(false);
		}

		@Override
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
			double d0 = 64.0D;

			if (entitylivingbase.getDistanceSqToEntity(this.parentEntity) < d0 * d0 && this.parentEntity.canEntityBeSeen(entitylivingbase)) {
				World world = this.parentEntity.worldObj;
				++this.attackTimer;

				if (this.attackTimer == 10) {
					if(!LCConfig.useVanillaGhastSounds) {
						world.playSound(null, new BlockPos(this.parentEntity), LCSoundEvents.underworldGhastWarn, SoundCategory.HOSTILE, 
								10.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
					} else {
						world.playEvent((EntityPlayer)null, 1015, new BlockPos(this.parentEntity), 0);
					}
				}

				if (this.attackTimer == 20) {
					double d1 = 4.0D;
					Vec3d vec3d = this.parentEntity.getLook(1.0F);
					double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.xCoord * d1);
					double d3 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F - 
							(0.5D + this.parentEntity.posY + this.parentEntity.height / 2.0F);
					double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.zCoord * d1);
					world.playEvent((EntityPlayer)null, 1016, new BlockPos(this.parentEntity), 0);
					EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.parentEntity, d2, d3, d4);
					entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
					entitylargefireball.posX = this.parentEntity.posX + vec3d.xCoord * d1;
					entitylargefireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0F + 0.5D;
					entitylargefireball.posZ = this.parentEntity.posZ + vec3d.zCoord * d1;
					world.spawnEntityInWorld(entitylargefireball);
					this.attackTimer = -30;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}

			this.parentEntity.setAttacking(this.attackTimer > 10);
		}
	}

}
