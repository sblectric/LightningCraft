package com.lightningcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lightningcraft.creativetabs.LCCreativeTabs;
import com.lightningcraft.registry.IRegistryBlock;
import com.lightningcraft.render.BlockColoring;
import com.lightningcraft.tiles.TileEntityChargingPlate;
import com.lightningcraft.tiles.TileEntityEnchReallocator;
import com.lightningcraft.tiles.TileEntityLightningCannon;
import com.lightningcraft.tiles.TileEntityLightningCell;
import com.lightningcraft.tiles.TileEntityLightningCrusher;
import com.lightningcraft.tiles.TileEntityLightningFurnace;
import com.lightningcraft.tiles.TileEntityLightningInfuser;
import com.lightningcraft.tiles.TileEntityLightningReceiver;
import com.lightningcraft.tiles.TileEntityLightningTransmitter;
import com.lightningcraft.tiles.TileEntityRFProvider;
import com.lightningcraft.tiles.TileEntityRFReceiver;
import com.lightningcraft.tiles.TileEntityStaticGenerator;
import com.lightningcraft.util.JointList;

/** The block class. */
public class LCBlocks {
	
	/** The list of blocks to help with registration */
	private static JointList<IRegistryBlock> blocks;
	
	/** The main block registry */
	public static void mainRegistry() {
		blocks = new JointList();
		addBlocks();
		registerBlocks();
		registerTileEntities();
	}
	
	// the blocks
	public static BlockLC metalBlock;
	public static BlockLC stoneBlock;
	public static BlockLogLC woodLog;
	public static BlockLC woodPlank;
	public static BlockSlabLC slabBlock;
	public static BlockSlabLC slabBlockDouble;
	public static BlockStairsLC thunderStairs;
	public static BlockStairsLC demonStairs;
	public static BlockStairsLC underStairs;
	public static BlockStairsLC underPlankStairs;
	public static BlockLC airTerminal;
	public static BlockLC lightningCell;
	public static BlockLC lightningFurnace;
	public static BlockLC lightningCrusher;
	public static BlockLC lightningInfuser;
	public static BlockLC staticGenerator;
	public static BlockLC wirelessBlock;
	public static BlockChargingPlate chargingPlate;
	public static BlockLC enchReallocator;
	public static PortalUnderworld underPortal;
	public static BlockLC corruptStone;
	public static BlockWallLC wallBlock;
	public static BlockLC lightBlock;
	public static BlockLC oreBlock;
	public static BlockUnderTNT underTNT;
	public static BlockLC lightningCannon;
	public static BlockLC rfProvider;
	public static BlockLC rfReceiver;
	
