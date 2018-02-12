package sblectric.lightningcraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.ref.RefStrings;

/** The underworld slime */
public class EntityUnderworldSlime extends EntityMagmaCube {
	
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(RefStrings.MODID, "entities/underworld_slime");

    public EntityUnderworldSlime(World world) {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    @Override
	public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
	public float getBrightness(float p_70013_1_) {
        return 1.0F;
    }

    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    @Override
	protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SNOWBALL;
    }

    @Override
	protected EntitySlime createInstance() {
        return new EntityUnderworldSlime(this.world);
    }
    
	/** The Underworld Slime loot table */
	@Override
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    @Override
	protected int getJumpDelay() {
        return super.getJumpDelay() / 2;
    }

    @Override
	protected void alterSquishAmount() {
        this.squishAmount *= 0.9F;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    @Override
	protected void jump() {
        this.motionY = 0.84F + this.getSlimeSize() * 0.1F;
        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    @Override
	protected boolean canDamagePlayer() {
        return true;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    @Override
	protected int getAttackStrength() {
        return super.getAttackStrength() + 4;
    }
}