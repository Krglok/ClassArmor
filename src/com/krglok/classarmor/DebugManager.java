package com.krglok.classarmor;

import java.util.logging.Level;

/*
 * Debug manager ArmorPerms
 * 
 * author: slipcor
 * 
 * version: v0.2.0.1 - 
 * 
 * history:
 *
 *     v0.2.0.0 - 
 *     v0.2.0.1 - copied for his project by Krglok
 */

public class DebugManager {
	public boolean active;
	public final ClassArmor plugin;

	public DebugManager(ClassArmor plugin) {
		this.plugin = plugin;
		active = false;
	}

	/*
	 * info log
	 */
	public void info(String s) {
		if (!active)
			return;
		plugin.log("[DEBUG]" + s, Level.INFO);
	}

	/*
	 * warning log
	 */
	public void warning(String s) {
		if (!active)
			return;
		plugin.log("[DEBUG]" + s, Level.WARNING);
	}

	/*
	 * severe log
	 */
	public void server(String s) {
		if (!active)
			return;
		plugin.log("[DEBUG]" + s, Level.SEVERE);
	}
}
