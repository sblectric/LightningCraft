package sblectric.lightningcraft.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sblectric.lightningcraft.creativetabs.LCCreativeTabs;
import sblectric.lightningcraft.ref.Material;
import sblectric.lightningcraft.ref.Metal.Dust;
import sblectric.lightningcraft.ref.Metal.Ingot;
import sblectric.lightningcraft.ref.Metal.Nugget;
import sblectric.lightningcraft.ref.Metal.Plate;
import sblectric.lightningcraft.ref.Metal.Rod;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.registry.IRegistryItem;
import sblectric.lightningcraft.registry.RegistryHelper;
import sblectric.lightningcraft.util.ArmorHelper;
import sblectric.lightningcraft.util.JointList;

public class LCItems {

	/** The list of items to help with registration */
	private static JointList<IRegistryItem> items;

	/** The main item registry */
	public static void mainRegistry() {
		items = new JointList();
		setupMaterials();
		addItems();
		finalizeMaterials();
		registerItems();
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
		elecArmor = ArmorHelper.addArmorMaterial("Electricium", RefStrings.MODID + ":elecArmor", 40, new int[]{3, 6, 8, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2F);
		skyArmor = ArmorHelper.addArmorMaterial("Skyfather", RefStrings.MODID + ":skyArmor", 50, new int[]{3, 7, 8, 4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.5F);
		mysticArmor = ArmorHelper.addArmorMaterial("Mystic", RefStrings.MODID + ":mysticArmor", 60, new int[]{4, 7, 8, 4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 3F);
		kineticArmor = ArmorHelper.addArmorMaterial("Kinetic", RefStrings.MODID + ":kineticArmor", 4, new int[]{1, 3, 5, 2}, 1, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0F);
	}
	
	/** Finish up material initialization */
	private static void finalizeMaterials() {
		// tools
		elecMat.setRepairItem(new ItemStack(ingot, 1, Ingot.ELEC));
		elecHammerMat.setRepairItem(new ItemStack(ingot, 1, Ingot.ELEC));
		skyMat.setRepairItem(new ItemStack(ingot, 1, Ingot.SKY));
		skyHammerMat.setRepairItem(new ItemStack(ingot, 1, Ingot.SKY));
		mysticMat.setRepairItem(new ItemStack(ingot, 1, Ingot.MYSTIC));
		mysticHammerMat.setRepairItem(new ItemStack(ingot, 1, Ingot.MYSTIC));
		
		// special tools
		soulMat.setRepairItem(new ItemStack(Blocks.SOUL_SAND));
		zombieMat.setRepairItem(new ItemStack(Items.ROTTEN_FLESH));
		featherMat.setRepairItem(new ItemStack(Items.FEATHER));
		enderMat.setRepairItem(new ItemStack(Items.ENDER_PEARL));
		blazeMat.setRepairItem(new ItemStack(Items.BLAZE_ROD));
		iceMat.setRepairItem(new ItemStack(Blocks.PACKED_ICE));
		
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
			golfClub = (ItemLC)new ItemGolfClub().setRegistryName("golfClub").setCreativeTab(LCCreativeTabs.items),
			golfClubGold = (ItemLC)new ItemGoldenClub().setRegistryName("golfClubGold").setCreativeTab(LCCreativeTabs.items),
			ingot = (ItemLC)new ItemMetalIngot().setRegistryName("ingot").setCreativeTab(LCCreativeTabs.items),
			nugget = (ItemLC)new ItemMetalNugget().setRegistryName("nugget").setCreativeTab(LCCreativeTabs.items),
			dust = (ItemLC)new ItemMetalDust().setRegistryName("dust").setCreativeTab(LCCreativeTabs.items),
			rod = (ItemLC)new ItemMetalRod().setRegistryName("rod").setCreativeTab(LCCreativeTabs.items),
			plate = (ItemLC)new ItemMetalPlate().setRegistryName("plate").setCreativeTab(LCCreativeTabs.items),
			material = (ItemLC)new ItemMaterial().setRegistryName("material").setCreativeTab(LCCreativeTabs.items),
			battery = (ItemLC)new ItemBattery().setRegistryName("battery").setCreativeTab(LCCreativeTabs.items),
			itemMagnet = (ItemLC)new ItemMagnet().setRegistryName("itemMagnet").setCreativeTab(LCCreativeTabs.items),
			wirelessMarker = (ItemLC)new ItemWirelessMarker().setRegistryName("wirelessMarker").setCreativeTab(LCCreativeTabs.items),
			
			// electricium tools
			elecHammer = (ItemSwordLC)new ItemHammer(elecHammerMat).setRegistryName("elecHammer").setCreativeTab(LCCreativeTabs.items),
			elecSword = (ItemSwordLC)new ItemChargedSword(elecMat).setRegistryName("elecSword").setCreativeTab(LCCreativeTabs.items),
			elecPick = (ItemPickaxeLC)new ItemPickaxeLC(elecMat).setRegistryName("elecPick").setCreativeTab(LCCreativeTabs.items),
			elecAxe = (ItemAxeLC)new ItemAxeLC(elecMat).setRegistryName("elecAxe").setCreativeTab(LCCreativeTabs.items),
			elecShovel = (ItemSpadeLC)new ItemSpadeLC(elecMat).setRegistryName("elecShovel").setCreativeTab(LCCreativeTabs.items),
			elecHoe = (ItemHoeLC)new ItemHoeLC(elecMat).setRegistryName("elecHoe").setCreativeTab(LCCreativeTabs.items),
			
			// electricium armor
			elecHelm = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.HEAD).setRegistryName("elecHelm").setCreativeTab(LCCreativeTabs.items),
			elecChest = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.CHEST).setRegistryName("elecChest").setCreativeTab(LCCreativeTabs.items),
			elecLegs = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.LEGS).setRegistryName("elecLegs").setCreativeTab(LCCreativeTabs.items),
			elecBoots = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.FEET).setRegistryName("elecBoots").setCreativeTab(LCCreativeTabs.items),
			
