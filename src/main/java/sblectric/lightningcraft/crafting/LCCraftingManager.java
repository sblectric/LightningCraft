package sblectric.lightningcraft.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import sblectric.lightningcraft.blocks.BlockLight;
import sblectric.lightningcraft.blocks.BlockSlabLC;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.blocks.BlockUnderOre;
import sblectric.lightningcraft.blocks.LCBlocks;
import sblectric.lightningcraft.integration.cofh.CoFH;
import sblectric.lightningcraft.items.LCItems;
import sblectric.lightningcraft.recipes.LCFuelHandler;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.ref.Metal.Dust;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.Metal.MBlock;
import sblectric.lightningcraft.ref.Metal.Nugget;
import sblectric.lightningcraft.ref.Metal.Plate;
import sblectric.lightningcraft.ref.Metal.Rod;

/** Oredict, crafting, and smelting recipes */
public class LCCraftingManager {
	
	/** The main registry for crafting recipes and such */
	public static void mainRegistry() {
		setOreDictionary();
		addCraftingRecipes();
		addSmeltingRecipes();
		GameRegistry.registerFuelHandler(new LCFuelHandler());
	}
	
	/** Add Ore Dictionary entries */
	private static void setOreDictionary() {
		// register this mod's items
		
		// register blocks
		for(int meta = 0; meta < Ingot.count; meta++) {
			OreDictionary.registerOre(MBlock.getBlockFromMeta(meta), new ItemStack(LCBlocks.metalBlock, 1, meta));
		}
		
		// register ingots
		for(int meta = 0; meta < Ingot.count; meta++) {
			OreDictionary.registerOre(Ingot.getIngotFromMeta(meta), new ItemStack(LCItems.ingot, 1, meta));
		}
		
		// register nuggets
		for(int meta = 0; meta < Ingot.count; meta++) {
			OreDictionary.registerOre(Nugget.getNuggetFromMeta(meta), new ItemStack(LCItems.nugget, 1, meta));
		}
		
		// register dusts
		for(int meta = 0; meta < Ingot.count; meta++) {
			OreDictionary.registerOre(Dust.getDustFromMeta(meta), new ItemStack(LCItems.dust, 1, meta));
		}
		
		// register rods
		for(int meta = 0; meta < Rod.count; meta++) {
			OreDictionary.registerOre(Rod.getRodFromMeta(meta), new ItemStack(LCItems.rod, 1, meta));
		}
		
		// register plates
		for(int meta = 0; meta < Rod.count; meta++) {
			OreDictionary.registerOre(Plate.getPlateFromMeta(meta), new ItemStack(LCItems.plate, 1, meta));
		}
		
		// wood
		OreDictionary.registerOre("logWood", new ItemStack(LCBlocks.woodLog));
		OreDictionary.registerOre("plankWood", new ItemStack(LCBlocks.woodPlank));
		OreDictionary.registerOre("slabWood", new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.UNDER_PLANK));
		OreDictionary.registerOre("stairWood", new ItemStack(LCBlocks.underPlankStairs));
		
