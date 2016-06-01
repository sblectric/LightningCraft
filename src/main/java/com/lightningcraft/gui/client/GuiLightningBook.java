package com.lightningcraft.gui.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.lightningcraft.recipes.LightningInfusionRecipes;
import com.lightningcraft.recipes.LightningInfusionRecipes.LightningInfusionRecipe;
import com.lightningcraft.ref.LCText;
import com.lightningcraft.ref.RefStrings;
import com.lightningcraft.util.JointList;
import com.lightningcraft.util.LCMisc;
import com.lightningcraft.util.StackHelper;

/** The Lightning Guide GUI! */
@SideOnly(Side.CLIENT)
public class GuiLightningBook extends GuiScreen {
	private final int bookImageHeight = 192;
	private final int bookImageWidth = 192;
	private int currPage = 0;
	private int indexPage, recipePage;
	private int bookTotalPages;
	private static ResourceLocation bookCoverTexture = new ResourceLocation(RefStrings.MODID, "textures/gui/lightning_book_cover.png");
	private static ResourceLocation bookMainTexture = new ResourceLocation(RefStrings.MODID, "textures/gui/lightning_book.png");
	private static ResourceLocation bookRecipeTexture = new ResourceLocation(RefStrings.MODID, "textures/gui/lightning_book_recipes.png");
	private LinkedList<String> pages = new LinkedList<String>();
	private LinkedList<RecipePage> recipePages = new LinkedList<RecipePage>();

	private GuiButton buttonDone;
	private NextPageButton buttonNextPage, buttonLastPage;
	private NextPageButton buttonPreviousPage, buttonFirstPage;
	private GuiButton level1Ref; private int pageLevel1 = -1;
	private GuiButton level2Ref; private int pageLevel2 = -1;
	private GuiButton level3Ref; private int pageLevel3 = -1;
	private GuiButton indexRef;
	private int level;

	private int mouseX;
	private int mouseY;

	public GuiLightningBook() {
		this(0);
	}

