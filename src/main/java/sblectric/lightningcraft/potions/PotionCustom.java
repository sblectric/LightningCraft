package sblectric.lightningcraft.potions;

import net.minecraft.potion.Potion;

/** Custom potion effect */
public class PotionCustom extends Potion {

	public PotionCustom(String stringID, boolean isBad, int liquidColor) {
		super(isBad, liquidColor);
		this.setRegistryName(stringID);
		this.setIconIndex(8, 0); // just transparency here
	}
	
	@Override
    public Potion setIconIndex(int x, int y) {
        return super.setIconIndex(x, y);
    }

}
