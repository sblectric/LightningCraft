package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerLightningCannon;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningCannon;

/** The Lightning Cannon gui */
@SideOnly(Side.CLIENT)
public class GuiLightningCannon extends GuiContainer {
	
	private static final ResourceLocation cannonGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpcannon.png");
	private TileEntityLightningCannon tileCannon;
	private GuiButton buttonAction;
	private int buttonActionX;

	public GuiLightningCannon(InventoryPlayer invPlayer, TileEntityLightningCannon tile) {
		super(new ContainerLightningCannon(invPlayer, tile));
		this.tileCannon = tile;
		this.ySize = 198;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        int buttonWidth;
        
        // mode toggle button
        buttonWidth = 150;
        buttonActionX = 10;
		buttonAction = new GuiButton(0, k + buttonActionX, l + 34, buttonWidth, 20, "");
		buttonList.add(buttonAction);
	}
	
	@Override
	protected void actionPerformed(GuiButton parButton) {
		if(parButton == buttonAction) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, 0);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// bar status
		int width = 125;
		int height = 16;
		int texty = 36;
		
		// titles
		String string = new ItemStack(LCBlocks.lightningCannon, 1, tileCannon.getBlockMetadata()).getDisplayName();
		this.fontRenderer.drawString(string, this.xSize / 2 - this.fontRenderer.getStringWidth(string) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
		
		// operating mode
		this.fontRenderer.drawString("Operating Mode", buttonActionX, 24, 4210752);
		buttonAction.displayString = tileCannon.mode.getName();
		
		// available LP
		int color = 4210752;
		Double power = tileCannon.getActualNeededPower(tileCannon.getAttackEnergy());
		String need = LCText.df.format(power) + " LE needed";
		String have = tileCannon.cellPower + " LE available";
		if(tileCannon.cellPower < power) color = 0xC00000;
		if(power != Double.NaN) this.fontRenderer.drawString(need, 10, 70, color);
		this.fontRenderer.drawString(have, 10, 80, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(cannonGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}