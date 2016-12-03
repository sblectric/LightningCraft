package sblectric.lightningcraft.entities;

import java.awt.Color;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import sblectric.lightningcraft.api.util.StackHelper;
import sblectric.lightningcraft.ref.RefStrings;

/** The sp00ky underworld skeleton */
public class EntityUnderworldSkeleton extends EntitySkeleton {
	
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation(RefStrings.MODID, "entities/underworld_skeleton");

	public EntityUnderworldSkeleton(World world) {
		super(world);
		this.isImmuneToFire = false;
	}
	
	/** Extra health and speed */
	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.325D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }
	
    /** Give him a sword, not a bow! */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        boolean hasBow = this.rand.nextBoolean();
        if(!hasBow) {
        	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
        } else {
        	this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
        }
        
        ItemStack leather_helmet = new ItemStack(Items.LEATHER_HELMET); StackHelper.setStackColor(leather_helmet, new Color(1,0,0));
        ItemStack leather_chestplate = new ItemStack(Items.LEATHER_CHESTPLATE); StackHelper.setStackColor(leather_chestplate, new Color(1,0,0));
        ItemStack leather_leggings = new ItemStack(Items.LEATHER_LEGGINGS); StackHelper.setStackColor(leather_leggings, new Color(1,0,0));
        ItemStack leather_boots = new ItemStack(Items.LEATHER_BOOTS); StackHelper.setStackColor(leather_boots, new Color(1,0,0));
        
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, leather_helmet);
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, leather_chestplate);
        this.setItemStackToSlot(EntityEquipmentSlot.LEGS, leather_leggings);
        this.setItemStackToSlot(EntityEquipmentSlot.FEET, leather_boots);
    }
	
	/** Underworld skeleton loot table */
	@Override
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

}
