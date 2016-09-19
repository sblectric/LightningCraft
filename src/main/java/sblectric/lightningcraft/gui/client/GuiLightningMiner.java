package sblectric.lightningcraft.gui.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import sblectric.lightningcraft.gui.server.ContainerLightningMiner;
import sblectric.lightningcraft.ref.LCText;
import sblectric.lightningcraft.ref.RefStrings;
import sblectric.lightningcraft.tiles.TileEntityLightningMiner;

/** Miner GUI */
@SideOnly(Side.CLIENT)
public class GuiLightningMiner extends GuiContainer {
	
	public static final ResourceLocation minerTx = new ResourceLocation(RefStrings.MODID, "textures/gui/container/lpminer.png");
	private TileEntityLightningMiner tile;
	private GuiButton modeBtn; private int modeButtonX;
	private GuiButton replaceBtn; private int replaceButtonX;

	public GuiLightningMiner(InventoryPlayer invPlayer, TileEntityLightningMiner tile) {
		super(new ContainerLightningMiner(invPlayer, tile));
		this.tile = tile;
		this.ySize = 198;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        int buttonWidth;
        
        // mode toggle button
        buttonWidth = 77;
        modeButtonX = 8;
        modeBtn = new GuiButton(0, k + modeButtonX, l + 54, buttonWidth, 20, "");
		buttonList.add(modeBtn);
		
        // replace toggle button
        buttonWidth = 77;
        replaceButtonX = this.xSize - modeButtonX - buttonWidth;
        replaceBtn = new GuiButton(1, k + replaceButtonX, l + 54, buttonWidth, 20, "");
		buttonList.add(replaceBtn);
	}
	
	@Override
	protected void actionPerformed(GuiButton parButton) {
		if(parButton != null) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, parButton.id);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		
		// titles
		String string = this.tile.getName();
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
		
		// operating mode
		this.fontRendererObj.drawString("Operating Mode", modeButtonX, modeBtn.yPosition - 9 - this.guiTop, 4210752);
		modeBtn.displayString = tile.mode.getName();
		
		// replace blocks?
		this.fontRendererObj.drawString("Replace Blocks?", replaceButtonX, replaceBtn.yPosition - 9 - this.guiTop, 4210752);
		replaceBtn.displayString = tile.replaceBlocks ? "Yes" : "No";

		// LP status
		int colorLP = 4210752;
		int colorXP = colorLP;
		
		String needLP;
		double power = tile.getActualNeededPower(tile.mode.getCost());
		
		if(power > 0) {
			needLP = LCText.df.format(power) + " LE needed";
		} else {
			needLP = "No LE source found";
		}
		String have = tile.cellPower + " LE available";
		
		if(tile.cellPower < power || tile.cellPower == 0) colorLP = 0xC00000;
		
		// draw the needs of the operation
		this.fontRendererObj.drawString(needLP, this.xSize - this.fontRendererObj.getStringWidth(needLP) - 8, 85, colorLP);
		this.fontRendererObj.drawString(have, this.xSize - this.fontRendererObj.getStringWidth(have) - 8, 95, colorLP);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(minerTx);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}