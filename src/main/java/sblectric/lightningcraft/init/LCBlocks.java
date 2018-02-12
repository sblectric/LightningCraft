package sblectric.lightningcraft.init;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftBlock;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.blocks.BlockAirTerminal;
import sblectric.lightningcraft.blocks.BlockChargingPlate;
import sblectric.lightningcraft.blocks.BlockDoubleSlabLC;
import sblectric.lightningcraft.blocks.BlockEnchReallocator;
import sblectric.lightningcraft.blocks.BlockLight;
import sblectric.lightningcraft.blocks.BlockLightningBreaker;
import sblectric.lightningcraft.blocks.BlockLightningCannon;
import sblectric.lightningcraft.blocks.BlockLightningCell;
import sblectric.lightningcraft.blocks.BlockLightningCrusher;
import sblectric.lightningcraft.blocks.BlockLightningFurnace;
import sblectric.lightningcraft.blocks.BlockLightningInfuser;
import sblectric.lightningcraft.blocks.BlockLightningMiner;
import sblectric.lightningcraft.blocks.BlockLogLC;
import sblectric.lightningcraft.blocks.BlockMetal;
import sblectric.lightningcraft.blocks.BlockPlankLC;
import sblectric.lightningcraft.blocks.BlockRFProvider;
import sblectric.lightningcraft.blocks.BlockRFReceiver;
import sblectric.lightningcraft.blocks.BlockSlabLC;
import sblectric.lightningcraft.blocks.BlockStairsLC;
import sblectric.lightningcraft.blocks.BlockStaticGenerator;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.BlockUnderOre;
import sblectric.lightningcraft.blocks.BlockUnderSand;
import sblectric.lightningcraft.blocks.BlockUnderTNT;
import sblectric.lightningcraft.blocks.BlockWallLC;
import sblectric.lightningcraft.blocks.BlockWeakStone;
import sblectric.lightningcraft.blocks.BlockWireless;
import sblectric.lightningcraft.blocks.PortalUnderworld;
import sblectric.lightningcraft.blocks.base.BlockLC;
import sblectric.lightningcraft.registry.RegistryHelper;
import sblectric.lightningcraft.render.BlockColoring;

/** The block class. */
public class LCBlocks {
	
	/** The list of blocks to help with registration */
	private static JointList<ILightningCraftBlock> blocks;
	