	public GuiLightningBook(int level)
	{

		// initialize it
		String edition = "";
		String S = LCText.secSign;
		switch(level) {
		case 0:
			break;
		case 1:
			edition = S + "4" + S + "lSKYFATHER EDITION";
			break;
		case 2:
			edition = S + "3" + S + "lUNDERWORLD EDITION";
			break;
		}

		// the pages
		pages.add("\n\n\n" + S + "9by SBlectric" + S + "0\n\n" + S + "oA Guide to the\nWorld of Lightning!" + S + "r\n\n" + edition);
		pages.add("It is said only the very brave or the very stupid mess with one of the most dangerous forces of nature.\n\nFor one to harness such raw power, one would need very specialized tools and machines.");
		pages.add("Lucky for you, you've discovered how to build these machines.");
		pages.add(S + "lAir Terminals" + S + "r - Also known as lightning rods. They act as preferential paths for lightning to take when it strikes the ground. Used to charge lightning cells and use LE.");
		pages.add(S + "lLightning Cells" + S + "r - These store energy from lightning strikes, also known as Lightning Energy (LE). To store the energy of a strike, an air terminal needs to be directly above the lightning cell.");
		pages.add(S + "lLightning Furnace" + S + "r - This is a furnace that uses LE instead of solid fuel to smelt items. A lightning furnace smelts items much quicker than a normal furnace, using one LE per 2 items burned.");
		pages.add(S + "lLightning Crusher" + S + "r - This is a machine that pulverizes items. With it, you can crush gravel into sand, ores to powder, and much more! Great for ore processing and a handy tool in general.");
		pages.add(S + "lLightning Infusion Table" + S + "r - The mighty Lightning Infusion Table takes the idea of using LE to create items to the next level. An item in the center slot gets infused with items in the outer slots along with some LE.");
		pages.add(S + "lElectrostatic Generator" + S + "r - This gives you a method of generating lightning, and by extension, LE by using blocks to generate a charge. When the charge is full (100C), lightning will strike. It does need a small amount of LE to run, though.");
		pages.add(S + "lWireless Power" + S + "r - Like Tesla, you've dreamt of wireless power. Today, you've discovered how to implement it. By creating a single transmitter from your core work area, you can add unlimited receivers nearby. To attune these receivers to the");
		pages.add("transmitter, you must craft a Tx/Rx tag, right click on the transmitter, then shift right-click on the receiver. These tags are reusable, and can be crafted back into an empty tag if you wish to mark a different transmitter location.");
		pages.add(S + "lEnchantment Reallocator" + S + "r - This can pull enchantments from an item and stick them on another. " + (level < 2 ? 
				"You hypothesize about making such a machine, but you lack the mystical items to do so. Perhaps the answer lies in another realm..." :
				"By using various mystical items, you can finally make such a device."));
		pages.add("Now that you know the machinery, you should know some of the items and blocks you can create.");
		pages.add(S + "lThunderstone" + S + "r - A mysterious dense stone birthed from the depths of the earth. It seems to have semi-liquid properties and resist wear and tear. Four can be made with 4 cobblestone, 4 quartz, and a block of obsidian.");
		pages.add(S + "lElectricium" + S + "r - Iron and gold are nice, but you've discovered a way to create a metal better than each of them. Electricium has impressive electral properties and is quite shocking! Can be made by infusing iron with gold, diamond and LE.");
		pages.add("Other Useful Items:\n\nThere are many useful things you can make with infusion, like diamonds from coal or sponges from wool and slimeballs. All infusion recipes can be found in the JEI and index.");
		pages.add("You've also discovered how to make new tools and armor.");
		pages.add(S + "lElectricium Tools" + S + "r - These tools are stronger, mine faster, and are more enchantable than vanilla tools, but are quite a bit more expensive to make. Made with electricium rods and ingots.");
		pages.add(S + "lElectricium Weapons" + S + "r - These weapons are quite powerful indeed. The sword and hammer have 9 and 10 attack damage respectively, and have latent electrical properties that can be brought out by the Hand of Thor enchantment.");
		pages.add(S + "lElectricium Armor" + S + "r - Offers slightly more protection than diamond armor and is more durable. Has latent electrical properties that can be brought out by the Electrostatic Aura enchantment.");
		pages.add(S + "lStonebound Swords" + S + "r - Stone doesn't channel LE very well, but by forcing LE through it, you can bind materials to it. You've discovered that infusing stone swords with several materials could have some interesting effects...");
		pages.add(S + "lMobile LE" + S + "r - Through your tinkering with electricium, you've discovered that, when combined with redstone, forms a primitive voltaic pile. By stabilizing this pile with iron rods and a comparator, you have managed to make a functional battery capable of storing a");
		pages.add("small amound of LE. You've learned that this battery can be charged from a lightning cell via a charging plate. Thus, when charged, this battery enables you to have a source of mobile LE when in your inventory. It may just come in handy!");
		pages.add(S + "lKinetics" + S + "r - You've discovered an interesting new field of research. It seems that by crafting a golden tool or piece of armor with redstone and an electricium plate somewhat similarly to making a battery, the electrical properties of the gold can be");
		pages.add("brought out. By providing these kinetic devices with a mobile source of raw power - lightning, perhaps? - you have found that the item's abilities are amplified many times over: a kinetic sword being over twice as powerful as a golden one, for example. Tools mining");
		pages.add("many times faster. Armor giving you invincibility. It seems that no feat is out of your reach now. You have even designed other trinkets that can use mobile LE to achieve neat functionalities.");
		if(!(level > 0)) {
			pages.add("Your mind turns to electricium. \"Sure, it's great!\" you say, but at the same time, you wonder if there is a material that offers more...");
			pages.add("What if... what if you were to infuse electricium... with thunderstone... and um... eyes of ender? Surely doing that wouldn't be taboo...!");
		} else { // add skyfather shard stuff
			pageLevel1 = pages.size();
			pages.add("Your intuition was right. The Skyfather ingot is powerful indeed. You can make divine tools and armor out of this new material. Tools made this way have special properties, while both tools and armor automatically repair themselves.");
			pages.add("You can also make LE cell upgrades by crafting a shard with four redstone.\nThe Skyfather Shard is useful, but the gods are taking notice of your mortal insolence. Demon Soldiers will now attack on sight.");
			pages.add("Perhaps even Skyfather stuff can be upgraded...");
		}

		if(level == 1) {
			pages.add("If you were to kill these demons, legend has it you can use their blood to forge a portal to the Underworld...");
		} else if(level > 1) {
			pageLevel2 = pages.size();
			pages.add("You've made your portal to the Underworld. What you see is a vast, dark, and damp cavern filled with spiders, demons, and more. It seems that it is so dark that night vision completely fails.");
			pages.add("There are a few naturally generated structures that exist in this evil dimension.");
			pages.add(S + "lWater Temples" + S + "r - These seemingly serene temples spawn on the bottom ocean of the Underworld.");
			pages.add(S + "lUnderworld Towers" + S + "r - These monolithic structures, found at nearly any height, are home to the Skeletal Guards. The top rooms have untold treasures. The guards drop their bones, which can be used to create high tier items.");
			pages.add(S + "lStalactite Ramparts" + S + "r - These garrisons spawn on the top of the Underworld, with cannons aimed at anyone foolish enough to attempt to breach their walls. However, these structures also contain divine treasures.");
			pages.add("Demons have been attacking you on sight for some time now. Well, you've made a potion that can do away with that. By infusing a water bottle with 2 demon blood and a 2 golden carrots, you've crafted a brew that keeps these demons at bay.");
			pages.add("If they're already chasing you down, it won't stop them, however, and it won't stop them from attacking you when you strike them. But it certainly is better than being attacked for like, no reason.");
		}

		pageLevel3 = pages.size();

		// now add the index
		addIndex();

		// total pages here
		this.bookTotalPages = pages.size();
		this.level = level;
	}

