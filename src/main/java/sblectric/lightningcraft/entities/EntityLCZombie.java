package sblectric.lightningcraft.entities;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sblectric.lightningcraft.init.LCAchievements;
import sblectric.lightningcraft.ref.RefMisc;
import sblectric.lightningcraft.util.WorldUtils;

/** Custom spawned Zombie with singular drive: to kill who you want to! */
public class EntityLCZombie extends EntityZombie {
	
	// entity fields
	private EntityLivingBase target;
	private EntityLivingBase owner;
	private UUID targetID;
	private UUID ownerID;
	private boolean isTargetPlayer;
	private boolean isOwnerPlayer;
	
	// load states
	private static final int stateREADNBT = 1;
	private static final int stateTRIED = 2;
	private static final int stateDONE = 3;
	private int loadstate = 0;
	private int targetTries = 0;
	private int ownerTries = 0;
	private int tries = 0;
	private static final int tryLimit = 32;
	
	public EntityLCZombie(World world) {
		super(world);
	}
	
	public EntityLCZombie(World world, EntityLivingBase target, EntityLivingBase owner) {
		super(world);
		this.target = target; this.targetID = target.getPersistentID();
		this.owner = owner; this.ownerID = owner.getPersistentID();
		this.isTargetPlayer = (target instanceof EntityPlayer);
		this.isOwnerPlayer = (owner instanceof EntityPlayer);
		this.removeTargetTasks();
		this.setTargetTask();
		this.loadstate = stateDONE;
	}
	
	private void removeTargetTasks() {
		
		// remove target tasks
		Set<EntityAITaskEntry> entries = this.targetTasks.taskEntries;
		Iterator<EntityAITaskEntry> iter = entries.iterator();
		while(iter.hasNext()){
			EntityAITaskEntry e = iter.next();
			iter.remove();
		}
	}
	
	private void setTargetTask() {
		// add new target task
		if(target != null) {
			this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		}
	}
	
	// active zombie load
	private void getData() {
		// initialize once
		if(this.loadstate == stateREADNBT) this.removeTargetTasks();
		
		// no-go case
		if(this.targetID == null && this.ownerID == null) {
			this.loadstate = stateDONE;
			return;
		}
		
		// try to get entities
		if(this.target == null)	this.target = this.targetID != null ? WorldUtils.getEntityLivingBaseFromUUID(world, targetID, isTargetPlayer) : null;
		if(this.owner == null)	this.owner = this.ownerID != null ? WorldUtils.getEntityLivingBaseFromUUID(world, ownerID, isOwnerPlayer): null;
		
		// set read state
		if(this.target == null && this.owner == null) {
			this.loadstate = stateTRIED;
			this.tries++;
			if(tries >= tryLimit) this.loadstate = stateDONE;
		} else {
			
			// give a bit of delay for a player in need
			if(isTargetPlayer && this.target == null && targetTries < tryLimit) {
				targetTries++;
				this.loadstate = stateTRIED;
				return;
			} 
			if(isOwnerPlayer && this.owner == null && ownerTries < tryLimit) {
				ownerTries++;
				this.loadstate = stateTRIED;
				return;
			}
			
			// success
			this.loadstate = stateDONE;
			this.setTargetTask();
		}
	}
	
	/** attack with anger */
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.world.isRemote) {
			
			// read in new data
			if((loadstate == stateREADNBT || loadstate == stateTRIED) && target == null || owner == null) this.getData();
			
			// make sure current target doesn't go into creative mode
			if(target != null && isTargetPlayer && ((EntityPlayer)target).capabilities.isCreativeMode) {
				target = null;
			}
			
			// set target
			if(target != null && target.isDead == false) {
				this.setAttackTarget(target);
				this.setRevengeTarget(target);
			} else {
				// their foe is gone
				if(loadstate == stateDONE) {
					
					// achievement get!
					if(owner != null && target != null && target.isDead && isOwnerPlayer && this.getHealth() > 0) {
						((EntityPlayer)owner).addStat(LCAchievements.specialSwordKill, 1);
					}
					this.die(); // the zombie has no more purpose
				}
			}
		}
	}
	
	
	/** Attacking mobs */
	@Override
	public boolean attackEntityAsMob(Entity victim) {
		if(!victim.world.isRemote) {
			if(victim instanceof EntityLivingBase) {
				// make sure to make the victim drop stuff as if it were a player kill
				// via reflection
				Class t = EntityLivingBase.class;
				String recentlyHit = RefMisc.DEV ? "recentlyHit" : "field_70718_bc";
				try {
					Field playerHit = t.getDeclaredField(recentlyHit);
					playerHit.setAccessible(true);
					playerHit.setInt(victim, 60); // same as player or wolf
				} catch (NoSuchFieldException e) {
					System.out.println("Could not set recentlyHit on attacked entity: field does not exist");
				} catch (IllegalAccessException e) {
					System.out.println("Could not set recentlyHit on attacked entity: field is not accessible");
				}
			}
	    }
		return super.attackEntityAsMob(victim);
	}
	
	/** get the owner of this Zombie */
	public EntityLivingBase getOwner() {
		return this.owner;
	}
	
	/** the brave soldier has done his duty */
	private void die() {
		if(this.getHealth() > 0) this.playSound(SoundEvents.ENTITY_ZOMBIE_DEATH, 1.0F, 1.0F);
		this.setHealth(0);
	} 
	
	/** drop no XP */
	@Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 0;
    }
	
	/** Make sure to override the loot table to return null */
	@Override
    protected ResourceLocation getLootTable() {
        return null;
    }
	
	/** NOTHING AT ALL */
	@Override
    protected boolean canDropLoot() {
        return false;
    }
	
	/** drop no items */
	@Override
	protected Item getDropItem() {
        return null;
    }
	
	/** NBT Read */
	@Override 
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		// get UUIDs
		String targetStr = tag.getString("target");
		String ownerStr = tag.getString("owner");
		this.targetID = UUID.fromString(targetStr);
		this.ownerID = UUID.fromString(ownerStr);
		
		// is the entity a player?
		this.isTargetPlayer = tag.getBoolean("isTargetPlayer");
		this.isOwnerPlayer = tag.getBoolean("isOwnerPlayer");
		
		// set to null for now
		this.target = null;
		this.owner = null;
		
		this.loadstate = stateREADNBT;
	}
	
	/** NBT Write */
	@Override 
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		// set UUIDS
		if(this.target != null) {
			tag.setString("target", this.targetID.toString());
			tag.setBoolean("isTargetPlayer", this.isTargetPlayer);
		}
		if(this.owner != null) {
			tag.setString("owner", this.ownerID.toString());
			tag.setBoolean("isOwnerPlayer", this.isOwnerPlayer);
		}
		return tag;
	}
}
