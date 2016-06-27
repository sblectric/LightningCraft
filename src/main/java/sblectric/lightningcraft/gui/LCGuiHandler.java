package sblectric.lightningcraft.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import sblectric.lightningcraft.gui.client.GuiEnchReallocator;
import sblectric.lightningcraft.gui.client.GuiLightningCannon;
import sblectric.lightningcraft.gui.client.GuiLightningCell;
import sblectric.lightningcraft.gui.client.GuiLightningCrusher;
import sblectric.lightningcraft.gui.client.GuiLightningFurnace;
import sblectric.lightningcraft.gui.client.GuiLightningInfuser;
import sblectric.lightningcraft.gui.client.GuiLightningReceiver;
import sblectric.lightningcraft.gui.client.GuiRFProvider;
import sblectric.lightningcraft.gui.client.GuiRFReceiver;
import sblectric.lightningcraft.gui.client.GuiStaticGenerator;
import sblectric.lightningcraft.gui.server.ContainerEnchReallocator;
import sblectric.lightningcraft.gui.server.ContainerLightningCannon;
import sblectric.lightningcraft.gui.server.ContainerLightningCell;
import sblectric.lightningcraft.gui.server.ContainerLightningCrusher;
import sblectric.lightningcraft.gui.server.ContainerLightningFurnace;
import sblectric.lightningcraft.gui.server.ContainerLightningInfuser;
import sblectric.lightningcraft.gui.server.ContainerLightningReceiver;
import sblectric.lightningcraft.gui.server.ContainerRFProvider;
import sblectric.lightningcraft.gui.server.ContainerRFReceiver;
import sblectric.lightningcraft.gui.server.ContainerStaticGenerator;
import sblectric.lightningcraft.tiles.TileEntityEnchReallocator;
import sblectric.lightningcraft.tiles.TileEntityLightningCannon;
import sblectric.lightningcraft.tiles.TileEntityLightningCell;
import sblectric.lightningcraft.tiles.TileEntityLightningCrusher;
import sblectric.lightningcraft.tiles.TileEntityLightningFurnace;
import sblectric.lightningcraft.tiles.TileEntityLightningInfuser;
import sblectric.lightningcraft.tiles.TileEntityLightningReceiver;
import sblectric.lightningcraft.tiles.TileEntityRFProvider;
import sblectric.lightningcraft.tiles.TileEntityRFReceiver;
import sblectric.lightningcraft.tiles.TileEntityStaticGenerator;

/** The GUI handler */
public class LCGuiHandler implements IGuiHandler {

	// list of GUI / container IDs
	public static final int lightningCellGui = 0;
	public static final int lightningFurnaceGui = 1;
	public static final int lightningCrusherGui = 2;
	public static final int lightningInfuserGui = 3;
	public static final int staticGeneratorGui = 4;
	public static final int enchReallocatorGui = 5;
	public static final int lightningRXGui = 6;
	public static final int lightningCannonGui = 7;
	
	public static final int lightningLEtoRFGui = 100;
	public static final int lightningRFtoLEGui = 101;