	/** Add the index (recipes and such) */
	protected void addIndex() {
		String S = LCText.secSign;
		int i = 0;

		// first, add a general page
		this.indexPage = pages.size();
		pages.add(S + "lIndex" + S + "r\nHere you can see all of the infusion recipes that you can make.");

		// now ready the recipe pages
		this.recipePage = pages.size();
		JointList<ItemStack> results = new JointList();
		JointList<Integer> costs = new JointList();

		// add each infusion to the index
		for(LightningInfusionRecipe recipe : LightningInfusionRecipes.instance().getRecipeList()) {
			ItemStack result = recipe.getOutput();
			List<String> items = recipe.getItems();
			JointList<String> ingredients = new JointList().join(items);
			ingredients.addFirst(recipe.getInfuseItem());
			int cost = recipe.getCost();
			pages.add(result.getDisplayName());
			recipePages.add(new RecipePage(this, result, ingredients, cost));
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonDone = new GuiButton(4, width / 2 - 49, 4 + bookImageHeight + (level + 1) * 24, 98, 20, I18n.format("gui.done", new Object[0]));
		level1Ref = new GuiButton(5, width / 2 - 80, 4 + bookImageHeight, 160, 20, "Skyfather Reference");
		level2Ref = new GuiButton(6, width / 2 - 80, 28 + bookImageHeight, 160, 20, "Underworld Reference");
		level3Ref = new GuiButton(7, width / 2 - 80, 52 + bookImageHeight, 160, 20, "Transcendental Reference");
		indexRef = new GuiButton(8, width / 2 - 49, 4 + bookImageHeight + level * 24, 98, 20, "Index");

		buttonList.add(buttonDone);
		if(level > 0) buttonList.add(level1Ref);
		if(level > 1) buttonList.add(level2Ref);
		if(level > 2) buttonList.add(level3Ref);
		buttonList.add(indexRef);
		int offsetFromScreenLeft = (width - bookImageWidth) / 2;
		int inOff = 20;
		buttonList.add(buttonNextPage = new NextPageButton(0, offsetFromScreenLeft + 120 - inOff, 156, true, false));
		buttonList.add(buttonPreviousPage = new NextPageButton(1, offsetFromScreenLeft + 38 + inOff, 156, false, false));
		buttonList.add(buttonLastPage = new NextPageButton(2, offsetFromScreenLeft + 145 - inOff, 156, true, true));
		buttonList.add(buttonFirstPage = new NextPageButton(3, offsetFromScreenLeft + 13 + inOff, 156, false, true));
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() 
	{
		buttonDone.visible = true; //(currPage == bookTotalPages - 1);
		buttonNextPage.visible = (currPage < bookTotalPages - 1);
		buttonPreviousPage.visible = currPage > 0;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
	{
		this.mouseX = mouseX;
		this.mouseY = mouseY;		

		Minecraft mc = Minecraft.getMinecraft();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (currPage == 0) {
			mc.getTextureManager().bindTexture(bookCoverTexture);
		}
		else if(currPage < recipePage) {
			mc.getTextureManager().bindTexture(bookMainTexture);
		}
		else {
			mc.getTextureManager().bindTexture(bookRecipeTexture);
		}

		// main window
		int offsetFromScreenLeft = (width - bookImageWidth ) / 2;
		drawTexturedModalRect(offsetFromScreenLeft, 2, 0, 0, bookImageWidth, bookImageHeight);

		// book pages
		int widthOfString;
		String stringPageIndicator = I18n.format("book.pageIndicator", new Object[] {Integer.valueOf(currPage + 1), bookTotalPages});
		widthOfString = fontRendererObj.getStringWidth(stringPageIndicator);
		fontRendererObj.drawString(stringPageIndicator, offsetFromScreenLeft - widthOfString + bookImageWidth - 44, 18, 0);
		fontRendererObj.drawSplitString(pages.get(currPage), offsetFromScreenLeft + 36, 34, 116, 0);

		// draw the superimplementation
		super.drawScreen(mouseX, mouseY, p_73863_3_);

		// draw the recipes
		if(currPage >= recipePage) {
			recipePages.get(currPage - recipePage).drawPage(offsetFromScreenLeft, 2);
		}
	}

	/**
	 * Called when a mouse button is pressed and the mouse is moved around. 
	 * Parameters are : mouseX, mouseY, lastButtonClicked & 
	 * timeSinceMouseClick.
	 */
	@Override
	protected void mouseClickMove(int parMouseX, int parMouseY, int parLastButtonClicked, long parTimeSinceMouseClick) 
	{

	}

	@Override
	protected void actionPerformed(GuiButton parButton) 
	{
		if (parButton == buttonDone)
		{
			// You can send a packet to server here if you need server to do 
			// something
			mc.displayGuiScreen((GuiScreen)null);
		}
		else if (parButton == buttonNextPage)
		{
			if (currPage < bookTotalPages - 1)
			{
				++currPage;
			}
		}
		else if (parButton == buttonPreviousPage)
		{
			if (currPage > 0)
			{
				--currPage;
			}
		}
		else if (parButton == buttonFirstPage)
		{
			currPage = 0;
		}
		else if (parButton == buttonLastPage)
		{
			currPage = bookTotalPages - 1;
		}
		else if (parButton == level1Ref)
		{
			currPage = pageLevel1;
		}
		else if (parButton == level2Ref)
		{
			currPage = pageLevel2;
		}
		else if (parButton == level3Ref)
		{
			currPage = pageLevel3;
		}
		else if (parButton == indexRef) {
			currPage = indexPage;
		}

	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat 
	 * events
	 */
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in 
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/** Draws the item stack */
	protected void drawItemStack(ItemStack stack, int x, int y) {
		if(stack != null && stack.getItem() != null) {
			GL11.glDisable(GL11.GL_LIGHTING);
			if(stack.getItem() instanceof ItemBlock) {
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				RenderHelper.enableGUIStandardItemLighting();
			}
			itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			String stackSize = stack.stackSize > 1 ? "" + stack.stackSize : "";
			itemRender.renderItemOverlayIntoGUI(fontRendererObj, stack, x, y, stackSize);
			RenderHelper.disableStandardItemLighting();
		}
	}

	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		if(stack != null && stack.getItem() != null) {
			super.renderToolTip(stack, x, y);
		}
	}

	@SideOnly(Side.CLIENT)
	static class RecipePage {

		private static final Random rand = new Random();
		private static final int maxMetaTicks = 1;
		private static final int maxRecTicks = 50;
		private double metaTicks = maxMetaTicks + 1;
		private double recTicks = maxRecTicks + 1;
		private long lasttime;
		private GuiLightningBook book;
		private ItemStack result;
		private List<String> ingredients;
		private int cost;
		private boolean ignoreDamage;
		private int[] itemRotate;
		private ItemStack[] displayStack;
		private int dispDamage = 0;
		private AxisAlignedBB resultBox;
		private LinkedList<AxisAlignedBB> ingredientBoxes;

		public RecipePage(GuiLightningBook book, ItemStack result, List<String> ingredients, int cost) {
			this.book = book;
			this.result = result.copy(); // copy to not overrwrite the recipe!!!
			this.ingredients = ingredients;
			this.cost = cost;
			this.ignoreDamage = false;
			this.lasttime = Minecraft.getSystemTime();
		}

		/** Draw the page */
		public void drawPage(int bookX, int bookY) {

			// show the needed LE
			book.fontRendererObj.drawSplitString(cost + " LE needed for this infusion", bookX + 36, 120, 116, 0);

			// some offset
			int xoff = 3;
			int yoff = -16;
			bookX += xoff;
			bookY += yoff;

			// ready display damages
			if(ignoreDamage && metaTicks >= maxMetaTicks) {
				dispDamage++;
				if(dispDamage > result.getMaxDamage()) {
					dispDamage = Math.min(1, result.getMaxDamage());
				}
				metaTicks = 0;
			}
			metaTicks += (Minecraft.getSystemTime() - lasttime) / 50D;

			// draw the result
			resultBox = new AxisAlignedBB(bookX + 120, bookY + 89, 0, bookX + 120 + 16, bookY + 89 + 16, 0);
			if(result.getMaxDamage() > 0) result.setItemDamage(dispDamage);
			book.drawItemStack(result, (int)resultBox.minX, (int)resultBox.minY);

			// draw the ingredients
			ingredientBoxes = new LinkedList<AxisAlignedBB>();
			ingredientBoxes.add(new AxisAlignedBB(bookX + 59, bookY + 89,  0, bookX + 59 + 16, bookY + 89 + 16,  0));
			ingredientBoxes.add(new AxisAlignedBB(bookX + 59, bookY + 69,  0, bookX + 59 + 16, bookY + 69 + 16,  0));
			ingredientBoxes.add(new AxisAlignedBB(bookX + 79, bookY + 89,  0, bookX + 79 + 16, bookY + 89 + 16,  0));
			ingredientBoxes.add(new AxisAlignedBB(bookX + 59, bookY + 109, 0, bookX + 59 + 16, bookY + 109 + 16, 0));
			ingredientBoxes.add(new AxisAlignedBB(bookX + 39, bookY + 89,  0, bookX + 39 + 16, bookY + 89 + 16,  0));

			// change the random slot position
			boolean change = recTicks >= maxRecTicks;
			Integer[] shuffle = new Integer[]{1, 2, 3, 4};
			if(change) {
				Collections.shuffle(Arrays.asList(shuffle));
				itemRotate = new int[ingredientBoxes.size()];
			}

			displayStack = new ItemStack[ingredientBoxes.size()];
			for(int i = 0; i < ingredientBoxes.size(); i++) {
				if(change) {
					if(i > 0) {
						itemRotate[i] = shuffle[i - 1];
					} else {
						itemRotate[i] = 0;
					}
					recTicks = 0;
				}
				ItemStack itemStack = StackHelper.animateItemStackFromString(ingredients.get(itemRotate[i]), change);
				if(itemStack == null) continue; // skip null items
				displayStack[i] = itemStack;
				if(displayStack[i].getMaxDamage() > 0) displayStack[i].setItemDamage(Math.min(displayStack[i].getMaxDamage(), dispDamage));
				book.drawItemStack(displayStack[i], (int)ingredientBoxes.get(i).minX, (int)ingredientBoxes.get(i).minY);
			}
			recTicks += (Minecraft.getSystemTime() - lasttime) / 50D;
			lasttime = Minecraft.getSystemTime();

			// now draw the tooltips on mouseover
			drawTooltips();
		}

		/** Draw the tooltips on each item in the infusion matrix */
		public void drawTooltips() {
			// result tooltip
			if(LCMisc.mouseColl(book.mouseX, book.mouseY, resultBox)) book.renderToolTip(result, book.mouseX, book.mouseY);

			// ingredient tooltips
			for(int i = 0; i < ingredientBoxes.size(); i++) {
				if(LCMisc.mouseColl(book.mouseX, book.mouseY, ingredientBoxes.get(i)))
					book.renderToolTip(displayStack[i], book.mouseX, book.mouseY);
			}
		}

	}

	@SideOnly(Side.CLIENT)
	static class NextPageButton extends GuiButton {
		private final boolean isNextButton;
		private final boolean isTurbo;

		public NextPageButton(int parButtonId, int parPosX, int parPosY, boolean parIsNextButton, boolean parIsTurbo)
		{
			super(parButtonId, parPosX, parPosY, 23, 13, "");
			isNextButton = parIsNextButton;
			isTurbo = parIsTurbo;
		}

		/**
		 * Draws this button to the screen.
		 */
		@Override
		public void drawButton(Minecraft mc, int parX, int parY)
		{
			if (visible)
			{
				boolean isButtonPressed = (parX >= xPosition 
						&& parY >= yPosition 
						&& parX < xPosition + width 
						&& parY < yPosition + height);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(bookMainTexture);
				int textureX = isTurbo ? 46 : 0;
				int textureY = 192;

				if (isButtonPressed)
				{
					textureX += 23;
				}

				if (!isNextButton)
				{
					textureY += 13;
				}

				drawTexturedModalRect(xPosition, yPosition, 
						textureX, textureY, 
						23, 13);
			}
		}
	}


}
