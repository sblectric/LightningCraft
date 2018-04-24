package sblectric.lightningcraft.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.api.registry.ILightningCraftItem;
import sblectric.lightningcraft.api.util.JointList;
import sblectric.lightningcraft.items.ItemBattery;
import sblectric.lightningcraft.items.ItemBlazeSword;
import sblectric.lightningcraft.items.ItemChargedSword;
import sblectric.lightningcraft.items.ItemElecArmor;
import sblectric.lightningcraft.items.ItemEnderSword;
import sblectric.lightningcraft.items.ItemFeatherSword;
import sblectric.lightningcraft.items.ItemGoldenClub;
import sblectric.lightningcraft.items.ItemGolfClub;
import sblectric.lightningcraft.items.ItemHammer;
import sblectric.lightningcraft.items.ItemIceSword;
import sblectric.lightningcraft.items.ItemKineticArmor;
import sblectric.lightningcraft.items.ItemKineticAxe;
import sblectric.lightningcraft.items.ItemKineticPick;
import sblectric.lightningcraft.items.ItemKineticSpade;
import sblectric.lightningcraft.items.ItemKineticSword;
import sblectric.lightningcraft.items.ItemLightningGuide;
import sblectric.lightningcraft.items.ItemMagnet;
import sblectric.lightningcraft.items.ItemMaterial;
import sblectric.lightningcraft.items.ItemMetalDust;
import sblectric.lightningcraft.items.ItemMetalIngot;
import sblectric.lightningcraft.items.ItemMetalNugget;
import sblectric.lightningcraft.items.ItemMetalPlate;
import sblectric.lightningcraft.items.ItemMetalRod;
import sblectric.lightningcraft.items.ItemMysticArmor;
import sblectric.lightningcraft.items.ItemMysticAxe;
import sblectric.lightningcraft.items.ItemMysticHammer;
import sblectric.lightningcraft.items.ItemMysticHoe;
import sblectric.lightningcraft.items.ItemMysticPick;
import sblectric.lightningcraft.items.ItemMysticSpade;
import sblectric.lightningcraft.items.ItemMysticSword;
import sblectric.lightningcraft.items.ItemSkyArmor;
import sblectric.lightningcraft.items.ItemSkyAxe;
import sblectric.lightningcraft.items.ItemSkyHammer;
import sblectric.lightningcraft.items.ItemSkyHoe;
import sblectric.lightningcraft.items.ItemSkyPick;
import sblectric.lightningcraft.items.ItemSkySpade;
import sblectric.lightningcraft.items.ItemSkySword;
import sblectric.lightningcraft.items.ItemSoulSword;
import sblectric.lightningcraft.items.ItemWirelessMarker;
import sblectric.lightningcraft.items.ItemZombieSword;
import sblectric.lightningcraft.items.base.ItemArmorLC;
import sblectric.lightningcraft.items.base.ItemAxeLC;
import sblectric.lightningcraft.items.base.ItemHoeLC;
import sblectric.lightningcraft.items.base.ItemLC;
import sblectric.lightningcraft.items.base.ItemPickaxeLC;
import sblectric.lightningcraft.items.base.ItemSpadeLC;
import sblectric.lightningcraft.items.base.ItemSwordLC;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.registry.RegistryHelper;
import sblectric.lightningcraft.util.ArmorHelper;
import sblectric.lightningcraft.util.ToolHelper;

public class LCItems {

	/** The list of items to help with registration */
	private static JointList<ILightningCraftItem> items;

	/** Create the items */
	public static void createItems() {
		items = new JointList();
		setupMaterials();
		addItems();
		finalizeMaterials();
	}

	// materials
	public static ToolMaterial elecMat;
	public static ToolMaterial elecHammerMat;
	public static ToolMaterial skyMat;
	public static ToolMaterial skyHammerMat;
	public static ToolMaterial mysticMat;
	public static ToolMaterial mysticHammerMat;
	public static ToolMaterial kineticMat;
	public static ToolMaterial soulMat;
	public static ToolMaterial zombieMat;
	public static ToolMaterial featherMat;
	public static ToolMaterial enderMat;
	public static ToolMaterial blazeMat;
	public static ToolMaterial iceMat;
	public static ArmorMaterial elecArmor;
	public static ArmorMaterial skyArmor;
	public static ArmorMaterial mysticArmor;
	public static ArmorMaterial kineticArmor;

