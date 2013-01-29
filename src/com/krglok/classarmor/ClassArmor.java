package com.krglok.classarmor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.krglok.classarmor.CommandExecuter;
import com.krglok.classarmor.PluginListener;
import com.krglok.classarmor.DebugManager;

import org.bukkit.Bukkit;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryCloseEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ClassArmor plugin class, has Commands and Listeners  
 *  
 * @version v4.0
 * 
 * @author krglok
 *
 *	use player.permissions for resolve permissions
 *  new commands implemmented
 *  
 * previous 
 * 		ArmorPerms plugin class the plugin is only a Listener, no Commands
 * 		author kdude63
 *         history: v3.2 - modified by krglok, new simple material check, import
 *         DebugManager, Console messages rename Main Class
 */

// public final class SampleBukkitPlugin extends JavaPlugin {
public final class ClassArmor extends JavaPlugin {
	public final DebugManager db = new DebugManager(this);
	public  ConfigManager configMgr;  // initialize after logger ready
//	public HandleVaultPermission perms = new HandleVaultPermission(this);
	
	Logger log = Logger.getLogger("Minecraft");
	

	/**
	 * Enable Plugin Read the Config File
	 */
	@Override
	public void onEnable() {
		// Create the PluginListener 
		new PluginListener(this);
		
		//  create ConfigManager
		configMgr = new ConfigManager(this);
		

		// set the command executor 
		this.getCommand(CommandExecuter.cmd1).setExecutor(new CommandExecuter(this));
		this.getCommand(CommandExecuter.cmd2).setExecutor(new CommandExecuter(this));

		log(" version " + this.getDescription().getVersion() + " ready",
				Level.INFO);
		db.info("Debug = "+configMgr.debugmode.toString());
	}

	/**
	 * Disable the Plugin
	 * 
	 */
	@Override
	public void onDisable() {
		// reset ConfigManager
		configMgr = null;
		log(" version " + this.getDescription().getVersion() + " disabled",
				Level.INFO);
	}

	/*  */
	/**
	 * send a message to server Console
	 * 
	 * @param message
	 * @param level
	 */
	public void log(String message, Level level) {
		Bukkit.getLogger().log(level, "[ArmorPerms] " + message);
	}

	/**
	 * Check if the player is trying to equip disallowed armor get the 4 armor
	 * inventory and check it against permissions - MaterialType - ArmorType -
	 * ArmorMaterialType and generate complex different messages
	 * 
	 * @param player
	 */
	public void checkArmor(Player player) {

		// armor check is disabled!!
		if (!configMgr.checkEnable) { return; }
		
		PlayerInventory pinv = player.getInventory();
		ItemStack helmet = pinv.getHelmet();
		ItemStack chestplate = pinv.getChestplate();
		ItemStack leggings = pinv.getLeggings();
		ItemStack boots = pinv.getBoots();

		// Krglok
		// check only for MaterialtypeList
		// db.info("ArmorPerms : Config messageType"+configMgr.messageType);
		if (configMgr.messageType.equals("1")) {
			// check material of every Armor in the ArmorTypeList
			// - HELMET
			// - CHESTPLATE
			// - LEGGINGS
			// - BOOTS

			db.info("ArmorPerms : Config armorType" + configMgr.armorTyp);
			db.info("ArmorPerms : Config materialType" + configMgr.materialTyp);
			if ((configMgr.armorTyp.contains("HELMET"))
					&& (!checkMaterial(player, helmet))) {
				player.getInventory().addItem(helmet);
//				player.getWorld().dropItem(player.getLocation(), helmet);
				pinv.setHelmet(new ItemStack(0));
				player.sendMessage(configMgr.noMaterial);
			}

			if ((configMgr.armorTyp.contains("CHESTPLATE"))
					&& (!checkMaterial(player, chestplate))) {
				player.getInventory().addItem(chestplate);
//				player.getWorld().dropItem(player.getLocation(), chestplate);
				pinv.setChestplate(new ItemStack(0));
				player.sendMessage(configMgr.noMaterial);
			}

			if ((configMgr.armorTyp.contains("LEGGINGS"))
					&& (!checkMaterial(player, leggings))) {
				player.getInventory().addItem(leggings);
//				player.getWorld().dropItem(player.getLocation(), leggings);
				pinv.setLeggings(new ItemStack(0));
				player.sendMessage(configMgr.noMaterial);
			}

			if ((configMgr.armorTyp.contains("BOOTS"))
					&& (!checkMaterial(player, boots))) {
				player.getInventory().addItem(boots);
//				player.getWorld().dropItem(player.getLocation(), boots);
				pinv.setBoots(new ItemStack(0));
				player.sendMessage(configMgr.noMaterial);
			}
		}

	}

	/**
	 * check for permission of player for the material permission
	 * armorperms.<materialName> uppercase
	 * 
	 * @param player
	 * @param armor
	 * @return boolean true is permission present or not relevant
	 */
	@SuppressWarnings("unused")
	private boolean checkMaterial(Player player, ItemStack armor) {
		// no item present
		if (armor == null ) { return true; }
		
		String material = "";
		// - DIAMOND
		if (armor.getType().name().contains("DIAMOND")) {
			material = "DIAMOND";
		}
		// - GOLD
		if (armor.getType().name().contains("GOLD")) {
			material = "GOLD";
		}
		// - CHAINMAIL
		if (armor.getType().name().contains("CHAINMAIL")) {
			material = "CHAINMAIL";
		}
		// - IRON
		if (armor.getType().name().contains("IRON")) {
			material = "IRON";
		}
		// - LEATHER
		if (armor.getType().name().contains("LEATHER")) {
			material = "LEATHER";
		}
		material = material.toLowerCase();
		db.info("search Permission for Material : " + material + " of "
				+ armor.getTypeId());
		if (player.isPermissionSet("armorperms." + material)) {
			db.info("Not Set Permission: armorperms." + material);
		}
		// no item present , do nothing
		if (armor != null) {
			// material not in MaterialTypeList, do nothing
			if (!configMgr.materialTyp.contains(material.toUpperCase())) {
				db.info("Not in MaterialType");
				return true;
			}
			// check has player material-permission
			if (player.hasPermission("armorperms." + material)) {
				db.info("Has permission " + "armorperms." + material);
				return true;
			} else {
				db.info("NO permission " + "armorperms." + material);
				return false;
			}
		}
		db.info("NO armor present");
		return true;
	}

}
