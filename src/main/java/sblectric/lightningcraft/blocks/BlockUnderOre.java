package sblectric.lightningcraft.blocks;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import sblectric.lightningcraft.blocks.base.BlockMeta;

/** The class for all of the Underworld ore variants in the mod */
public class BlockUnderOre extends BlockMeta {
	
	// number of variants
	public static final int nVariants = 4;

	// ores
	public static final int IRON = 0;
	public static final int GOLD = 1;
	public static final int DIAMOND = 2;
	public static final int EMERALD = 3;
	
	public BlockUnderOre() {
		super(Blocks.STONE, nVariants, 10, 100, false);
	}
	
	/** Add an achievement for harvesting this ore */
//	@Override
//    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
//		if(!worldIn.isRemote) player.addStat(LCAchievements.mineUnderworldOre, 1);
//    }
	
	/** Get the dropped item */
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		switch(this.getMetaFromState(state)) {
		case DIAMOND:
			return Items.DIAMOND;
		case EMERALD:
			return Items.EMERALD;
		default:
			return Item.getItemFromBlock(this);
		}
	}
	
	/** Get the dropped metadata */
	@Override
	public int damageDropped(IBlockState state) {
		int meta = this.getMetaFromState(state);
		switch(meta) {
		case DIAMOND:
		case EMERALD:
			return 0;
		default:
			return meta;
		}
	}
	
	/** fortune */
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		switch(this.getMetaFromState(state)) {
		case DIAMOND:
		case EMERALD:
			return 2 + random.nextInt(1 + fortune);
		default:
			return 1;
		}
	}

}
