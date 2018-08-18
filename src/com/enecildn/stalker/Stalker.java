package com.enecildn.stalker;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Stalker extends JavaPlugin implements CommandExecutor, Listener
{
	public static HashMap<String, Player> OnlinePlayer = new HashMap<String, Player>();
	
	public void onEnable()
	{
		getCommand("stk").setExecutor(new CommandManager(this));
		getServer().getPluginManager().registerEvents(this, this);
		OnlinePlayer.clear();
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			OnlinePlayer.put(player.getName(), player);
		}
	}
	
	public void onDisable()
	{
		OnlinePlayer.clear();
	}
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event)
	{
		OnlinePlayer.put(event.getPlayer().getName(), event.getPlayer());
	}
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event)
	{
		OnlinePlayer.remove(event.getPlayer().getName());
	}
}
