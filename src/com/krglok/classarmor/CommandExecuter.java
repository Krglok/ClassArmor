package com.krglok.classarmor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author krglok
 *
 */
public class CommandExecuter implements CommandExecutor {
	private final ClassArmor plugin;

	public static String cmd1 = "classarmor";
	public static String cmd2 = "ap";
	
	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public CommandExecuter(ClassArmor plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 */
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		plugin.db.info(command.getName()+" " + args.toString());
		// check command always ewayable
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("perms")) {
				// execute list
				if (!(sender instanceof Player)) {
					sender.sendMessage("This command can only be run by a player.");
				} else {
					cmdPerms(sender);
					return true;
				}
				// end always
				return true;
			}
		}
		if ((sender.isOp())
				|| (sender.hasPermission("armorperms.admin") && args.length > 0)) {
			// /
			// this.plugin.getConfig().set("sample.message",
			// Joiner.on(' ').join(args));
			plugin.db.info("Command : armorperms.admin " + args.toString());
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("list")) {
					// execute list
					if (!(sender instanceof Player)) {
						sender.sendMessage("This command can only be run by a player.");
					} else {
						return cmdList(sender);
					}
				} else if (args[0].equalsIgnoreCase("messages")) {
					// execute Message
					if (!(sender instanceof Player)) {
						sender.sendMessage("This command can only be run by a player.");
					} else {
						return cmdMessage(sender);
					}
				} else if (args[0].equalsIgnoreCase("debug")) {
					// execute debug
					cmdDebug(sender);
					return true;
				} else if (args[0].equalsIgnoreCase("enable")) {
					// execute enable armor check
					return cmdEnable(sender);
				} else if (args[0].equalsIgnoreCase("disable")) {
					// execute disable armor check
					return cmdDisable(sender);
				}
			} else {
				// command without param
				cmdNone(sender, command);
			}
			return true;
		} else {
			// no permissions
			sender.sendMessage(ChatColor.RED
					+ "You dont have permissions to do that!");
			cmdNone(sender,command);
			return false;
		}
	}

	
	/**
	 * Printout Command text from plugin.yml
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdNone(CommandSender sender, Command command) {
		sender.sendMessage(ChatColor.YELLOW + "--------------------");
		sender.sendMessage(ChatColor.YELLOW + "ClassArmor Vers.: "
				+ plugin.getDescription().getVersion());
		if (plugin.db.active) {
			sender.sendMessage(ChatColor.RED+"Debug Mode true");
		} else {
			sender.sendMessage(ChatColor.YELLOW+"Debug mode false");
		}
		if (!plugin.configMgr.checkEnable) {
			sender.sendMessage(ChatColor.RED+"Check Disabled");
		} else {
			sender.sendMessage(ChatColor.YELLOW+"Check enabled");
		}
		if (command.getName().equalsIgnoreCase("armorperms")) {
			sender.sendMessage(ChatColor.GRAY
					+ plugin.getCommand("armorperms").getDescription());
			sender.sendMessage(ChatColor.GRAY
					+ plugin.getCommand("armorperms").getUsage());
		} else {
			sender.sendMessage(ChatColor.GRAY
					+ plugin.getCommand("ap").getDescription());
			sender.sendMessage(ChatColor.GRAY
					+ plugin.getCommand("ap").getUsage());
			
		}
		return false;
	}

	/**
	 * printout the Armor Config
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdList(CommandSender sender) {

		sender.sendMessage(ChatColor.YELLOW
				+ "ClassArmor------------------------------------");
		sender.sendMessage("ArmorTypeList"+ChatColor.YELLOW
				+ plugin.configMgr.armorTyp.toString());
		sender.sendMessage("MaterialTypeList"+ChatColor.YELLOW
				+ plugin.configMgr.materialTyp.toString());

		return true;
	}

	/**
	 * printout the Message in Config
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdMessage(CommandSender sender) {

		sender.sendMessage(ChatColor.YELLOW
				+ "ArmorPerms------------------------------------");
		sender.sendMessage(ChatColor.YELLOW + "Messages");
		sender.sendMessage("noLeather " + ChatColor.YELLOW
				+ plugin.configMgr.noMaterial);
		sender.sendMessage("messageType " + ChatColor.YELLOW + plugin.configMgr.messageType);

		return true;
	}

	/**
	 * Enable Flag Debug mode , toggle the mode
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdDebug(CommandSender sender) {

		if (plugin.configMgr.debugmode) {
			// debug mode off
			plugin.configMgr.debugmode = false;
			plugin.db.active = false;
			sender.sendMessage(ChatColor.GREEN + "ArmorPerms debug disabled");
		} else {
			// debug mode on
			plugin.configMgr.debugmode = true;
			plugin.db.active = true;
			sender.sendMessage(ChatColor.GREEN + "ArmorPerms debug enabled");
		}
		return true;
	}

	/**
	 * Enable Flag Armor Check, no armor check
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdEnable(CommandSender sender) {
		plugin.configMgr.checkEnable = true;
		sender.sendMessage(ChatColor.GREEN + "ArmorPerms armorcheck enabled");

		return true;
	}

	/**
	 * Disable Flag Armor Check, armor will be do
	 * 
	 * @param sender
	 * @return
	 */
	private boolean cmdDisable(CommandSender sender) {
		plugin.configMgr.checkEnable = false;
		sender.sendMessage(ChatColor.GREEN + "ArmorPerms armorcheck disabled");

		return true;
	}

	private boolean cmdPerms(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + "----------------------------");
		sender.sendMessage(ChatColor.YELLOW + "Armor Permissions ");
		if ((sender instanceof Player)) {
			Player player = (Player) sender;
			if (player.hasPermission("armorpers.chainmail")) {
				sender.sendMessage(ChatColor.GREEN + "armorpers.chainmail : true ");
			}
			if (player.hasPermission("armorperms.diamond")) {
				sender.sendMessage(ChatColor.GREEN + " : true ");
			}
			if (player.hasPermission("armorperms.iron")) {
				sender.sendMessage(ChatColor.GREEN + "armorperms.diamond : true ");
			}
			if (player.hasPermission("armorperms.gold")) {
				sender.sendMessage(ChatColor.GREEN + "armorperms.gold : true ");
			}
			if (player.hasPermission("armorperms.leather")) {
				sender.sendMessage(ChatColor.GREEN + "armorperms.leather : true ");
			}
			return true;
		}
		sender.sendMessage(ChatColor.RED + "NOT a Player ");
		return true;
	}
	
}
