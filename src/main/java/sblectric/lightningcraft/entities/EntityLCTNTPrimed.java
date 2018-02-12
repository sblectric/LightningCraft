package sblectric.lightningcraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import sblectric.lightningcraft.blocks.BlockUnderTNT;
import sblectric.lightningcraft.init.LCNetwork;
import sblectric.lightningcraft.network.MessageSyncTNT;

/** LightningCraft TNT entity */
public class EntityLCTNTPrimed extends Entity {

	public int fuse;
	public int variant;
	private EntityLivingBase tntPlacedBy;
	private boolean firstTick = false;

	public EntityLCTNTPrimed(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.fuse = 20;
	}

	public EntityLCTNTPrimed(World world, int variant, double x, double y, double z, EntityLivingBase placer) {
		this(world);
		this.setPosition(x, y, z);
		float f = (float)(Math.random() * Math.PI * 2.0D);
		this.motionX = -((float)Math.sin(f)) * 0.02F;
		this.motionY = 0.20000000298023224D;
		this.motionZ = -((float)Math.cos(f)) * 0.02F;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.variant = variant;
		this.tntPlacedBy = placer;
		this.fuse = 20;
		
	}

	@Override
	protected void entityInit() {}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/** go boom! */
	protected void explode() {
		float f = 22f;
		int i = 3;
		if(variant == BlockUnderTNT.MYSTIC) {
			for(int j = -i; j <= i; j += i) {
				this.world.createExplosion(this, this.posX - i, this.posY + j, this.posZ - i, f, true);
				this.world.createExplosion(this, this.posX + i, this.posY + j, this.posZ - i, f, true);
				this.world.createExplosion(this, this.posX + i, this.posY + j, this.posZ + i, f, true);
				this.world.createExplosion(this, this.posX - i, this.posY + j, this.posZ + i, f, true);
			}
		} else {
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if(!world.isRemote && !firstTick) {
			LCNetwork.net.sendToAllAround(new MessageSyncTNT(this), 
					new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 1024));
			firstTick = true;
		}
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if (this.fuse-- <= 0)
		{
			this.setDead();

			if (!this.world.isRemote)
			{
				this.explode();
			}
		}
		else
		{
			this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setByte("Fuse", (byte)this.fuse);
		tag.setByte("Variant", (byte)this.variant);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.fuse = tag.getByte("Fuse");
		this.variant = tag.getByte("Variant");
	}

	/**
	 * returns null or the entityliving it was placed or ignited by
	 */
	public EntityLivingBase getTntPlacedBy() {
		return this.tntPlacedBy;
	}

}
