package sblectric.lightningcraft.blocks;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import sblectric.lightningcraft.items.blocks.ItemBlockRarity;
import sblectric.lightningcraft.registry.IRegistryBlock;
import sblectric.lightningcraft.tiles.TileEntityChargingPlate;

/** The charging plate */
public class BlockChargingPlate extends BlockPressurePlate implements IRegistryBlock, ITileEntityProvider {

	public BlockChargingPlate() {
		super(Material.IRON, Sensitivity.MOBS);
	}
	
    // Like a regular pressure plate, but provides charging power
	@Override
	public TileEntity createNewTileEntity(World world, int par2) {
		return new TileEntityChargingPlate();
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
