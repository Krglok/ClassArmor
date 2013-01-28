package com.krglok.classarmor;

import java.text.MessageFormat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
//import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class PluginListener implements Listener {
    private final ClassArmor plugin;

    /**
     * This listener needs to know about the plugin which it came from
     * Events listen to : onInventory
     * 
     * @param plugin
     */
    public PluginListener(ClassArmor plugin) {
        // Register the listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    
    /*
     * Another example of a event handler. This one will give you the name of
     * the entity you interact with, if it is a Creature it will give you the
     * creature Id.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        final EntityType entityType = event.getRightClicked().getType();

        event.getPlayer().sendMessage(MessageFormat.format(
                "You interacted with a {0} it has an id of {1}",
                entityType.getName(),
                entityType.getTypeId()));
    }

	/**
	 * Checks armor slots on closing inventory
	 * 
	 * @param event
	 */
    @EventHandler
    public void onInventory(InventoryCloseEvent event)
    {
    	//InventoryType.PLAYER
        if (event.getView().getType() == InventoryType.CRAFTING) {
            plugin.checkArmor((Player) event.getPlayer());
        }
        //plugin.log.info("ArmorPerms : OnInvetory Type "+event.getView().getType().toString());
    }
    
    /**
     * Checks armor on Player Quit
     * 
     * @param event
     */
//    @EventHandler
//    public void onPlayerQuit(PlayerQuitEvent event)
//    {
//    	//plugin.checkArmor(event.getPlayer());
//    }
    
    /**
     * Checks Armor on every Player move !!
     * 
     * @param event
     */
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event)
//    {
//    	//plugin.checkArmor(event.getPlayer());
//    }

    
}
