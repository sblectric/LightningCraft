package sblectric.lightningcraft.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/** Custom config class that supports default ints without ranges */
public class ConfigurationCustom extends Configuration {

	public ConfigurationCustom(File f) {
		super(f);
	}
	
    /**
     * Creates a integer property.
     *
     * @param name Name of the property.
     * @param category Category of the property.
     * @param defaultValue Default value of the property.
     * @param comment A brief description what the property does.
     * @return The value of the new integer property.
     */
    public int getInt(String name, String category, int defaultValue, String comment) {
        return getInt(name, category, defaultValue, comment, name);
    }

    /**
     * Creates a integer property.
     *
     * @param name Name of the property.
     * @param category Category of the property.
     * @param defaultValue Default value of the property.
     * @param comment A brief description what the property does.
     * @param langKey A language key used for localization of GUIs
     * @return The value of the new integer property.
     */
    public int getInt(String name, String category, int defaultValue, String comment, String langKey) {
        Property prop = this.get(category, name, defaultValue);
        prop.setLanguageKey(langKey);
        prop.setComment(comment + " [default: " + defaultValue + "]");
        return prop.getInt(defaultValue);
    }

}
