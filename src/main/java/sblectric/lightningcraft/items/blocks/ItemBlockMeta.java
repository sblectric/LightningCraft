package sblectric.lightningcraft.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/** An item of a BlockMeta */
public class ItemBlockMeta extends ItemBlock {
	
	/** An item of a BlockMeta */
	public ItemBlockMeta(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
    }

}