	/** The main block registry */
	public static void mainRegistry() {
		blocks = new JointList();
		addBlocks();
		registerBlocks();
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
	public static BlockLC lightningBreaker;
	public static BlockLC lightningMiner;
	public static BlockLC lightningConduit;
	public static BlockLC staticGenerator;
	public static BlockLC wirelessBlock;
	public static BlockChargingPlate chargingPlate;
	public static BlockLC enchReallocator;
	public static PortalUnderworld underPortal;
	public static BlockUnderSand underSand;
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
			metalBlock = (BlockLC)new BlockMetal().setRegistryName("metal_block").setCreativeTab(LCCreativeTabs.blocks),
			stoneBlock = (BlockLC)new BlockStone().setRegistryName("stone_block").setCreativeTab(LCCreativeTabs.blocks),
			woodLog = (BlockLogLC)new BlockLogLC().setRegistryName("wood_log").setCreativeTab(LCCreativeTabs.blocks),
			woodPlank = (BlockLC)new BlockPlankLC().setRegistryName("wood_plank").setCreativeTab(LCCreativeTabs.blocks),
			slabBlock = (BlockSlabLC)new BlockSlabLC().setRegistryName("slab_block").setCreativeTab(LCCreativeTabs.blocks),
			slabBlockDouble = (BlockSlabLC)new BlockDoubleSlabLC().setRegistryName("slab_block_double"), // no creative tab for the double slab
			thunderStairs = (BlockStairsLC)new BlockStairsLC().setRegistryName("thunder_stairs").setCreativeTab(LCCreativeTabs.blocks),
			demonStairs = (BlockStairsLC)new BlockStairsLC().setRegistryName("demon_stairs").setCreativeTab(LCCreativeTabs.blocks),
			underStairs = (BlockStairsLC)new BlockStairsLC().setRegistryName("under_stairs").setCreativeTab(LCCreativeTabs.blocks),
			underPlankStairs = (BlockStairsLC)new BlockStairsLC(woodPlank).setRegistryName("under_plank_stairs").setCreativeTab(LCCreativeTabs.blocks),
			airTerminal = (BlockLC)new BlockAirTerminal().setRegistryName("air_terminal").setCreativeTab(LCCreativeTabs.blocks),
			lightningCell = (BlockLC)new BlockLightningCell().setRegistryName("lightning_cell").setCreativeTab(LCCreativeTabs.blocks),
			lightningFurnace = (BlockLC)new BlockLightningFurnace().setRegistryName("lightning_furnace").setCreativeTab(LCCreativeTabs.blocks),
			lightningCrusher = (BlockLC)new BlockLightningCrusher().setRegistryName("lightning_crusher").setCreativeTab(LCCreativeTabs.blocks),
			lightningInfuser = (BlockLC)new BlockLightningInfuser().setRegistryName("lightning_infuser").setCreativeTab(LCCreativeTabs.blocks),
			lightningBreaker = (BlockLC)new BlockLightningBreaker().setRegistryName("lightning_breaker").setCreativeTab(LCCreativeTabs.blocks),
			lightningMiner = (BlockLC)new BlockLightningMiner().setRegistryName("lightning_miner").setCreativeTab(LCCreativeTabs.blocks),
			staticGenerator = (BlockLC)new BlockStaticGenerator().setRegistryName("static_generator").setCreativeTab(LCCreativeTabs.blocks),
			wirelessBlock = (BlockLC)new BlockWireless().setRegistryName("wireless_block").setCreativeTab(LCCreativeTabs.blocks),
			chargingPlate = (BlockChargingPlate)new BlockChargingPlate().setRegistryName("charging_plate").setCreativeTab(LCCreativeTabs.blocks),
			enchReallocator = (BlockLC)new BlockEnchReallocator().setRegistryName("ench_reallocator").setCreativeTab(LCCreativeTabs.blocks),
			underPortal = (PortalUnderworld)new PortalUnderworld().setRegistryName("under_portal"), // no creative tab for the portal
			underSand = (BlockUnderSand)new BlockUnderSand().setRegistryName("under_sand").setCreativeTab(LCCreativeTabs.blocks),
			corruptStone = (BlockLC)new BlockWeakStone().setRegistryName("corrupt_stone").setCreativeTab(LCCreativeTabs.blocks),
			wallBlock = (BlockWallLC)new BlockWallLC().setRegistryName("wall_block").setCreativeTab(LCCreativeTabs.blocks),
			lightBlock = (BlockLC)new BlockLight().setRegistryName("light_block").setCreativeTab(LCCreativeTabs.blocks),
			oreBlock = (BlockLC)new BlockUnderOre().setRegistryName("ore_block").setCreativeTab(LCCreativeTabs.blocks),
			underTNT = (BlockUnderTNT)new BlockUnderTNT().setRegistryName("under_tnt").setCreativeTab(LCCreativeTabs.blocks),
			lightningCannon = (BlockLC)new BlockLightningCannon().setRegistryName("lightning_cannon").setCreativeTab(LCCreativeTabs.blocks),
			rfProvider = (BlockLC)new BlockRFProvider().setRegistryName("rf_provider").setCreativeTab(LCCreativeTabs.blocks),
			rfReceiver = (BlockLC)new BlockRFReceiver().setRegistryName("rf_receiver").setCreativeTab(LCCreativeTabs.blocks)
		);
	}
	
	/** Register the blocks */
	private static void registerBlocks() {
		RegistryHelper.registerBlocks(blocks);
	}
	
	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		for(ILightningCraftBlock block : blocks) {
			if(block.getItemClass() != null) block.registerRender();
		}
	}
	
	/** Register the block coloring */
	@SideOnly(Side.CLIENT)
	public static void registerBlockColors() {
		for(ILightningCraftBlock block : blocks) BlockColoring.registerBlock(block);
	}

}
