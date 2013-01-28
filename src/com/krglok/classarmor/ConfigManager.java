package com.krglok.classarmor;

import java.io.File;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 
 * @author krglok
 *
 */
public class ConfigManager {
	private final ClassArmor plugin;

	FileConfiguration config;

	public List<String> materialTyp;
	public List<String> armorTyp;
	public String messageType;
	public String colorCode;
	public String noMaterial;
	public Boolean debugmode;
	public Boolean checkEnable;

	public ConfigManager(ClassArmor refplugin) {
		this.plugin = refplugin;

		config = this.plugin.getConfig();
		// generate default configuration
		if (!new File(this.plugin.getDataFolder().getPath()
				+ File.separatorChar + "config.yml").exists())
			this.plugin.saveDefaultConfig();
		// read the configuration details
		materialTyp = config.getStringList("MaterialTyp");
		armorTyp = config.getStringList("ArmorTyp");
		noMaterial = config.getString("MessageMaterial");
		messageType = config.getString("MessageType");
		colorCode = config.getString("Color");
		debugmode = config.getBoolean("debug", false);
		plugin.db.active = this.debugmode;
		checkEnable = config.getBoolean("enable", true);

	}
}
