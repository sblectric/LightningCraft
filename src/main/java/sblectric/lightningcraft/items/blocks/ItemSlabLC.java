package sblectric.lightningcraft.items.blocks;

import sblectric.lightningcraft.blocks.LCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

/** The slab item */
public class ItemSlabLC extends ItemSlab {
	
	public ItemSlabLC(Block block, BlockSlab single, BlockSlab db) {
		super(block, single, db);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public ItemSlabLC(Block block) {
		this(block, LCBlocks.slabBlock, LCBlocks.slabBlockDouble);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage() % 8;
    }

}
