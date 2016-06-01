package com.lightningcraft.items;

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

import com.lightningcraft.creativetabs.LCCreativeTabs;
import com.lightningcraft.ref.Material;
import com.lightningcraft.ref.Metal.Dust;
import com.lightningcraft.ref.Metal.Ingot;
import com.lightningcraft.ref.Metal.Nugget;
import com.lightningcraft.ref.Metal.Plate;
import com.lightningcraft.ref.Metal.Rod;
import com.lightningcraft.ref.RefStrings;
import com.lightningcraft.registry.IRegistryItem;
import com.lightningcraft.util.ArmorHelper;
import com.lightningcraft.util.JointList;

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
	public static ItemPotionLC potion;
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
			guide = (ItemLC)new ItemLightningGuide().setUnlocalizedName("guide").setCreativeTab(LCCreativeTabs.items),
			golfClub = (ItemLC)new ItemGolfClub().setUnlocalizedName("golfClub").setCreativeTab(LCCreativeTabs.items),
			golfClubGold = (ItemLC)new ItemGoldenClub().setUnlocalizedName("golfClubGold").setCreativeTab(LCCreativeTabs.items),
			ingot = (ItemLC)new ItemMetalIngot().setUnlocalizedName("ingot").setCreativeTab(LCCreativeTabs.items),
			nugget = (ItemLC)new ItemMetalNugget().setUnlocalizedName("nugget").setCreativeTab(LCCreativeTabs.items),
			dust = (ItemLC)new ItemMetalDust().setUnlocalizedName("dust").setCreativeTab(LCCreativeTabs.items),
			rod = (ItemLC)new ItemMetalRod().setUnlocalizedName("rod").setCreativeTab(LCCreativeTabs.items),
			plate = (ItemLC)new ItemMetalPlate().setUnlocalizedName("plate").setCreativeTab(LCCreativeTabs.items),
			material = (ItemLC)new ItemMaterial().setUnlocalizedName("material").setCreativeTab(LCCreativeTabs.items),
			battery = (ItemLC)new ItemBattery().setUnlocalizedName("battery").setCreativeTab(LCCreativeTabs.items),
			itemMagnet = (ItemLC)new ItemMagnet().setUnlocalizedName("itemMagnet").setCreativeTab(LCCreativeTabs.items),
			wirelessMarker = (ItemLC)new ItemWirelessMarker().setUnlocalizedName("wirelessMarker").setCreativeTab(LCCreativeTabs.items),
			
			// potion
			potion = (ItemPotionLC)new ItemPotionLC().setUnlocalizedName("potion").setCreativeTab(LCCreativeTabs.items),
			
			// electricium tools
			elecHammer = (ItemSwordLC)new ItemHammer(elecHammerMat).setUnlocalizedName("elecHammer").setCreativeTab(LCCreativeTabs.items),
			elecSword = (ItemSwordLC)new ItemChargedSword(elecMat).setUnlocalizedName("elecSword").setCreativeTab(LCCreativeTabs.items),
			elecPick = (ItemPickaxeLC)new ItemPickaxeLC(elecMat).setUnlocalizedName("elecPick").setCreativeTab(LCCreativeTabs.items),
			elecAxe = (ItemAxeLC)new ItemAxeLC(elecMat).setUnlocalizedName("elecAxe").setCreativeTab(LCCreativeTabs.items),
			elecShovel = (ItemSpadeLC)new ItemSpadeLC(elecMat).setUnlocalizedName("elecShovel").setCreativeTab(LCCreativeTabs.items),
			elecHoe = (ItemHoeLC)new ItemHoeLC(elecMat).setUnlocalizedName("elecHoe").setCreativeTab(LCCreativeTabs.items),
			
			// electricium armor
			elecHelm = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.HEAD).setUnlocalizedName("elecHelm").setCreativeTab(LCCreativeTabs.items),
			elecChest = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.CHEST).setUnlocalizedName("elecChest").setCreativeTab(LCCreativeTabs.items),
			elecLegs = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.LEGS).setUnlocalizedName("elecLegs").setCreativeTab(LCCreativeTabs.items),
			elecBoots = (ItemArmorLC)new ItemElecArmor(elecArmor, EntityEquipmentSlot.FEET).setUnlocalizedName("elecBoots").setCreativeTab(LCCreativeTabs.items),
			
			// special swords
			soulSword = (ItemSwordLC)new ItemSoulSword(soulMat).setUnlocalizedName("soulSword").setCreativeTab(LCCreativeTabs.items),
			zombieSword = (ItemSwordLC)new ItemZombieSword(zombieMat).setUnlocalizedName("zombieSword").setCreativeTab(LCCreativeTabs.items),
			featherSword = (ItemSwordLC)new ItemFeatherSword(featherMat).setUnlocalizedName("featherSword").setCreativeTab(LCCreativeTabs.items),
			enderSword = (ItemSwordLC)new ItemEnderSword(enderMat).setUnlocalizedName("enderSword").setCreativeTab(LCCreativeTabs.items),
			blazeSword = (ItemSwordLC)new ItemBlazeSword(blazeMat).setUnlocalizedName("blazeSword").setCreativeTab(LCCreativeTabs.items),
			iceSword = (ItemSwordLC)new ItemIceSword(iceMat).setUnlocalizedName("iceSword").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather tools
			skyHammer = (ItemSwordLC)new ItemSkyHammer(skyHammerMat).setUnlocalizedName("skyHammer").setCreativeTab(LCCreativeTabs.items),
			skySword = (ItemSwordLC)new ItemSkySword(skyMat).setUnlocalizedName("skySword").setCreativeTab(LCCreativeTabs.items),
			skyPick = (ItemPickaxeLC)new ItemSkyPick(skyMat).setUnlocalizedName("skyPick").setCreativeTab(LCCreativeTabs.items),
			skyAxe = (ItemAxeLC)new ItemSkyAxe(skyMat).setUnlocalizedName("skyAxe").setCreativeTab(LCCreativeTabs.items),
			skyShovel = (ItemSpadeLC)new ItemSkySpade(skyMat).setUnlocalizedName("skyShovel").setCreativeTab(LCCreativeTabs.items),
			skyHoe = (ItemHoeLC)new ItemSkyHoe(skyMat).setUnlocalizedName("skyHoe").setCreativeTab(LCCreativeTabs.items),
			
			// skyfather armor
			skyHelm = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.HEAD).setUnlocalizedName("skyHelm").setCreativeTab(LCCreativeTabs.items),
			skyChest = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.CHEST).setUnlocalizedName("skyChest").setCreativeTab(LCCreativeTabs.items),
			skyLegs = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.LEGS).setUnlocalizedName("skyLegs").setCreativeTab(LCCreativeTabs.items),
			skyBoots = (ItemArmorLC)new ItemSkyArmor(skyArmor, EntityEquipmentSlot.FEET).setUnlocalizedName("skyBoots").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic tools
			kineticSword = (ItemSwordLC)new ItemKineticSword(kineticMat).setUnlocalizedName("kineticSword").setCreativeTab(LCCreativeTabs.items),
			kineticPick = (ItemPickaxeLC)new ItemKineticPick(kineticMat).setUnlocalizedName("kineticPick").setCreativeTab(LCCreativeTabs.items),
			kineticAxe = (ItemAxeLC)new ItemKineticAxe(kineticMat).setUnlocalizedName("kineticAxe").setCreativeTab(LCCreativeTabs.items),
			kineticShovel = (ItemSpadeLC)new ItemKineticSpade(kineticMat).setUnlocalizedName("kineticShovel").setCreativeTab(LCCreativeTabs.items),
			
			// kinetic armor
			kineticHelm = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.HEAD).
					setUnlocalizedName("kineticHelm").setCreativeTab(LCCreativeTabs.items),
			kineticChest = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.CHEST).
					setUnlocalizedName("kineticChest").setCreativeTab(LCCreativeTabs.items),
			kineticLegs = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.LEGS).
					setUnlocalizedName("kineticLegs").setCreativeTab(LCCreativeTabs.items),
			kineticBoots = (ItemArmorLC)new ItemKineticArmor(kineticArmor, EntityEquipmentSlot.FEET).
					setUnlocalizedName("kineticBoots").setCreativeTab(LCCreativeTabs.items),
			
			// mystic tools
			mysticHammer = (ItemSwordLC)new ItemMysticHammer(mysticHammerMat).setUnlocalizedName("mysticHammer").setCreativeTab(LCCreativeTabs.items),
			mysticSword = (ItemSwordLC)new ItemMysticSword(mysticMat).setUnlocalizedName("mysticSword").setCreativeTab(LCCreativeTabs.items),
			mysticPick = (ItemPickaxeLC)new ItemMysticPick(mysticMat).setUnlocalizedName("mysticPick").setCreativeTab(LCCreativeTabs.items),
			mysticAxe = (ItemAxeLC)new ItemMysticAxe(mysticMat).setUnlocalizedName("mysticAxe").setCreativeTab(LCCreativeTabs.items),
			mysticShovel = (ItemSpadeLC)new ItemMysticSpade(mysticMat).setUnlocalizedName("mysticShovel").setCreativeTab(LCCreativeTabs.items),
			mysticHoe = (ItemHoeLC)new ItemMysticHoe(mysticMat).setUnlocalizedName("mysticHoe").setCreativeTab(LCCreativeTabs.items),
			
			// mystic armor
			mysticHelm = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.HEAD).
					setUnlocalizedName("mysticHelm").setCreativeTab(LCCreativeTabs.items),
			mysticChest = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.CHEST).
					setUnlocalizedName("mysticChest").setCreativeTab(LCCreativeTabs.items),
			mysticLegs = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.LEGS).
					setUnlocalizedName("mysticLegs").setCreativeTab(LCCreativeTabs.items),
			mysticBoots = (ItemArmorLC)new ItemMysticArmor(mysticArmor, EntityEquipmentSlot.FEET).
					setUnlocalizedName("mysticBoots").setCreativeTab(LCCreativeTabs.items)
		);
	}

	/** Register the items */
	private static void registerItems() {
		// iterate through them
		for(IRegistryItem item : items) {
			item.setRarity();
			GameRegistry.registerItem((Item)item, item.getShorthandName());
		}
	}

	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		// iterate through them
		for(IRegistryItem item : items) {
			item.registerRender(mesher);
		}
	}

}
