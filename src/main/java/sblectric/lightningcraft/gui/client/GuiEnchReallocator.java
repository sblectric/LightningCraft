package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerEnchReallocator;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityEnchReallocator;

/** Reallocation station GUI */
@SideOnly(Side.CLIENT)
public class GuiEnchReallocator extends GuiContainerLC {
	
	public static final ResourceLocation reallocGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpenchantmentreallocator.png");
	private TileEntityEnchReallocator tileRealloc;
	private GuiButton buttonAction;

	public GuiEnchReallocator(InventoryPlayer invPlayer, TileEntityEnchReallocator tile) {
		super(new ContainerEnchReallocator(invPlayer, tile));
		this.tileRealloc = tile;
		this.ySize = 198;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		buttonAction = new GuiButton(0, k + this.xSize - 64, l + 82, 60, 20, "");
		buttonAction.visible = false;
		buttonList.add(buttonAction);
	}
	
	@Override
	protected void actionPerformed(GuiButton parButton)
	{
		if(parButton == buttonAction) {
			if(!(tileRealloc.reallocCookTime > 0)) {
				// start the enchanting
				this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 1);
			} else {
				// cancel the enchanting
				this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, -1);
			}
		}
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		
		if(tileRealloc.reallocCookTime > 0) {
			buttonAction.displayString = "Cancel";
		} else {
			buttonAction.displayString = "Start";
		}
		
		if(tileRealloc.cellPower < tileRealloc.lpCost || tileRealloc.cellPower == 0 || 
				mc.player.experienceLevel < tileRealloc.xpCost || tileRealloc.lpCost <= 0 || tileRealloc.nTopEnchs <= 0) {
			buttonAction.visible = false;
		} else {
			buttonAction.visible = true;
		}
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		String string = this.tileRealloc.getName();
		this.fontRenderer.drawString(string, this.xSize / 2 - this.fontRenderer.getStringWidth(string) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);

		// LP status
		int colorLP = 4210752;
		int colorXP = colorLP;
		
		String needLP;
		String needXP;
		double power = tileRealloc.getActualNeededPower(tileRealloc.lpCost);
		
		if(power > 0) {
			needLP = LCText.df.format(power) + " LE needed";
			needXP = tileRealloc.xpCost + " XP needed";
		} else {
			needLP = "No item found";
			needXP = "";
		}
		String have = tileRealloc.cellPower + " LE available";
		
		String enchs, enchs2;
		if(tileRealloc.nTopEnchs > 0) {
			enchs = "Will reallocate";
			enchs2 = tileRealloc.nTopEnchs + (tileRealloc.nTopEnchs > 1 ? " enchantments" : " enchantment");
		} else {
			enchs = "No enchantments";
			enchs2 = "available";
		}
		
		if(tileRealloc.cellPower < power || tileRealloc.cellPower == 0) colorLP = 0xC00000;
		if(mc.player.experienceLevel < tileRealloc.xpCost) colorXP = 0xC00000;
		
		// draw the active enchantments
		this.fontRenderer.drawString(enchs, 60, 20, 4210752);
		this.fontRenderer.drawString(enchs2, 60, 30, 4210752);
		
		// draw the needs of the operation
		this.fontRenderer.drawString(needXP, 60, 50, colorXP);
		this.fontRenderer.drawString(needLP, 60, 60, colorLP);
		this.fontRenderer.drawString(have, 60, 70, colorLP);
		
		// just update the player currently using it
		if(tileRealloc.player != this.mc.player) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 0);
			tileRealloc.player = this.mc.player;
		}
	}

	@Override
	protected void drawBackground() {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(reallocGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	        int i1;
	        i1 = this.tileRealloc.getReallocProgressScaled(13);
	        this.drawTexturedModalRect(k + 35, l + 37, 176, 0, 15, i1 + 1);
	}

}