	/** Set up tool and armor materials */
	private static void setupMaterials() {
		// tools
		elecMat = EnumHelper.addToolMaterial("Electricium", 4, 3000, 14.0f, 5.0f, 22);
		elecHammerMat = EnumHelper.addToolMaterial("Electricium Hammer", 4, 3500, 16.0f, 9.0f, 22);
		skyMat = EnumHelper.addToolMaterial("Skyfather", 5, 3500, 18.0f, 6.5f, 30);
		skyHammerMat = EnumHelper.addToolMaterial("Skyfather Hammer", 5, 3500, 18.0f, 12.0f, 30);
		mysticMat = EnumHelper.addToolMaterial("Mystic", 6, 4000, 18.0f, 8.0f, 30);
		mysticHammerMat = EnumHelper.addToolMaterial("Mystic Hammer", 6, 4000, 18.0f, 15.0f, 30);
		kineticMat = EnumHelper.addToolMaterial("Kinetic Shard", 3, 100, 1.0f, 0.0f, 1);
		
		// special tools
		soulMat = EnumHelper.addToolMaterial("Soul", 1, 150, 4.0F, 1.5F, 10);
		zombieMat = EnumHelper.addToolMaterial("Zombie", 1, 131, 4.0F, 1.0F, 10);
		featherMat = EnumHelper.addToolMaterial("Feather", 1, 131, 4.0F, 1.0F, 10);
		enderMat = EnumHelper.addToolMaterial("Ender", 1, 200, 4.0F, 1.5F, 10);
		blazeMat = EnumHelper.addToolMaterial("Blaze", 1, 150, 4.0F, 1.0F, 10);
		iceMat = EnumHelper.addToolMaterial("Ice", 1, 100, 4.0F, 0.5F, 10);

		// armor
		elecArmor = ArmorHelper.addArmorMaterial("Electricium", RefStrings.MODID + ":elec_armor", 40, new int[]{3, 6, 8, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2F);
		skyArmor = ArmorHelper.addArmorMaterial("Skyfather", RefStrings.MODID + ":sky_armor", 50, new int[]{3, 7, 8, 4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.5F);
		mysticArmor = ArmorHelper.addArmorMaterial("Mystic", RefStrings.MODID + ":mystic_armor", 60, new int[]{4, 7, 8, 4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3F);
		kineticArmor = ArmorHelper.addArmorMaterial("Kinetic", RefStrings.MODID + ":kinetic_armor", 4, new int[]{1, 3, 5, 2}, 1, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	}
	
	/** Finish up material initialization */
	private static void finalizeMaterials() {
		// tools
		ToolHelper.setRepairStack(elecMat, new ItemStack(ingot, 1, Ingot.ELEC));
		ToolHelper.setRepairStack(elecHammerMat, new ItemStack(ingot, 1, Ingot.ELEC));
		ToolHelper.setRepairStack(skyMat, new ItemStack(ingot, 1, Ingot.SKY));
		ToolHelper.setRepairStack(skyHammerMat, new ItemStack(ingot, 1, Ingot.SKY));
		ToolHelper.setRepairStack(mysticMat, new ItemStack(ingot, 1, Ingot.MYSTIC));
		ToolHelper.setRepairStack(mysticHammerMat, new ItemStack(ingot, 1, Ingot.MYSTIC));
		
		// special tools
		ToolHelper.setRepairStack(soulMat, new ItemStack(Blocks.SOUL_SAND));
		ToolHelper.setRepairStack(zombieMat, new ItemStack(Items.ROTTEN_FLESH));
		ToolHelper.setRepairStack(featherMat, new ItemStack(Items.FEATHER));
		ToolHelper.setRepairStack(enderMat, new ItemStack(Items.ENDER_PEARL));
		ToolHelper.setRepairStack(blazeMat, new ItemStack(Items.BLAZE_ROD));
		ToolHelper.setRepairStack(iceMat, new ItemStack(Blocks.PACKED_ICE));
		
		// armor
		ArmorHelper.setRepairStack(elecArmor, new ItemStack(ingot, 1, Ingot.ELEC));
		ArmorHelper.setRepairStack(skyArmor, new ItemStack(ingot, 1, Ingot.SKY));
		ArmorHelper.setRepairStack(mysticArmor, new ItemStack(ingot, 1, Ingot.MYSTIC));
	}

	// the items
	public static ItemLC guide;
	public static ItemLC golfClub;
	public static ItemLC golfClubGold;
	public static ItemLC ingot;
	public static ItemLC nugget;
	public static ItemLC dust;
	public static ItemLC rod;
	public static ItemLC plate;
	public static ItemLC material;
	public static ItemLC battery;
	public static ItemLC itemMagnet;
	public static ItemLC wirelessMarker;
	public static ItemSwordLC elecHammer, elecSword;
	public static ItemPickaxeLC elecPick;
	public static ItemAxeLC elecAxe;
	public static ItemSpadeLC elecShovel;
	public static ItemHoeLC elecHoe;
	public static ItemArmorLC elecHelm, elecChest, elecLegs, elecBoots;
	public static ItemSwordLC soulSword, zombieSword, featherSword, enderSword, blazeSword, iceSword;
	public static ItemSwordLC skyHammer, skySword;
	public static ItemPickaxeLC skyPick;
	public static ItemAxeLC skyAxe;
	public static ItemSpadeLC skyShovel;
	public static ItemHoeLC skyHoe;
	public static ItemArmorLC skyHelm, skyChest, skyLegs, skyBoots;
	public static ItemSwordLC kineticSword;
	public static ItemPickaxeLC kineticPick;
	public static ItemAxeLC kineticAxe;
	public static ItemSpadeLC kineticShovel;
	public static ItemArmorLC kineticHelm, kineticChest, kineticLegs, kineticBoots;
	public static ItemSwordLC mysticHammer, mysticSword;
	public static ItemPickaxeLC mysticPick;
	public static ItemAxeLC mysticAxe;
	public static ItemSpadeLC mysticShovel;
	public static ItemHoeLC mysticHoe;
	public static ItemArmorLC mysticHelm, mysticChest, mysticLegs, mysticBoots;

	/** Add the items */
	private static void addItems() {
		items.join(
			// basic items
			guide = (ItemLC)new ItemLightningGuide().setRegistryName("guide").setCreativeTab(LCCreativeTabs.items),
			golfClub = (ItemLC)new ItemGolfClub().setRegistryName("golf_club").setCreativeTab(LCCreativeTabs.items),
			golfClubGold = (ItemLC)new ItemGoldenClub().setRegistryName("golf_club_gold").setCreativeTab(LCCreativeTabs.items),
			ingot = (ItemLC)new ItemMetalIngot().setRegistryName("ingot").setCreativeTab(LCCreativeTabs.items),
			nugget = (ItemLC)new ItemMetalNugget().setRegistryName("nugget").setCreativeTab(LCCreativeTabs.items),
			dust = (ItemLC)new ItemMetalDust().setRegistryName("dust").setCreativeTab(LCCreativeTabs.items),
			rod = (ItemLC)new ItemMetalRod().setRegistryName("rod").setCreativeTab(LCCreativeTabs.items),
			plate = (ItemLC)new ItemMetalPlate().setRegistryName("plate").setCreativeTab(LCCreativeTabs.items),
			material = (ItemLC)new ItemMaterial().setRegistryName("material").setCreativeTab(LCCreativeTabs.items),
			battery = (ItemLC)new ItemBattery().setRegistryName("battery").setCreativeTab(LCCreativeTabs.items),
			itemMagnet = (ItemLC)new ItemMagnet().setRegistryName("item_magnet").setCreativeTab(LCCreativeTabs.items),
			wirelessMarker = (ItemLC)new ItemWirelessMarker().setRegistryName("wireless_marker").setCreativeTab(LCCreativeTabs.items),
			
			// electricium tools
			elecHammer = (ItemSwordLC)new ItemHammer(elecHammerMat).setRegistryName("elec_hammer").setCreativeTab(LCCreativeTabs.items),
			elecSword = (ItemSwordLC)new ItemChargedSword(elecMat).setRegistryName("elec_sword").setCreativeTab(LCCreativeTabs.items),
			elecPick = (ItemPickaxeLC)new ItemPickaxeLC(elecMat).setRegistryName("elec_pick").setCreativeTab(LCCreativeTabs.items),
			elecAxe = (ItemAxeLC)new ItemAxeLC(elecMat).setRegistryName("elec_axe").setCreativeTab(LCCreativeTabs.items),
			elecShovel = (ItemSpadeLC)new ItemSpadeLC(elecMat).setRegistryName("elec_shovel").setCreativeTab(LCCreativeTabs.items),
			elecHoe = (ItemHoeLC)new ItemHoeLC(elecMat).setRegistryName("elec_hoe").setCreativeTab(LCCreativeTabs.items),
			
			// electricium armor
			elecHelm = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.HEAD).setRegistryName("elec_helm").setCreativeTab(LCCreativeTabs.items),
			elecChest = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.CHEST).setRegistryName("elec_chest").setCreativeTab(LCCreativeTabs.items),
			elecLegs = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.LEGS).setRegistryName("elec_legs").setCreativeTab(LCCreativeTabs.items),
			elecBoots = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.FEET).setRegistryName("elec_boots").setCreativeTab(LCCreativeTabs.items),
			
			// special swords
			soulSword = (ItemSwordLC)new ItemSoulSword(soulMat).setRegistryName("soul_sword").setCreativeTab(LCCreativeTabs.items),
			zombieSword = (ItemSwordLC)new ItemZombieSword(zombieMat).setRegistryName("zombie_sword").setCreativeTab(LCCreativeTabs.items),
			featherSword = (ItemSwordLC)new ItemFeatherSword(featherMat).setRegistryName("feather_sword").setCreativeTab(LCCreativeTabs.items),
			enderSword = (ItemSwordLC)new ItemEnderSword(enderMat).setRegistryName("ender_sword").setCreativeTab(LCCreativeTabs.items),
			blazeSword = (ItemSwordLC)new ItemBlazeSword(blazeMat).setRegistryName("blaze_sword").setCreativeTab(LCCreativeTabs.items),
			iceSword = (ItemSwordLC)new ItemIceSword(iceMat).setRegistryName("ice_sword").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather tools
			skyHammer = (ItemSwordLC)new ItemSkyHammer(skyHammerMat).setRegistryName("sky_hammer").setCreativeTab(LCCreativeTabs.items),
			skySword = (ItemSwordLC)new ItemSkySword(skyMat).setRegistryName("sky_sword").setCreativeTab(LCCreativeTabs.items),
			skyPick = (ItemPickaxeLC)new ItemSkyPick(skyMat).setRegistryName("sky_pick").setCreativeTab(LCCreativeTabs.items),
			skyAxe = (ItemAxeLC)new ItemSkyAxe(skyMat).setRegistryName("sky_axe").setCreativeTab(LCCreativeTabs.items),
			skyShovel = (ItemSpadeLC)new ItemSkySpade(skyMat).setRegistryName("sky_shovel").setCreativeTab(LCCreativeTabs.items),
			skyHoe = (ItemHoeLC)new ItemSkyHoe(skyMat).setRegistryName("sky_hoe").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather armor
			skyHelm = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.HEAD).setRegistryName("sky_helm").setCreativeTab(LCCreativeTabs.items),
			skyChest = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.CHEST).setRegistryName("sky_chest").setCreativeTab(LCCreativeTabs.items),
			skyLegs = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.LEGS).setRegistryName("sky_legs").setCreativeTab(LCCreativeTabs.items),
			skyBoots = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.FEET).setRegistryName("sky_boots").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic tools
			kineticSword = (ItemSwordLC)new ItemKineticSword(kineticMat).setRegistryName("kinetic_sword").setCreativeTab(LCCreativeTabs.items),
			kineticPick = (ItemPickaxeLC)new ItemKineticPick(kineticMat).setRegistryName("kinetic_pick").setCreativeTab(LCCreativeTabs.items),
			kineticAxe = (ItemAxeLC)new ItemKineticAxe(kineticMat).setRegistryName("kinetic_axe").setCreativeTab(LCCreativeTabs.items),
			kineticShovel = (ItemSpadeLC)new ItemKineticSpade(kineticMat).setRegistryName("kinetic_shovel").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic armor
			kineticHelm = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.HEAD).
					setRegistryName("kinetic_helm").setCreativeTab(LCCreativeTabs.items),
			kineticChest = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.CHEST).
					setRegistryName("kinetic_chest").setCreativeTab(LCCreativeTabs.items),
			kineticLegs = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.LEGS).
					setRegistryName("kinetic_legs").setCreativeTab(LCCreativeTabs.items),
			kineticBoots = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.FEET).
					setRegistryName("kinetic_boots").setCreativeTab(LCCreativeTabs.items),
			
			// mystic tools
			mysticHammer = (ItemSwordLC)new ItemMysticHammer(mysticHammerMat).setRegistryName("mystic_hammer").setCreativeTab(LCCreativeTabs.items),
			mysticSword = (ItemSwordLC)new ItemMysticSword(mysticMat).setRegistryName("mystic_sword").setCreativeTab(LCCreativeTabs.items),
			mysticPick = (ItemPickaxeLC)new ItemMysticPick(mysticMat).setRegistryName("mystic_pick").setCreativeTab(LCCreativeTabs.items),
			mysticAxe = (ItemAxeLC)new ItemMysticAxe(mysticMat).setRegistryName("mystic_axe").setCreativeTab(LCCreativeTabs.items),
			mysticShovel = (ItemSpadeLC)new ItemMysticSpade(mysticMat).setRegistryName("mystic_shovel").setCreativeTab(LCCreativeTabs.items),
			mysticHoe = (ItemHoeLC)new ItemMysticHoe(mysticMat).setRegistryName("mystic_hoe").setCreativeTab(LCCreativeTabs.items),
			
			// mystic armor
			mysticHelm = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.HEAD).
					setRegistryName("mystic_helm").setCreativeTab(LCCreativeTabs.items),
			mysticChest = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.CHEST).
					setRegistryName("mystic_chest").setCreativeTab(LCCreativeTabs.items),
			mysticLegs = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.LEGS).
					setRegistryName("mystic_legs").setCreativeTab(LCCreativeTabs.items),
			mysticBoots = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.FEET).
					setRegistryName("mystic_boots").setCreativeTab(LCCreativeTabs.items)
		);
	}

	/** Register the items */
	public static void registerItems() {
		RegistryHelper.registerItems(items);
	}

	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		for(ILightningCraftItem item : items) item.registerRender();
	}

}
