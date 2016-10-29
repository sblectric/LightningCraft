package sblectric.lightningcraft.integration.chisel;

import sblectric.lightningcraft.blocks.BlockLight;
import sblectric.lightningcraft.blocks.BlockStone;
import sblectric.lightningcraft.config.LCConfig;
import sblectric.lightningcraft.init.LCBlocks;
import sblectric.lightningcraft.ref.Log;
import team.chisel.api.carving.CarvingUtils;

/** The class to integrate LightningCraft with Chisel */
public class ChiselIntegration {

	/** Register the carving stuff */
	public static void mainRegistry() {
		try {
			// add new groups
			CarvingUtils.getChiselRegistry().addGroup(new CarvingGroup("thunder", new CarvingVariationList(
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.THUNDER),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.THUNDER_BRICK),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.THUNDER_BRICK_CHISELED),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.THUNDER_BRICK_FANCY),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.THUNDER_BRICK_FANCY_2)
					)));
			CarvingUtils.getChiselRegistry().addGroup(new CarvingGroup("demon", new CarvingVariationList(
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.DEMON),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.DEMON_BRICK),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.DEMON_BRICK_CHISELED),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.DEMON_BRICK_FANCY),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.DEMON_BRICK_FANCY_2)
					)));
			CarvingUtils.getChiselRegistry().addGroup(new CarvingGroup("under", new CarvingVariationList(
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.UNDER),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.UNDER_BRICK),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.UNDER_BRICK_CHISELED),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.UNDER_BRICK_FANCY),
					new CarvingVariation(LCBlocks.stoneBlock, BlockStone.UNDER_BRICK_FANCY_2)
					)));
			CarvingUtils.getChiselRegistry().addGroup(new CarvingGroup("lamp", new CarvingVariationList(
					new CarvingVariation(LCBlocks.lightBlock, BlockLight.UNDER_LAMP_FANCY),
					new CarvingVariation(LCBlocks.lightBlock, BlockLight.UNDER_LAMP_FANCY_2),
					new CarvingVariation(LCBlocks.lightBlock, BlockLight.UNDER_LAMP_FANCY_3)
					)));

			// add blocks to existing groups
			if(LCConfig.chiselCorruptStone) {
				CarvingUtils.getChiselRegistry().addVariation("stonebrick", new CarvingVariation(LCBlocks.corruptStone, 0));
			}

			Log.logger.info("Chisel integration complete.");
		} catch(Exception e) {
			Log.logger.error("Chisel integration failed!");
			e.printStackTrace();
		}
	}

}
