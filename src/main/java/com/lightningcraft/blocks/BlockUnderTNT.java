package com.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.lightningcraft.entities.EntityLCTNTPrimed;
import com.lightningcraft.items.blocks.ItemBlockRarity;
import com.lightningcraft.registry.IRegistryBlock;

/** Underworld TNT (VERY short fuse and strong splosion) */
public class BlockUnderTNT extends BlockTNT implements IRegistryBlock {
	
	public BlockUnderTNT() {
		super();
		this.setSoundType(SoundType.SAND);
		this.setHardness(10.0f);
		this.setResistance(5.0f);
	}
	
	/** Go boom */
	@Override
    public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
        if (!worldIn.isRemote) {
            if(state.getValue(EXPLODE)) {
                EntityLCTNTPrimed entitytntprimed = new EntityLCTNTPrimed(worldIn, 
                		(double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter);
                worldIn.spawnEntityInWorld(entitytntprimed);
                worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ,
                		SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
	
	/** Go boom when other things go boom */
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            EntityLCTNTPrimed entitytntprimed = new EntityLCTNTPrimed(worldIn, 
            		(double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
            entitytntprimed.fuse = worldIn.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            worldIn.spawnEntityInWorld(entitytntprimed);
        }
    }
	
    @Override
    public int quantityDropped(Random rand) {
        return rand.nextInt(3) + 1;
    }
    
    /** Just drop some gunpowder */
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Items.GUNPOWDER;
    }
    
    /** And never itself */
    @Override
    protected boolean canSilkHarvest() {
    	return false;
    }
    
	@Override
	public Class getItemClass() {
		return ItemBlockRarity.class;
	}
	
	@Override
	public Object[] getItemClassArgs() {
		return new Object[]{EnumRarity.UNCOMMON};
	}

}
