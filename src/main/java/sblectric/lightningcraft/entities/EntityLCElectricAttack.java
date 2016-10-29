package sblectric.lightningcraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCParticles;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.util.WorldUtils;

/** The cannon fire */
public class EntityLCElectricAttack extends EntityFireball {
	
	public double startX, startY, startZ;

	public EntityLCElectricAttack(World world)
	{
		super(world);
		this.startX = this.posX;
		this.startY = this.posY;
		this.startZ = this.posZ;
	}

	public EntityLCElectricAttack(World world, double x, double y, double z, double vx, double vy, double vz)
	{
		super(world, x, y, z, vx, vy, vz);
		this.startX = x;
		this.startY = y;
		this.startZ = z;
	}
	
	public EntityLCElectricAttack(World world, double x, double y, double z, Entity target)
	{
		this(world, x, y, z, 0, 0, 0);
		
		// calculate the future position of the entity
		Vec3d result = target != null ? WorldUtils.estimateEntityPosition(target, x, y, z, 0.75D) : null;
		
		double vx, vy, vz;
		if(target != null) {
			vx = result.xCoord - x;
			vy = result.yCoord + 1 - y;
			vz = result.zCoord - z;
		} else {
			vx = 1.0;
			vy = 1.0;
			vz = 1.0;
		}
		
        double d6 = MathHelper.sqrt_double(vx * vx + vy * vy + vz * vz);
        this.accelerationX = vx / d6 * 0.125D;
        this.accelerationY = vy / d6 * 0.125D;
        this.accelerationZ = vz / d6 * 0.125D;
        
        this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		this.extinguish();
		
		if(this.worldObj.isRemote) { // client stuff
			double d = 0.2125;
			double xoff = 0;
			double yoff = 0;
			double zoff = 0;
			for(int i = 0; i < 12; i++) {
				xoff = rand.nextGaussian();
				yoff = rand.nextGaussian();
				zoff = rand.nextGaussian();
				LCParticles.spawnParticle("electricAttack", posX + (xoff - 0.5) * d, posY + (yoff - 0.5) * d, posZ + (zoff - 0.5) * d, 0, 0, 0);
			}
		} else { // server stuff
			if(this.accelerationX == 0 && this.accelerationY == 0 && this.accelerationZ == 0) {
				this.setDead();
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult mop) {
		
		 if (!this.worldObj.isRemote)
	        {
			 
	            if (mop.entityHit != null)
	            {
	            	// this lightning is strong enough to kill an unarmored player in one shot
	                if (mop.entityHit.attackEntityFrom(new EntityDamageSource(RefStrings.MODID + ":cannon", this), 21.0F))
	                {
	                	if(!mop.entityHit.isImmuneToFire()) mop.entityHit.setFire(2);
	                	this.worldObj.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 6.0F, 2.0F);
	                }
	            }
	            else
	            {
	                BlockPos pos = mop.getBlockPos();
	                int i, j, k = i = j = 0;
	                
	                // ignore the cannon itself
				 	if(this.worldObj.getBlockState(pos).getBlock() == LCBlocks.lightningCannon) {
				 		return;
				 	}

	                switch (mop.sideHit.getIndex())
	                {
	                    case 0:
	                        --j;
	                        break;
	                    case 1:
	                        ++j;
	                        break;
	                    case 2:
	                        --k;
	                        break;
	                    case 3:
	                        ++k;
	                        break;
	                    case 4:
	                        --i;
	                        break;
	                    case 5:
	                        ++i;
	                }
	                
	                pos = pos.add(i, j, k);

	                if (this.worldObj.isAirBlock(pos))
	                {
	                    this.worldObj.setBlockState(pos, Blocks.FIRE.getDefaultState());
	                }
	            }
	            
	            this.worldObj.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 6.0F, 2.0F);
	            this.setDead();
	        }
	}

	/** No striking electricity back! */
	@Override
	public boolean isEntityInvulnerable(DamageSource source) {
		return true;
	}

}