		// ores
		OreDictionary.registerOre("oreIron", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.IRON));
		OreDictionary.registerOre("oreGold", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.GOLD));
		OreDictionary.registerOre("oreDiamond", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.DIAMOND));
		OreDictionary.registerOre("oreEmerald", new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.EMERALD));
		
		// other materials
		OreDictionary.registerOre("nuggetNetherStar", new ItemStack(LCItems.material, 1, Material.NETHER_NUGGET));
		OreDictionary.registerOre("dustDiamond", new ItemStack(LCItems.material, 1, Material.DIAMOND_DUST));
		OreDictionary.registerOre("dustEmerald", new ItemStack(LCItems.material, 1, Material.EMERALD_DUST));
		OreDictionary.registerOre("dustQuartz", new ItemStack(LCItems.material, 1, Material.QUARTZ_DUST));
	}
	
	/** Add crafting recipes */
	private static void addCraftingRecipes() {
		
		// block <-> ingot recipes
		for(int meta = 0; meta < Ingot.count; meta++) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.metalBlock, 1, meta), "XXX","XXX","XXX", 'X',Ingot.getIngotFromMeta(meta));
			RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.ingot, 9, meta), new ItemStack(LCBlocks.metalBlock, 1, meta));
		}
		
		// ingot <-> nugget recipes
		for(int meta = 0; meta < Ingot.count; meta++) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.ingot, 1, meta), "XXX","XXX","XXX", 'X',Nugget.getNuggetFromMeta(meta));
			RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.nugget, 9, meta), new ItemStack(LCItems.ingot, 1, meta));
		}
		
		// rod recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.rod, 1, meta), "  X"," X ","X  ", 'X',Rod.getIngotFromMeta(meta));
		}
		
		// plate recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.plate, 3, meta), "XXX", 'X',Rod.getIngotFromMeta(meta));
		}
		
		// air terminal recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.airTerminal, 1, meta), " I "," X ","PIP", 
					'I',Rod.getIngotFromMeta(meta), 'X',Rod.getRodFromMeta(meta), 'P',Plate.getPlateFromMeta(meta));
		}
		
		// the guide
		RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.guide), Items.BOOK, new ItemStack(LCItems.rod, 1, OreDictionary.WILDCARD_VALUE));
		
		// golf clubs
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.golfClub), " II"," X ","I  ", 'X',"rodIron", 'I',"ingotIron");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.golfClubGold), " II"," X ","I  ", 'X',"rodGold", 'I',"ingotGold");
		
		// nether nugget
		RecipeHelper.addShapedOreRecipe(new ItemStack(Items.NETHER_STAR), "XXX","XXX","XXX", 'X',"nuggetNetherStar");
		RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.material, 9, Material.NETHER_NUGGET), Items.NETHER_STAR);
		
		// materials
		RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.material, 3, Material.UNDER_MEAL), new ItemStack(LCItems.material, 1, Material.UNDER_BONE));
		RecipeHelper.addShapelessOreRecipe(new ItemStack(Items.DYE, 6, 1), new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD));
		RecipeHelper.addShapelessOreRecipe(new ItemStack(Items.DYE, 3, 6), new ItemStack(LCItems.material, 1, Material.UNDER_MEAL));
		
		// thunderstone
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER), "QXQ","XOX","QXQ", 
				'X',"cobblestone", 'O',Blocks.OBSIDIAN, 'Q',"gemQuartz");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.THUNDER));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.THUNDER), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.thunderStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK));
		
		// demonstone
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.DEMON_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.DEMON));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.DEMON), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.demonStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK));
		
		// understone
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.UNDER_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.UNDER));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.UNDER), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.underStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK));
		
		// lantern to lamp (decorative)
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP), "RBR","BLB","RBR",
				'R',Blocks.GLOWSTONE, 'B',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), 
				'L',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP_FANCY), "AB","BA",
				'A',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER), 'B',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER_LAMP));
		
		// corrupt walls
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wallBlock, 6), "BBB","BBB", 'B',LCBlocks.corruptStone);
		
		// wood
		RecipeHelper.addShapelessOreRecipe(new ItemStack(LCBlocks.woodPlank, 4), LCBlocks.woodLog);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.UNDER_PLANK), "XXX", 'X',LCBlocks.woodPlank);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.underPlankStairs, 4), "X  ","XX ","XXX", 'X',LCBlocks.woodPlank);
		
		// tool recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecHammer), "PPP","XIX"," I ", 'X',"ingotElectricium", 'I',"rodElectricium", 'P',"plateElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecSword), " X ","PXP"," I ", 'X',"ingotElectricium", 'I',"rodElectricium", 'P',"plateElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecPick), "XXX"," I "," I ", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecAxe), "XX","XI"," I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecShovel), "X","I","I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecHoe), "XX"," I"," I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyHammer), "PPP","XIX"," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather", 'P',"plateSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skySword), " X ","PXP"," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather", 'P',"plateSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyPick), "XXX"," I "," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyAxe), "XX","XI"," I", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyShovel), "X","I","I", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyHoe), "XX"," I"," I", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		
		// armor recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecHelm), "XXX","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecChest), "X X","XXX","XXX", 'X',"ingotElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecLegs), "XXX","X X","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.elecBoots), "X X","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyHelm), "XXX","X X", 'X',"ingotSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyChest), "X X","XXX","XXX", 'X',"ingotSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyLegs), "XXX","X X","X X", 'X',"ingotSkyfather");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.skyBoots), "X X","X X", 'X',"ingotSkyfather");
		
		// kinetic tools
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticSword), "RCR","RAR","RER",
				'A',Items.GOLDEN_SWORD, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticPick), "RCR","RAR","RER",
				'A',Items.GOLDEN_PICKAXE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticAxe), "RCR","RAR","RER",
				'A',Items.GOLDEN_AXE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticShovel), "RCR","RAR","RER",
				'A',Items.GOLDEN_SHOVEL, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		
		// kinetic armor
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticHelm), "RCR","RAR","RER", 
				'A',Items.GOLDEN_HELMET, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticChest), "RCR","RAR","RER", 
				'A',Items.GOLDEN_CHESTPLATE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticLegs), "RCR","RAR","RER", 
				'A',Items.GOLDEN_LEGGINGS, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.kineticBoots), "RCR","RAR","RER", 
				'A',Items.GOLDEN_BOOTS,  'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		
		// cell recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC), "XAX","IBI","XAX",
				'B',"plateGold", 'X',"rodIron", 'I',"plateElectricium", 'A',"dustRedstone");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.SKY), "XAX","IBI","XAX",
				'B',new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC), 'X',"rodGold", 'I',"plateSkyfather", 'A',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.MYSTIC), "XAX","IBI","XAX",
				'B',new ItemStack(LCBlocks.lightningCell, 1, Ingot.SKY), 'X',"rodSkyfather", 'I',"plateMystic", 'A',"plateElectricium");
		
		// cell upgrade
		ItemStack cellUpgrade = new ItemStack(LCItems.material, 1, Material.CELL_UPGRADE);
		RecipeHelper.addShapedOreRecipe(cellUpgrade, " R ","RXR"," R ", 'X',"plateSkyfather", 'R',"dustRedstone");
		
		// other machine recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningFurnace), "XCX","XAX","XIX",
				'X',"cobblestone", 'I',"plateIron", 'A',Blocks.FURNACE, 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCrusher), "ZCZ","XAX","ZIZ",
				'Z',Blocks.IRON_BARS, 'X',Blocks.OBSIDIAN, 'I',"plateElectricium", 'A',LCBlocks.lightningFurnace, 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningInfuser), "XCX","XAX","XIX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER), 'I',"plateGold", 'A',"rodElectricium", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.staticGenerator), "XCX","COC","XCX", 
				'C',Items.COMPARATOR, 'X',Blocks.QUARTZ_BLOCK, 'O',new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.enchReallocator), "TCT","XAX","XEX",
				'A',new ItemStack(LCItems.material, 1, Material.ENSORCELLED), 'E',Blocks.ENCHANTING_TABLE, 
				'T',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), 'C',Items.COMPARATOR, 
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK_CHISELED));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCannon, 1, 1), "ERE","ICI","ETE", 
				'C',new ItemStack(LCItems.material, 1, Material.CANNON_CORE), 'I',"rodIron", 'E',"rodElectricium", 'T',Blocks.TNT, 'R',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.lightningCannon, 1, 2), "EQE","ICI","EDE", 
				'C',new ItemStack(LCBlocks.lightningCannon, 1, 1), 'I',"rodMystic", 'E',"rodSkyfather", 'Q',cellUpgrade, 'D',"dustMystic");
		
		// wireless recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 0), " T ","XOX","XCX", 'C',"dustDiamond",
				'X',"plateIron", 'O',cellUpgrade, 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.ELEC));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 1), " T ","XOX","XCX", 'C',Items.COMPARATOR,
				'X',"rodIron", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 0), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.SKY));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 2), " T ","XOX","XCX", 'C',"plateElectricium",
				'X',"blockIron", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 1), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.MYSTIC));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 3), " T ","XOX","XCX", 'C',"dustRedstone",
				'X',"plateGold", 'O',cellUpgrade, 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.ELEC));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 4), " T ","XOX","XCX", 'C',Items.COMPARATOR,
				'X',"rodGold", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 3), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.SKY));
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 5), " T ","XOX","XCX", 'C',"blockRedstone",
				'X',"blockGold", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 4), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.MYSTIC));
		
		// RF machines
		if(CoFH.apiLoaded) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.rfProvider), "BRB","CAC","BCB",
					'B',"plateElectricium", 'A',"blockIron", 'R',Items.COMPARATOR, 'C',"plateIron");
			RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.rfReceiver), "BRB","CAC","BCB",
					'B',"blockRedstone", 'A',"blockGold", 'R',Items.COMPARATOR, 'C',"plateIron");
		}
		
		// wireless markers
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.wirelessMarker, 1), " C ","EPE"," U ", 
				'P',Items.PAPER, 'E',"dustElectricium", 'U',cellUpgrade, 'C',Items.COMPARATOR);
		RecipeHelper.addShapelessOreRecipe(new ItemStack(LCItems.wirelessMarker, 1, 0), new ItemStack(LCItems.wirelessMarker, 1, 1));
		
		// battery recipes
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.battery, 1, 0), "ICI","IBI","IRI", 
				'B',"plateElectricium", 'I',"rodIron", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.battery, 1, 1), "RUR","RBR","RIR", 
				'B',new ItemStack(LCItems.battery, 1, 0), 'U',cellUpgrade, 'R',"dustRedstone", 'I',"rodElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.battery, 1, 2), "RUR","RBR","RIR", 
				'B',new ItemStack(LCItems.battery, 1, 1), 'U',"ingotSkyfather", 'R',"blockRedstone", 'I',"blockElectricium");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCBlocks.chargingPlate), "RCR","RPR","RER", 'E',"plateElectricium", 
				'P',Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'R',"dustRedstone", 'C',Items.COMPARATOR);
		
		// magnets
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.itemMagnet, 1, 0), "INN","  N","INN", 'I',"plateIron", 'N',"blockRedstone");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.itemMagnet, 1, 1), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 0), 'E',"ingotIron");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.itemMagnet, 1, 2), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 1), 'E',"ingotGold");
		RecipeHelper.addShapedOreRecipe(new ItemStack(LCItems.itemMagnet, 1, 3), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 2), 'E',"ingotElectricium");
		
	}
	
	/** Add smelting recipes */
	private static void addSmeltingRecipes() {
		// dust -> ingots
		for(int meta = 0; meta < Ingot.count; meta++) {
			GameRegistry.addSmelting(new ItemStack(LCItems.dust, 1, meta), new ItemStack(LCItems.ingot, 1, meta), 1.0F);
		}
		
		// wood
		GameRegistry.addSmelting(new ItemStack(LCBlocks.woodLog, 1, 0), new ItemStack(Items.COAL, 1, 1), 1.0F);
		
		// ores
		GameRegistry.addSmelting(new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.IRON), new ItemStack(Items.IRON_INGOT), 1.0F);
		GameRegistry.addSmelting(new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.GOLD), new ItemStack(Items.GOLD_INGOT), 1.0F);
		GameRegistry.addSmelting(new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.DIAMOND), new ItemStack(Items.DIAMOND, 2), 1.0F);
		GameRegistry.addSmelting(new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.EMERALD), new ItemStack(Items.EMERALD, 2), 1.0F);
		
		// other materials
		GameRegistry.addSmelting(new ItemStack(LCItems.material, 1, Material.DIAMOND_DUST), new ItemStack(Items.DIAMOND), 1.0F);
		GameRegistry.addSmelting(new ItemStack(LCItems.material, 1, Material.EMERALD_DUST), new ItemStack(Items.EMERALD), 1.0F);
		GameRegistry.addSmelting(new ItemStack(LCItems.material, 1, Material.QUARTZ_DUST), new ItemStack(Items.QUARTZ), 1.0F);
	}

}
