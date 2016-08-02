package sblectric.lightningcraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** The underworld slime */
public class EntityUnderworldSlime extends EntityMagmaCube {

    public EntityUnderworldSlime(World world)
    {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
	public int getTotalArmorValue()
    {
        return this.getSlimeSize() * 3;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
	public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    @Override
	protected EnumParticleTypes getParticleType()
    {
        return EnumParticleTypes.SNOWBALL;
    }

    @Override
	protected EntitySlime createInstance()
    {
        return new EntityUnderworldSlime(this.worldObj);
    }

    @Override
	protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.ICE);
    }
    
	/** Make sure to override the loot table to return null */
	@Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        Item item = this.getDropItem();

        if (item != null && this.getSlimeSize() > 1)
        {
            int j = this.rand.nextInt(4) - 2;

            if (p_70628_2_ > 0)
            {
                j += this.rand.nextInt(p_70628_2_ + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    @Override
	protected int getJumpDelay()
    {
        return super.getJumpDelay() / 2;
    }

    @Override
	protected void alterSquishAmount()
    {
        this.squishAmount *= 0.9F;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    @Override
	protected void jump()
    {
        this.motionY = 0.84F + this.getSlimeSize() * 0.1F;
        this.isAirBorne = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    @Override
	protected boolean canDamagePlayer()
    {
        return true;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    @Override
	protected int getAttackStrength()
    {
        return super.getAttackStrength() + 4;
    }
}