	// Get the serverside container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case lightningCellGui: // Lightning Cell
			TileEntityLightningCell tileCell = (TileEntityLightningCell) world.getTileEntity(pos);
			return new ContainerLightningCell(player.inventory, tileCell);
		case lightningFurnaceGui: // Lightning Furnace
			TileEntityLightningFurnace tileFurnace = (TileEntityLightningFurnace) world.getTileEntity(pos);
			return new ContainerLightningFurnace(player.inventory, tileFurnace);
		case lightningCrusherGui: // Lightning Crusher
			TileEntityLightningCrusher tileCrusher = (TileEntityLightningCrusher) world.getTileEntity(pos);
			return new ContainerLightningCrusher(player.inventory, tileCrusher);
		case lightningInfuserGui: // Lightning Infusion Table
			TileEntityLightningInfuser tileInfuser = (TileEntityLightningInfuser) world.getTileEntity(pos);
			return new ContainerLightningInfuser(player.inventory, tileInfuser);
		case staticGeneratorGui: // Static Generator
			TileEntityStaticGenerator tileGenerator = (TileEntityStaticGenerator) world.getTileEntity(pos);
			return new ContainerStaticGenerator(player.inventory, tileGenerator);
		case enchReallocatorGui: // Enchantment Reallocator
			TileEntityEnchReallocator tileRealloc = (TileEntityEnchReallocator) world.getTileEntity(pos);
			return new ContainerEnchReallocator(player.inventory, tileRealloc);
		case lightningRXGui: // Lightning Receiver
			TileEntityLightningReceiver tileRX = (TileEntityLightningReceiver) world.getTileEntity(pos);
			return new ContainerLightningReceiver(player.inventory, tileRX);
		case lightningCannonGui: // Lightning Cannon
			TileEntityLightningCannon tileCannon = (TileEntityLightningCannon) world.getTileEntity(pos);
			return new ContainerLightningCannon(player.inventory, tileCannon);
		case lightningLEtoRFGui: // RF Provider
			TileEntityRFProvider tileLERF = (TileEntityRFProvider) world.getTileEntity(pos);
			return new ContainerRFProvider(player.inventory, tileLERF);
		case lightningRFtoLEGui: // RF Receiver
			TileEntityRFReceiver tileRFLE = (TileEntityRFReceiver) world.getTileEntity(pos);
			return new ContainerRFReceiver(player.inventory, tileRFLE);
		default:
			return null;
		}
	}

	// Get the clientside GUI
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case lightningCellGui: // Lightning Cell
			TileEntityLightningCell tileCell = (TileEntityLightningCell) world.getTileEntity(pos);
			return new GuiLightningCell(player.inventory, tileCell);
		case lightningFurnaceGui: // Lightning Furnace
			TileEntityLightningFurnace tileFurnace = (TileEntityLightningFurnace) world.getTileEntity(pos);
			return new GuiLightningFurnace(player.inventory, tileFurnace);
		case lightningCrusherGui: // Lightning Crusher
			TileEntityLightningCrusher tileCrusher = (TileEntityLightningCrusher) world.getTileEntity(pos);
			return new GuiLightningCrusher(player.inventory, tileCrusher);
		case lightningInfuserGui: // Lightning Infusion Table
			TileEntityLightningInfuser tileInfuser = (TileEntityLightningInfuser) world.getTileEntity(pos);
			return new GuiLightningInfuser(player.inventory, tileInfuser);
		case staticGeneratorGui: // Static Generator
			TileEntityStaticGenerator tileGenerator = (TileEntityStaticGenerator) world.getTileEntity(pos);
			return new GuiStaticGenerator(player.inventory, tileGenerator);
		case enchReallocatorGui: // Enchantment Reallocator
			TileEntityEnchReallocator tileRealloc = (TileEntityEnchReallocator) world.getTileEntity(pos);
			return new GuiEnchReallocator(player.inventory, tileRealloc);
		case lightningRXGui: // Lightning Receiver
			TileEntityLightningReceiver tileRX = (TileEntityLightningReceiver) world.getTileEntity(pos);
			return new GuiLightningReceiver(player.inventory, tileRX);
		case lightningCannonGui: // Lightning Cannon
			TileEntityLightningCannon tileCannon = (TileEntityLightningCannon) world.getTileEntity(pos);
			return new GuiLightningCannon(player.inventory, tileCannon);
		case lightningLEtoRFGui: // RF Provider
			TileEntityRFProvider tileLERF = (TileEntityRFProvider) world.getTileEntity(pos);
			return new GuiRFProvider(player.inventory, tileLERF);
		case lightningRFtoLEGui: // RF Receiver
			TileEntityRFReceiver tileRFLE = (TileEntityRFReceiver) world.getTileEntity(pos);
			return new GuiRFReceiver(player.inventory, tileRFLE);
		default:
			return null;
		}
	}

}

