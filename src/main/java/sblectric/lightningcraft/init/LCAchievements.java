package sblectric.lightningcraft.init;

//import net.minecraft.block.Block;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.stats.Achievement;
//import net.minecraftforge.common.AchievementPage;
//import sblectric.lightningcraft.api.util.JointList;
//import sblectric.lightningcraft.blocks.BlockUnderOre;
//import sblectric.lightningcraft.ref.Material;
//import sblectric.lightningcraft.ref.Metal.Ingot;
//import sblectric.lightningcraft.ref.Metal.Rod;
//import sblectric.lightningcraft.ref.RefStrings;
//
///** The class for achievements */
//public class LCAchievements {
//	
//	private static JointList<Achievement> achievements;
//	public static AchievementPage lcAPage;
//	
//	/** Main achievement registry */
//	public static void mainRegistry() {
//		achievements = new JointList();
//		setupPage();
//		registerAchievements();
//	}
//	
//	/** Define the page */
//	private static void setupPage() {
//		lcAPage = new AchievementPage(RefStrings.NAME);
//		AchievementPage.registerAchievementPage(lcAPage);
//	}
//	
//	// 2.0+
//	public static Achievement craftRod;
//	public static Achievement craftGuide;
//	public static Achievement swingGolfClub;
//	public static Achievement getElectricium;
//	public static Achievement craftCell;
//	public static Achievement craftTerminal;
//	public static Achievement craftFurnace;
//	public static Achievement craftCrusher;
//	public static Achievement craftInfuser;
//	public static Achievement craftGenerator;
//	public static Achievement craftBattery;
//	public static Achievement craftKinetic;
//	public static Achievement infuseSpecialSword;
//	public static Achievement specialSwordKill;
//	public static Achievement infuseSkyfather;
//	public static Achievement upgradeMachine;
//	public static Achievement craftMiner;
//	public static Achievement craftWireless;
//	public static Achievement killDemon;
//	public static Achievement reachUnderworld;
//	public static Achievement mineUnderworldOre;
//	public static Achievement craftReallocator;
//	public static Achievement infuseMystic;
//	public static Achievement mysticArmor;
//	public static Achievement mysticHammer;
//	public static Achievement perfectCell;
//	
//	/** Register the achievements */
//	private static void registerAchievements() {
//		ItemStack chestplate = new ItemStack(LCItems.mysticChest); chestplate.addEnchantment(LCEnchantments.elecAura, 3);
//		ItemStack hammer = new ItemStack(LCItems.mysticHammer); hammer.addEnchantment(LCEnchantments.handOfThor, 3);
//		
//		achievements.join(
//			craftRod = addAchievement(RefStrings.MODID + ":craftRod", "craftRod", 0, 0, 
//					new ItemStack(LCItems.rod, 1, Rod.IRON), null).initIndependentStat().registerStat(),
//			craftGuide = addAchievement(RefStrings.MODID + ":craftGuide", "craftGuide", 1, 2, 
//					LCItems.guide, craftRod).registerStat(),		
//			swingGolfClub = addAchievement(RefStrings.MODID + ":swingGolfClub", "swingGolfClub", 2, 0, 
//					LCItems.golfClub, craftRod).registerStat(),
//			getElectricium = addAchievement(RefStrings.MODID + ":getElectricium", "getElectricium", 4, 0, 
//					new ItemStack(LCItems.ingot, 1, Ingot.ELEC), swingGolfClub).registerStat(),
//			craftCell = addAchievement(RefStrings.MODID + ":craftCell", "craftCell", 6, 0, 
//					new ItemStack(LCBlocks.lightningCell, 1, Ingot.ELEC), getElectricium).registerStat(),
//			craftTerminal = addAchievement(RefStrings.MODID + ":craftTerminal", "craftTerminal", 6, -2, 
//					new ItemStack(LCBlocks.airTerminal, 1, Rod.IRON), craftCell).registerStat(),
//			craftFurnace = addAchievement(RefStrings.MODID + ":craftFurnace", "craftFurnace", 5, 3, 
//					LCBlocks.lightningFurnace, craftCell).registerStat(),
//			craftCrusher = addAchievement(RefStrings.MODID + ":craftCrusher", "craftCrusher", 5, 5, 
//					LCBlocks.lightningCrusher, craftCell).registerStat(),
//			craftInfuser = addAchievement(RefStrings.MODID + ":craftInfuser", "craftInfuser", 8, 4, 
//					LCBlocks.lightningInfuser, craftCell).registerStat(),
//			craftGenerator = addAchievement(RefStrings.MODID + ":craftGenerator", "craftGenerator", 7, 2, 
//					LCBlocks.staticGenerator, craftCell).registerStat(),
//			craftBattery = addAchievement(RefStrings.MODID + ":craftBattery", "craftBattery", 8, 0, 
//					LCItems.battery, craftCell).registerStat(),
//			craftKinetic = addAchievement(RefStrings.MODID + ":craftKinetic", "craftKinetic", 10, -1, 
//					LCItems.kineticChest, craftBattery).registerStat(),
//			infuseSpecialSword = addAchievement(RefStrings.MODID + ":infuseSpecialSword", "infuseSpecialSword", 10, 3, 
//					Items.STONE_SWORD, craftInfuser).registerStat(),
//			specialSwordKill = addAchievement(RefStrings.MODID + ":specialSwordKill", "specialSwordKill", 12, 3, 
//					LCItems.blazeSword, infuseSpecialSword).registerStat(),
//			infuseSkyfather = addAchievement(RefStrings.MODID + ":infuseSkyfather", "infuseSkyfather", 9, 6, 
//					new ItemStack(LCItems.ingot, 1, Ingot.SKY), craftInfuser).registerStat(), // semi-independent
//			upgradeMachine = addAchievement(RefStrings.MODID + ":upgradeCell", "upgradeCell", 11, 6, 
//					new ItemStack(LCItems.material, 1, Material.UPGRADE), infuseSkyfather).registerStat(),
//			craftMiner = addAchievement(RefStrings.MODID + ":craftMiner", "craftMiner", 11, 8, 
//					LCBlocks.lightningMiner, infuseSkyfather).registerStat(),
//			craftWireless = addAchievement(RefStrings.MODID + ":craftWireless", "craftWireless", 13, 7, 
//					LCBlocks.wirelessBlock, upgradeMachine).registerStat(),
//			killDemon = addAchievement(RefStrings.MODID + ":killDemon", "killDemon", 6, 7, 
//					new ItemStack(LCItems.material, 1, Material.DEMON_BLOOD), infuseSkyfather).registerStat(),
//			reachUnderworld = addAchievement(RefStrings.MODID + ":reachUnderworld", "reachUnderworld", 6, 9, 
//					new ItemStack(LCItems.material, 1, Material.UNDER_CHARGE), killDemon).registerStat(),
//			mineUnderworldOre = addAchievement(RefStrings.MODID + ":mineUnderworldOre", "mineUnderworldOre", 6, 11, 
//					new ItemStack(LCBlocks.oreBlock, 1, BlockUnderOre.EMERALD), reachUnderworld).registerStat(),
//			craftReallocator = addAchievement(RefStrings.MODID + ":craftReallocator", "craftReallocator", 8, 9, 
//					new ItemStack(LCItems.material, 1, Material.ENSORCELLED), reachUnderworld).registerStat(),
//			infuseMystic = addAchievement(RefStrings.MODID + ":infuseMystic", "infuseMystic", 4, 9, 
//					new ItemStack(LCItems.ingot, 1, Ingot.MYSTIC), reachUnderworld).registerStat().setSpecial(),
//			mysticArmor = addAchievement(RefStrings.MODID + ":mysticArmor", "mysticArmor", 2, 9, 
//					chestplate, infuseMystic).registerStat().setSpecial(),
//			mysticHammer = addAchievement(RefStrings.MODID + ":mysticHammer", "mysticHammer", 1, 8, 
//					hammer, infuseMystic).registerStat().setSpecial(),
//			perfectCell = addAchievement(RefStrings.MODID + ":perfectCell", "perfectCell", 4, 11, 
//					new ItemStack(LCBlocks.lightningCell, 1, Ingot.MYSTIC), infuseMystic).registerStat()
//		);
//	}
//	
//	/** Add an achievement to the page and return it */
//	private static Achievement addAchievement(String ref, String name, int x, int y, ItemStack stack, Achievement parent) {
//		Achievement a = new Achievement(ref, ref, x, y, stack, parent);
//		lcAPage.getAchievements().add(a);
//		return a;
//	}
//	
//	/** Add an achievement to the page and return it */
//	private static Achievement addAchievement(String ref, String name, int x, int y, Item item, Achievement parent) {
//		return addAchievement(ref, name, x, y, new ItemStack(item), parent);
//	}
//	
//	/** Add an achievement to the page and return it */
//	private static Achievement addAchievement(String ref, String name, int x, int y, Block item, Achievement parent) {
//		return addAchievement(ref, name, x, y, new ItemStack(item), parent);
//	}
//
//}
