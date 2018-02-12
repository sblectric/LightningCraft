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
import sblectric.lightningcraft.blocks.BlockUnderTNT;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.init.LCItems;
import sblectric.lightningcraft.integration.energy.EnergyApiHelper;
import sblectric.lightningcraft.recipes.LCFuelHandler;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.ref.Metal;
import sblectric.lightningcraft.ref.Metal.Dust;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.Metal.MBlock;
import sblectric.lightningcraft.ref.Metal.Nugget;
import sblectric.lightningcraft.ref.Metal.Plate;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.ref.RefStrings;

/** Oredict, crafting, and smelting recipes */
public class LCCraftingManager {

	/** Add Ore Dictionary entries */
	public static void setOreDictionary() {
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
	public static void addCraftingRecipes() {

		// block <-> ingot recipes
		for(int meta = 0; meta < Ingot.count; meta++) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.metalBlock, 1, meta), "XXX","XXX","XXX", 'X',Ingot.getIngotFromMeta(meta));
			RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.ingot, 9, meta), new ItemStack(LCBlocks.metalBlock, 1, meta));
		}

		// ingot <-> nugget recipes
		for(int meta = 0; meta < Ingot.count; meta++) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCItems.ingot, 1, meta), "XXX","XXX","XXX", 'X',Nugget.getNuggetFromMeta(meta));
			RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.nugget, 9, meta), new ItemStack(LCItems.ingot, 1, meta));
		}

		// rod recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCItems.rod, 1, meta), "  X"," X ","X  ", 'X',Rod.getIngotFromMeta(meta));
		}

		// plate recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCItems.plate, 3, meta), "XXX", 'X',Rod.getIngotFromMeta(meta));
		}

		// air terminal recipes
		for(int meta = 0; meta < Rod.count; meta++) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.airTerminal, 1, meta), " I "," X ","PIP", 
					'I',Rod.getIngotFromMeta(meta), 'X',Rod.getRodFromMeta(meta), 'P',Plate.getPlateFromMeta(meta));
		}

		// golf clubs
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.golfClub), " II"," X ","I  ", 'X',"rodIron", 'I',"ingotIron");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.golfClubGold), " II"," X ","I  ", 'X',"rodGold", 'I',"ingotGold");

		// nether nugget
		RecipeHelper.addShapedRecipe(new ItemStack(Items.NETHER_STAR), "XXX","XXX","XXX", 'X',"nuggetNetherStar");
		RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.material, 9, Material.NETHER_NUGGET), Items.NETHER_STAR);

		// materials
		RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.material, 3, Material.UNDER_MEAL), new ItemStack(LCItems.material, 1, Material.UNDER_BONE));
		RecipeHelper.addShapelessRecipe(new ItemStack(Items.DYE, 6, 1), new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD));
		RecipeHelper.addShapelessRecipe(new ItemStack(Items.DYE, 3, 6), new ItemStack(LCItems.material, 1, Material.UNDER_MEAL));

		// thunderstone
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER), "QXQ","XOX","QXQ", 
				'X',"cobblestone", 'O',Blocks.OBSIDIAN, 'Q',"gemQuartz");
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.THUNDER));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER_BRICK_FANCY), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK_CHISELED));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.THUNDER_BRICK_FANCY_2), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK));
		
		// thunderstone slab and stairs
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.THUNDER), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.thunderStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER_BRICK));

		// demonstone
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.DEMON_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.DEMON));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.DEMON_BRICK_FANCY), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK_CHISELED));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.DEMON_BRICK_FANCY_2), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK));
		
		// demonstone slab and stairs
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.DEMON), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.demonStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK));

		// understone
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.UNDER_BRICK), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), "X","X",
				'X',new ItemStack(LCBlocks.slabBlock, 1, BlockSlabLC.UNDER));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.UNDER_BRICK_FANCY), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.stoneBlock, 4, BlockStone.UNDER_BRICK_FANCY_2), "XX","XX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK));
		
		// understone slab and stairs
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.UNDER), "XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.underStairs, 4), "X  ","XX ","XXX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK));

		// lantern to lamp (decorative)
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP), "RBR","BLB","RBR",
				'R',Blocks.GLOWSTONE, 'B',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), 
				'L',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP_FANCY), "AB","BA",
				'A',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER), 'B',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER_LAMP));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP_FANCY_2), "AA","AA",
				'A',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER_LAMP_FANCY));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP_FANCY_3), "AA","AA",
				'A',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER_LAMP_FANCY_2));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightBlock, 4, BlockLight.UNDER_LAMP_FANCY), "AA","AA",
				'A',new ItemStack(LCBlocks.lightBlock, 1, BlockLight.UNDER_LAMP_FANCY_3));
		
		// corrupt walls
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wallBlock, 6), "BBB","BBB", 'B',LCBlocks.corruptStone);
		
		// TNT
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.underTNT, 1, BlockUnderTNT.LIGHTNING), "GSG","SGS","GSG",
				'G',new ItemStack(LCItems.material, 1, Material.UNDER_POWDER_2), 'S',LCBlocks.underSand);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.underTNT, 1, BlockUnderTNT.MYSTIC), "GSG","TGT","GSG",
				'G',new ItemStack(LCItems.material, 1, Material.DIVINE_DUST), 'S',new ItemStack(LCBlocks.underTNT, 1, BlockUnderTNT.LIGHTNING), 'T',Blocks.TNT);

		// wood
		RecipeHelper.addShapelessRecipe(new ItemStack(LCBlocks.woodPlank, 4), LCBlocks.woodLog);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.slabBlock, 6, BlockSlabLC.UNDER_PLANK), "XXX", 'X',LCBlocks.woodPlank);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.underPlankStairs, 4), "X  ","XX ","XXX", 'X',LCBlocks.woodPlank);

		// tool recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecHammer), "PPP","XIX"," I ", 'X',"ingotElectricium", 'I',"rodElectricium", 'P',"plateElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecSword), " X ","PXP"," I ", 'X',"ingotElectricium", 'I',"rodElectricium", 'P',"plateElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecPick), "XXX"," I "," I ", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecAxe), "XX","XI"," I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecShovel), "X","I","I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecHoe), "XX"," I"," I", 'X',"ingotElectricium", 'I',"rodElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyHammer), "PPP","XIX"," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather", 'P',"plateSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skySword), " X ","PXP"," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather", 'P',"plateSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyPick), "XXX"," I "," I ", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyAxe), "XX","XI"," I", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyShovel), "X","I","I", 'X',"ingotSkyfather", 'I',"rodSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyHoe), "XX"," I"," I", 'X',"ingotSkyfather", 'I',"rodSkyfather");

		// armor recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecHelm), "XXX","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecChest), "X X","XXX","XXX", 'X',"ingotElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecLegs), "XXX","X X","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.elecBoots), "X X","X X", 'X',"ingotElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyHelm), "XXX","X X", 'X',"ingotSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyChest), "X X","XXX","XXX", 'X',"ingotSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyLegs), "XXX","X X","X X", 'X',"ingotSkyfather");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.skyBoots), "X X","X X", 'X',"ingotSkyfather");

		// kinetic tools
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticSword), "RCR","RAR","RER",
				'A',Items.GOLDEN_SWORD, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticPick), "RCR","RAR","RER",
				'A',Items.GOLDEN_PICKAXE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticAxe), "RCR","RAR","RER",
				'A',Items.GOLDEN_AXE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticShovel), "RCR","RAR","RER",
				'A',Items.GOLDEN_SHOVEL, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);

		// kinetic armor
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticHelm), "RCR","RAR","RER", 
				'A',Items.GOLDEN_HELMET, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticChest), "RCR","RAR","RER", 
				'A',Items.GOLDEN_CHESTPLATE, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticLegs), "RCR","RAR","RER", 
				'A',Items.GOLDEN_LEGGINGS, 'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.kineticBoots), "RCR","RAR","RER", 
				'A',Items.GOLDEN_BOOTS,  'E',"plateElectricium", 'R',"dustRedstone", 'C',Items.COMPARATOR);

		// cell recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC), "XAX","IBI","XAX",
				'B',"plateGold", 'X',"rodIron", 'I',"plateElectricium", 'A',"dustRedstone");
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.SKY), "XAX","IBI","XAX",
				'B',new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC), 'X',"rodGold", 'I',"plateSkyfather", 'A',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCell, 1, Ingot.MYSTIC), "XAX","IBI","XAX",
				'B',new ItemStack(LCBlocks.lightningCell, 1, Ingot.SKY), 'X',"rodSkyfather", 'I',"plateMystic", 'A',"plateElectricium");

		// cell upgrade
		ItemStack lUpgrade = new ItemStack(LCItems.material, 1, Material.UPGRADE);
		RecipeHelper.addShapedRecipe(lUpgrade, " R ","RXR"," R ", 'X',"plateSkyfather", 'R',"dustRedstone");

		// other machine recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningFurnace), "XCX","XAX","XIX",
				'X',"cobblestone", 'I',"plateIron", 'A',Blocks.FURNACE, 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCrusher), "ZCZ","XAX","ZIZ",
				'Z',Blocks.IRON_BARS, 'X',Blocks.OBSIDIAN, 'I',"plateElectricium", 'A',LCBlocks.lightningFurnace, 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningInfuser), "XCX","XAX","XIX",
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.THUNDER), 'I',"plateGold", 'A',"rodElectricium", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningBreaker), "XCX","GAG","XIX",
				'X',"plateIron", 'G',"plateGold", 'I',Items.DIAMOND_PICKAXE, 'A',"blockIron", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningMiner), "XCX","GAG","XIX",
				'X',"plateGold", 'G',"plateSkyfather", 'I',LCItems.elecPick, 'A',new ItemStack(LCBlocks.lightningBreaker), 'C',"plateElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.staticGenerator), "XCX","COC","XCX", 
				'C',Items.COMPARATOR, 'X',Blocks.QUARTZ_BLOCK, 'O',new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.enchReallocator), "TCT","XAX","XEX",
				'A',new ItemStack(LCItems.material, 1, Material.ENSORCELLED), 'E',Blocks.ENCHANTING_TABLE, 
				'T',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.UNDER_BRICK_CHISELED), 'C',Items.COMPARATOR, 
				'X',new ItemStack(LCBlocks.stoneBlock, 1, BlockStone.DEMON_BRICK_CHISELED));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCannon, 1, 1), "ERE","ICI","ETE", 
				'C',new ItemStack(LCItems.material, 1, Material.CANNON_CORE), 'I',"rodIron", 'E',"rodElectricium", 'T',Blocks.TNT, 'R',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.lightningCannon, 1, 2), "EQE","ICI","EDE", 
				'C',new ItemStack(LCBlocks.lightningCannon, 1, 1), 'I',"rodMystic", 'E',"rodSkyfather", 'Q',lUpgrade, 'D',"dustMystic");

		// wireless recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 0), " T ","XOX","XCX", 'C',"dustDiamond",
				'X',"plateIron", 'O',lUpgrade, 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.ELEC));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 1), " T ","XOX","XCX", 'C',Items.COMPARATOR,
				'X',"rodIron", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 0), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.SKY));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 2), " T ","XOX","XCX", 'C',"plateElectricium",
				'X',"blockIron", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 1), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.MYSTIC));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 3), " T ","XOX","XCX", 'C',"dustRedstone",
				'X',"plateGold", 'O',lUpgrade, 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.ELEC));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 4), " T ","XOX","XCX", 'C',Items.COMPARATOR,
				'X',"rodGold", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 3), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.SKY));
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.wirelessBlock, 1, 5), " T ","XOX","XCX", 'C',"blockRedstone",
				'X',"blockGold", 'O',new ItemStack(LCBlocks.wirelessBlock, 1, 4), 'T',new ItemStack(LCBlocks.airTerminal, 1, Rod.MYSTIC));

		// RF machines
		if(EnergyApiHelper.rfOrTeslaLoaded) {
			RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.rfProvider), "BRB","CAC","BCB",
					'B',"plateElectricium", 'A',"blockIron", 'R',Items.COMPARATOR, 'C',"plateIron");
			RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.rfReceiver), "BRB","CAC","BCB",
					'B',"blockRedstone", 'A',"blockGold", 'R',Items.COMPARATOR, 'C',"plateIron");
		}

		// wireless markers
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.wirelessMarker, 1), " C ","EPE"," U ", 
				'P',Items.PAPER, 'E',"dustElectricium", 'U',lUpgrade, 'C',Items.COMPARATOR);
		RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.wirelessMarker, 1, 0), new ItemStack(LCItems.wirelessMarker, 1, 1));

		// battery recipes
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.battery, 1, 0), "ICI","IBI","IRI", 
				'B',"plateElectricium", 'I',"rodIron", 'R',"dustRedstone", 'C',Items.COMPARATOR);
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.battery, 1, 1), "RUR","RBR","RIR", 
				'B',new ItemStack(LCItems.battery, 1, 0), 'U',lUpgrade, 'R',"dustRedstone", 'I',"rodElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.battery, 1, 2), "RUR","RBR","RIR", 
				'B',new ItemStack(LCItems.battery, 1, 1), 'U',"ingotSkyfather", 'R',"blockRedstone", 'I',"blockElectricium");
		RecipeHelper.addShapedRecipe(new ItemStack(LCBlocks.chargingPlate), "RCR","RPR","RER", 'E',"plateElectricium", 
				'P',Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'R',"dustRedstone", 'C',Items.COMPARATOR);

		// magnets
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.itemMagnet, 1, 0), "INN","  N","INN", 'I',"plateIron", 'N',"blockRedstone");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.itemMagnet, 1, 1), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 0), 'E',"ingotIron");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.itemMagnet, 1, 2), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 1), 'E',"ingotGold");
		RecipeHelper.addShapedRecipe(new ItemStack(LCItems.itemMagnet, 1, 3), "IEE"," ME","IEE", 
				'I',"plateIron", 'M',new ItemStack(LCItems.itemMagnet, 1, 2), 'E',"ingotElectricium");

	}

	/** Add smelting recipes */
	public static void addSmeltingRecipes() {
		
		// register the fuel handler
		GameRegistry.registerFuelHandler(new LCFuelHandler());
		
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

//	/** Wrap up the crafting stuff here */
//	public static void postInit() {
//		// find all rod prefixes
//		for(String name : OreDictionary.getOreNames()) {
//			if(!name.equals("rod") && name.startsWith("rod") && Metal.getAllNames().contains(name.substring(3))) {
//				for(ItemStack s : OreDictionary.getOres(name)) {
//					// remove the other mods' recipes
//					try {
//						if(LCConfig.disableOtherRods && !s.getItem().getRegistryName().getResourceDomain().equals(RefStrings.MODID)) {
//							RecipeHelper.removeRecipes(s);
//							continue; // don't add it to the valid metal types
//						}
//					} catch(Exception e) {}
//
//					// add it to the "rodLC" group if it's a valid metal type
//					OreDictionary.registerOre("rodLC", s);
//				}
//			}
//		}
//
//		// the guide's recipe
//		RecipeHelper.addShapelessRecipe(new ItemStack(LCItems.guide), Items.BOOK, "rodLC");
//	}

}
