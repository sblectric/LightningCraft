package sblectric.lightningcraft.gui;

/** A class to help with converting between ints and shorts for container transport */
public class ShortSender {
	
	/** Get the low 16 bits from an int */
	public static short getLowShort(int value) {
		return (short)(value >> 16);
	}
	
	/** Get the high 16 bits from an int */
	public static short getHighShort(int value) {
		return (short)(value & 0xFFFF);
	}
	
	/** Reconstruct an int from two shorts */
	public static int getInt(short lowShort, short highShort) {
		return (int)(lowShort << 16) | (highShort & 0xFFFF);
	}

}
