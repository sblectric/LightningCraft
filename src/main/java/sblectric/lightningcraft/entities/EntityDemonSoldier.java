package sblectric.lightningcraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import sblectric.lightningcraft.achievements.LCAchievements;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.potions.LCPotions;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.sounds.LCSoundEvents;
import sblectric.lightningcraft.util.JointList;
import sblectric.lightningcraft.util.SkyUtils;

/** The demon soldier entity */
public class EntityDemonSoldier extends EntityMob {
	
	// fields
	protected boolean isTough;
	
	/** make the demon */
	public EntityDemonSoldier(World world, boolean isTough) {
		this(world);
		this.isTough = isTough;
	}

	/** make the demon */
	public EntityDemonSoldier(World world) {
		super(world);
		
		// reset entity state
		this.isTough = false;
		
		// chance to be really mean
		if(this.rand.nextDouble() <= 0.01) {
			this.isTough = true;
		}
				
		// hostile AI
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.setSize(0.6F, 1.8F);
        
        // hell mob
        this.isImmuneToFire = true;
	}
	
	/** make sure AI is enabled */
	@Override
	public boolean isAIDisabled() {
	   return false;
	}
	
	/** the demon's stats */
	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }
	
	/** update the demon */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(!this.worldObj.isRemote){
					
			// find nearest insolent player
			if(this.getAttackTarget() == null) {
				EntityPlayer p = (EntityPlayer)this.findPlayerToAttack();
				
				if(p != null) {
					this.setAttackTarget(p);
					p.addChatMessage(new TextComponentString(
							"[Demon Soldier] " + LCText.secSign + "4" + LCText.secSign + "oYou will pay for your insolence." + LCText.secSign + "r"));
				}
			} else {
				// return to being pleasant if the player enters non-survival
				if(this.getAttackTarget() instanceof EntityPlayer && ((EntityPlayer)this.getAttackTarget()).capabilities.disableDamage) {
					this.setAttackTarget(null);
				}
			}
			
			// get hurt when under the Demon Warding effect, and lose the target (once per two seconds)
			if(this.ticksExisted % 40 == 0 && this.getActivePotionEffect(LCPotions.demonFriend) != null) {
				this.setAttackTarget(null);
				this.attackEntityFrom(DamageSource.magic, 0.5F);
			}
			
		}
	}
	
    protected Entity findPlayerToAttack() {
    	EntityPlayerMP p = (EntityPlayerMP)this.worldObj.getClosestPlayerToEntity(this, 16.0D);
        return p != null && !p.capabilities.disableDamage && this.canEntityBeSeen(p) && SkyUtils.isPlayerInsolent(p) ? p : null;
    }
	
	/**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
		boolean flag = super.attackEntityFrom(source, damage);
		if(!this.worldObj.isRemote) {
			Entity p = source.getEntity();
			
			// foolish you
			if(this.getHealth() > 0 && p != null && p instanceof EntityPlayerMP && 
					this.getAttackTarget() != p && !((EntityPlayerMP)p).capabilities.disableDamage) {
				((EntityPlayerMP)p).addChatMessage(new TextComponentString(
						"[Demon Soldier] " + LCText.secSign + "4" + LCText.secSign + "oWhat a fool." + LCText.secSign + "r"));
				this.setAttackTarget((EntityPlayerMP)p); // switch it up
			}
			
			// kill the demon for an achievement
			if(this.getHealth() <= 0 && p instanceof EntityPlayer) {
				((EntityPlayer)p).addStat(LCAchievements.killDemon, 1);
			}
		}
    	return flag;
    }
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		super.onInitialSpawn(difficulty, livingdata);
		this.armDemon();
		return livingdata;
	}
	
	/** give the demon some artillery */
	protected void armDemon() {
		// add a sword
		JointList<ItemStack> swords = new JointList().join(
			new ItemStack(LCItems.soulSword),
			new ItemStack(LCItems.zombieSword),
			new ItemStack(LCItems.featherSword),
			new ItemStack(LCItems.enderSword),
			new ItemStack(LCItems.blazeSword),
			new ItemStack(LCItems.iceSword)
		);
		
		int sel = this.rand.nextInt(swords.size());
		ItemStack sword = isTough ? new ItemStack(LCItems.skySword) : swords.get(sel);
		sword.addEnchantment(Enchantments.FIRE_ASPECT, 2);
		this.setHeldItem(EnumHand.MAIN_HAND, sword);
		
		// add armor
		if(isTough) {
			this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
			this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
			this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
		}
	}
	
	 /** Armor value */
    @Override
	public int getTotalArmorValue() {
        int i = super.getTotalArmorValue() + 8;
        if (i > 23) {
            i = 23;
        }
        return i;
    }
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
	protected SoundEvent getAmbientSound() {
        return LCSoundEvents.demonSoldierIdle;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected SoundEvent getHurtSound() {
    	return LCSoundEvents.demonSoldierHurt;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected SoundEvent getDeathSound() {
    	return LCSoundEvents.demonSoldierDeath;
    }
    
    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
	protected void dropFewItems(boolean playerkill, int looting) {
        int j = this.rand.nextInt(3 + looting);
        int k;

        // common drops
        for (k = 0; k < j; k++) {
            this.dropItem(Items.BLAZE_POWDER, 1);
        }

        j = this.rand.nextInt(2 + looting);

        for (k = 0; k < j; k++) {
        	this.entityDropItem(new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD), 0);
        }
        
    }
    
    /** NBT Read */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.isTough = tag.getBoolean("isTough");
	}
	
	/** NBT Write */
	@Override 
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setBoolean("isTough", this.isTough);
		return tag;
	}

}