			// special swords
			soulSword = (ItemSwordLC)new ItemSoulSword(soulMat).setRegistryName("soulSword").setCreativeTab(LCCreativeTabs.items),
			zombieSword = (ItemSwordLC)new ItemZombieSword(zombieMat).setRegistryName("zombieSword").setCreativeTab(LCCreativeTabs.items),
			featherSword = (ItemSwordLC)new ItemFeatherSword(featherMat).setRegistryName("featherSword").setCreativeTab(LCCreativeTabs.items),
			enderSword = (ItemSwordLC)new ItemEnderSword(enderMat).setRegistryName("enderSword").setCreativeTab(LCCreativeTabs.items),
			blazeSword = (ItemSwordLC)new ItemBlazeSword(blazeMat).setRegistryName("blazeSword").setCreativeTab(LCCreativeTabs.items),
			iceSword = (ItemSwordLC)new ItemIceSword(iceMat).setRegistryName("iceSword").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather tools
			skyHammer = (ItemSwordLC)new ItemSkyHammer(skyHammerMat).setRegistryName("skyHammer").setCreativeTab(LCCreativeTabs.items),
			skySword = (ItemSwordLC)new ItemSkySword(skyMat).setRegistryName("skySword").setCreativeTab(LCCreativeTabs.items),
			skyPick = (ItemPickaxeLC)new ItemSkyPick(skyMat).setRegistryName("skyPick").setCreativeTab(LCCreativeTabs.items),
			skyAxe = (ItemAxeLC)new ItemSkyAxe(skyMat).setRegistryName("skyAxe").setCreativeTab(LCCreativeTabs.items),
			skyShovel = (ItemSpadeLC)new ItemSkySpade(skyMat).setRegistryName("skyShovel").setCreativeTab(LCCreativeTabs.items),
			skyHoe = (ItemHoeLC)new ItemSkyHoe(skyMat).setRegistryName("skyHoe").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather armor
			skyHelm = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.HEAD).setRegistryName("skyHelm").setCreativeTab(LCCreativeTabs.items),
			skyChest = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.CHEST).setRegistryName("skyChest").setCreativeTab(LCCreativeTabs.items),
			skyLegs = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.LEGS).setRegistryName("skyLegs").setCreativeTab(LCCreativeTabs.items),
			skyBoots = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.FEET).setRegistryName("skyBoots").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic tools
			kineticSword = (ItemSwordLC)new ItemKineticSword(kineticMat).setRegistryName("kineticSword").setCreativeTab(LCCreativeTabs.items),
			kineticPick = (ItemPickaxeLC)new ItemKineticPick(kineticMat).setRegistryName("kineticPick").setCreativeTab(LCCreativeTabs.items),
			kineticAxe = (ItemAxeLC)new ItemKineticAxe(kineticMat).setRegistryName("kineticAxe").setCreativeTab(LCCreativeTabs.items),
			kineticShovel = (ItemSpadeLC)new ItemKineticSpade(kineticMat).setRegistryName("kineticShovel").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic armor
			kineticHelm = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.HEAD).
					setRegistryName("kineticHelm").setCreativeTab(LCCreativeTabs.items),
			kineticChest = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.CHEST).
					setRegistryName("kineticChest").setCreativeTab(LCCreativeTabs.items),
			kineticLegs = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.LEGS).
					setRegistryName("kineticLegs").setCreativeTab(LCCreativeTabs.items),
			kineticBoots = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.FEET).
					setRegistryName("kineticBoots").setCreativeTab(LCCreativeTabs.items),
			
			// mystic tools
			mysticHammer = (ItemSwordLC)new ItemMysticHammer(mysticHammerMat).setRegistryName("mysticHammer").setCreativeTab(LCCreativeTabs.items),
			mysticSword = (ItemSwordLC)new ItemMysticSword(mysticMat).setRegistryName("mysticSword").setCreativeTab(LCCreativeTabs.items),
			mysticPick = (ItemPickaxeLC)new ItemMysticPick(mysticMat).setRegistryName("mysticPick").setCreativeTab(LCCreativeTabs.items),
			mysticAxe = (ItemAxeLC)new ItemMysticAxe(mysticMat).setRegistryName("mysticAxe").setCreativeTab(LCCreativeTabs.items),
			mysticShovel = (ItemSpadeLC)new ItemMysticSpade(mysticMat).setRegistryName("mysticShovel").setCreativeTab(LCCreativeTabs.items),
			mysticHoe = (ItemHoeLC)new ItemMysticHoe(mysticMat).setRegistryName("mysticHoe").setCreativeTab(LCCreativeTabs.items),
			
			// mystic armor
			mysticHelm = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.HEAD).
					setRegistryName("mysticHelm").setCreativeTab(LCCreativeTabs.items),
			mysticChest = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.CHEST).
					setRegistryName("mysticChest").setCreativeTab(LCCreativeTabs.items),
			mysticLegs = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.LEGS).
					setRegistryName("mysticLegs").setCreativeTab(LCCreativeTabs.items),
			mysticBoots = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.FEET).
					setRegistryName("mysticBoots").setCreativeTab(LCCreativeTabs.items)
		);
	}

	/** Register the items */
	private static void registerItems() {
		RegistryHelper.registerItems(items);
	}

	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		for(IRegistryItem item : items) item.registerRender();
	}

}
