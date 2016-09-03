package sblectric.lightningcraft.ref;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.FMLCommonHandler;

/** Some metal definitions */
public class Metal {

	// metal names
	public static final String NAME_IRON = "Iron";
	public static final String NAME_STEEL = "Steel";
	public static final String NAME_LEAD = "Lead";
	public static final String NAME_TIN = "Tin";
	public static final String NAME_ALUM = "Aluminum";
	public static final String NAME_GOLD = "Gold";
	public static final String NAME_COPPER = "Copper";
	public static final String NAME_ELEC = "Electricium";
	public static final String NAME_SKY = "Skyfather";
	public static final String NAME_MYSTIC = "Mystic";
	private static final String NAME_DEFAULT = "[none]";

	/** Gets all valid metal names */
	public static final List<String> getAllNames() {
		ArrayList<String> names = new ArrayList();
		try {
			for(Field f : Metal.class.getFields()) {
				if(f.getName().startsWith("NAME_")) {
					names.add((String)f.get(null));
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	// metal colors
	public static final int COLOR_IRON = new Color(197,197,197).getRGB();
	public static final int COLOR_STEEL = new Color(133,133,133).getRGB();
	public static final int COLOR_LEAD = new Color(98,111,119).getRGB();
	public static final int COLOR_TIN = new Color(216,213,203).getRGB();
	public static final int COLOR_ALUM = new Color(227,227,227).getRGB();
	public static final int COLOR_GOLD = new Color(232,206,95).getRGB();
	public static final int COLOR_COPPER = new Color(234,134,46).getRGB();
	public static final int COLOR_ELEC = new Color(179,255,255).getRGB();
	public static final int COLOR_SKY = new Color(44,44,44).getRGB();
	public static final int COLOR_MYSTIC = new Color(174,43,30).getRGB();
	private static final int COLOR_DEFAULT = Color.WHITE.getRGB();

	/** Base metal definition */
	private static class Base {}

	/** Ingot metadata definitions */
	public static class Ingot extends Base {
		public static final int count = 3;
		public static final int ELEC = 0;
		public static final int SKY = 1;
		public static final int MYSTIC = 2;

		/** Get the metal's name from its metadata */
		public static String getNameFromMeta(int meta) {
			switch(meta) {
			case ELEC: return NAME_ELEC;
			case SKY: return NAME_SKY;
			case MYSTIC: return NAME_MYSTIC;
			default: return NAME_DEFAULT;
			}
		}

		/** Get the full oredict name */
		public static String getIngotFromMeta(int meta) {
			return "ingot" + getNameFromMeta(meta);
		}

		/** Gets the metal color from its metadata */
		public static int getColorFromMeta(int meta) {
			switch(meta) {
			case ELEC: return COLOR_ELEC;
			case SKY: return COLOR_SKY;
			case MYSTIC: return COLOR_MYSTIC;
			default: return COLOR_DEFAULT;
			}
		}
	}

	/** Block metadata definitions */
	public static class MBlock extends Ingot {
		/** Get the full oredict name */
		public static String getBlockFromMeta(int meta) {
			return "block" + getNameFromMeta(meta);
		}
	}

	/** Nugget metadata definitions */
	public static class Nugget extends Ingot {

		/** Get the full oredict name */
		public static String getNuggetFromMeta(int meta) {
			return "nugget" + getNameFromMeta(meta);
		}
	}

	/** Dust metadata definitions */
	public static class Dust extends Ingot {
		/** Get the full oredict name */
		public static String getDustFromMeta(int meta) {
			return "dust" + getNameFromMeta(meta);
		}
	}

	/** Rod metadata definitions */
	public static class Rod extends Base {
		public static final int count = 10;
		public static final int IRON = 0;
		public static final int STEEL = 1;
		public static final int LEAD = 2;
		public static final int TIN = 3;
		public static final int ALUM = 4;
		public static final int GOLD = 5;
		public static final int COPPER = 6;
		public static final int ELEC = 7;
		public static final int SKY = 8;
		public static final int MYSTIC = 9;

		/** Get the metal's name from its metadata */
		public static String getNameFromMeta(int meta) {
			switch(meta) {
			case IRON: return NAME_IRON;
			case STEEL: return NAME_STEEL;
			case LEAD: return NAME_LEAD;
			case TIN: return NAME_TIN;
			case ALUM: return NAME_ALUM;
			case GOLD: return NAME_GOLD;
			case COPPER: return NAME_COPPER;
			case ELEC: return NAME_ELEC;
			case SKY: return NAME_SKY;
			case MYSTIC: return NAME_MYSTIC;
			default: return NAME_DEFAULT;
			}
		}

		/** Gets the oredict name of the ingot that makes this part */
		public static String getIngotFromMeta(int meta) {
			return "ingot" + getNameFromMeta(meta);
		}

		/** Get the full oredict name */
		public static String getRodFromMeta(int meta) {
			return "rod" + getNameFromMeta(meta);
		}

		/** Gets the metal color from its metadata */
		public static int getColorFromMeta(int meta) {
			switch(meta) {
			case IRON: return COLOR_IRON;
			case STEEL: return COLOR_STEEL;
			case LEAD: return COLOR_LEAD;
			case TIN: return COLOR_TIN;
			case ALUM: return COLOR_ALUM;
			case GOLD: return COLOR_GOLD;
			case COPPER: return COLOR_COPPER;
			case ELEC: return COLOR_ELEC;
			case SKY: return COLOR_SKY;
			case MYSTIC: return COLOR_MYSTIC;
			default: return COLOR_DEFAULT;
			}
		}
	}

	/** Plate metadata definitions */
	public static class Plate extends Rod {
		/** Get the full oredict name */
		public static String getPlateFromMeta(int meta) {
			return "plate" + getNameFromMeta(meta);
		}
	}

}