	/** Add the blocks */
	private static void addBlocks() {
		blocks.join(
			metalBlock = (BlockLC)new BlockMetal().setUnlocalizedName("metalBlock").setCreativeTab(LCCreativeTabs.blocks),
			stoneBlock = (BlockLC)new BlockStone().setUnlocalizedName("stoneBlock").setCreativeTab(LCCreativeTabs.blocks),
			woodLog = (BlockLogLC)new BlockLogLC().setUnlocalizedName("woodLog").setCreativeTab(LCCreativeTabs.blocks),
			woodPlank = (BlockLC)new BlockPlankLC().setUnlocalizedName("woodPlank").setCreativeTab(LCCreativeTabs.blocks),
			slabBlock = (BlockSlabLC)new BlockSlabLC().setUnlocalizedName("slabBlock").setCreativeTab(LCCreativeTabs.blocks),
			slabBlockDouble = (BlockSlabLC)new BlockDoubleSlabLC().setUnlocalizedName("slabBlockDouble"), // no creative tab for the double slab
			thunderStairs = (BlockStairsLC)new BlockStairsLC().setUnlocalizedName("thunderStairs").setCreativeTab(LCCreativeTabs.blocks),
			demonStairs = (BlockStairsLC)new BlockStairsLC().setUnlocalizedName("demonStairs").setCreativeTab(LCCreativeTabs.blocks),
			underStairs = (BlockStairsLC)new BlockStairsLC().setUnlocalizedName("underStairs").setCreativeTab(LCCreativeTabs.blocks),
			underPlankStairs = (BlockStairsLC)new BlockStairsLC(woodPlank).setUnlocalizedName("underPlankStairs").setCreativeTab(LCCreativeTabs.blocks),
			airTerminal = (BlockLC)new BlockAirTerminal().setUnlocalizedName("airTerminal").setCreativeTab(LCCreativeTabs.blocks),
			lightningCell = (BlockLC)new BlockLightningCell().setUnlocalizedName("lightningCell").setCreativeTab(LCCreativeTabs.blocks),
			lightningFurnace = (BlockLC)new BlockLightningFurnace().setUnlocalizedName("lightningFurnace").setCreativeTab(LCCreativeTabs.blocks),
			lightningCrusher = (BlockLC)new BlockLightningCrusher().setUnlocalizedName("lightningCrusher").setCreativeTab(LCCreativeTabs.blocks),
			lightningInfuser = (BlockLC)new BlockLightningInfuser().setUnlocalizedName("lightningInfuser").setCreativeTab(LCCreativeTabs.blocks),
			staticGenerator = (BlockLC)new BlockStaticGenerator().setUnlocalizedName("staticGenerator").setCreativeTab(LCCreativeTabs.blocks),
			wirelessBlock = (BlockLC)new BlockWireless().setUnlocalizedName("wirelessBlock").setCreativeTab(LCCreativeTabs.blocks),
			chargingPlate = (BlockChargingPlate)new BlockChargingPlate().setUnlocalizedName("chargingPlate").setCreativeTab(LCCreativeTabs.blocks),
			enchReallocator = (BlockLC)new BlockEnchReallocator().setUnlocalizedName("enchReallocator").setCreativeTab(LCCreativeTabs.blocks),
			underPortal = (PortalUnderworld)new PortalUnderworld().setUnlocalizedName("underPortal"), // no creative tab for the portal
			corruptStone = (BlockLC)new BlockWeakStone().setUnlocalizedName("corruptStone").setCreativeTab(LCCreativeTabs.blocks),
			wallBlock = (BlockWallLC)new BlockWallLC().setUnlocalizedName("wallBlock").setCreativeTab(LCCreativeTabs.blocks),
			lightBlock = (BlockLC)new BlockLight().setUnlocalizedName("lightBlock").setCreativeTab(LCCreativeTabs.blocks),
			oreBlock = (BlockLC)new BlockUnderOre().setUnlocalizedName("oreBlock").setCreativeTab(LCCreativeTabs.blocks),
			underTNT = (BlockUnderTNT)new BlockUnderTNT().setUnlocalizedName("underTNT").setCreativeTab(LCCreativeTabs.blocks),
			lightningCannon = (BlockLC)new BlockLightningCannon().setUnlocalizedName("lightningCannon").setCreativeTab(LCCreativeTabs.blocks),
			rfProvider = (BlockLC)new BlockRFProvider().setUnlocalizedName("rfProvider").setCreativeTab(LCCreativeTabs.blocks),
			rfReceiver = (BlockLC)new BlockRFReceiver().setUnlocalizedName("rfReceiver").setCreativeTab(LCCreativeTabs.blocks)
		);
	}
	
	/** Register the blocks */
	private static void registerBlocks() {
		// iterate through them
		for(IRegistryBlock block : blocks) {
			GameRegistry.registerBlock((Block)block, block.getItemClass(), block.getShorthandName(), block.getItemClassArgs());
		}
	}
	
	/** Register the tile entities */
	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityLightningCell.class, "TileEntityLightningCell");
		GameRegistry.registerTileEntity(TileEntityLightningFurnace.class, "TileEntityLightningFurnace");
		GameRegistry.registerTileEntity(TileEntityLightningCrusher.class, "TileEntityLightningCrusher");
		GameRegistry.registerTileEntity(TileEntityLightningInfuser.class, "TileEntityLightningInfuser");
		GameRegistry.registerTileEntity(TileEntityStaticGenerator.class, "TileEntityStaticGenerator");
		GameRegistry.registerTileEntity(TileEntityChargingPlate.class, "TileEntityChargingPlate");
		GameRegistry.registerTileEntity(TileEntityEnchReallocator.class, "TileEntityEnchReallocator");
		GameRegistry.registerTileEntity(TileEntityLightningCannon.class, "TileEntityLightningCannon");
		GameRegistry.registerTileEntity(TileEntityLightningTransmitter.class, "TileEntityLightningTransmitter");
		GameRegistry.registerTileEntity(TileEntityLightningReceiver.class, "TileEntityLightningReceiver");
		GameRegistry.registerTileEntity(TileEntityRFProvider.class, "TileEntityLERFProvider");
		GameRegistry.registerTileEntity(TileEntityRFReceiver.class, "TileEntityLERFReceiver");
	}
	
	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	    
		// iterate through them
		for(IRegistryBlock block : blocks) {
			block.registerRender(mesher);
			BlockColoring.registerBlock((Block)block);
		}
	}

}
