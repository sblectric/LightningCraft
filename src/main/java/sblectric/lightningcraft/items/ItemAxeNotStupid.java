package sblectric.lightningcraft.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/** Because Forge won't fix the axe class (wow!) */
public class ItemAxeNotStupid extends ItemTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, 
			Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});
	
	public ItemAxeNotStupid(ToolMaterial mat) {
		super(5.0F, -3.2F, mat, EFFECTIVE_ON);
	}
	
    @Override
	public Set<String> getToolClasses(ItemStack stack) {
    	return ImmutableSet.of("axe");
    }

}
