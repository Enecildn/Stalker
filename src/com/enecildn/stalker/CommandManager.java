package com.enecildn.stalker;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor
{
	private static HashMap<String, Player> OnlinePlayer = Stalker.OnlinePlayer;
	private static final String PREFIX = ChatColor.WHITE + "[" + ChatColor.GREEN + "Stalker" + ChatColor.WHITE + "] " + ChatColor.RESET;
	private static ArrayList<Player> StalkingDisabled = new ArrayList<Player>();
	
	public CommandManager(Stalker stk)
	{
		stk.getCommand("stk").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] data)
	{
		if (label.equalsIgnoreCase("stk"))
		{
			if (data.length == 0)
			{
				sender.sendMessage(PREFIX + ChatColor.AQUA + "/stk [player]" + ChatColor.GOLD + " - Gets location of [player].");
				sender.sendMessage(PREFIX + ChatColor.AQUA + "/stk [true/false]" + ChatColor.GOLD + " - Toggles wether other players can get own location or not");
				if (sender.isOp())
				{
					sender.sendMessage(PREFIX + ChatColor.AQUA + "/stk [player] [true/false]" + ChatColor.GOLD + " - Toggles wether other players can get location of [player] or not");
				}
			}
			else if (data.length == 1)
			{
				if (!data[0].equalsIgnoreCase("true") && !data[0].equalsIgnoreCase("false"))
				{
					if (!StalkingDisabled.contains(sender))
					{
						if (OnlinePlayer.containsKey(data[0]))
						{
							Player player = OnlinePlayer.get(data[0]);
							if (!StalkingDisabled.contains(player))
							{
								Location location = player.getLocation();
								double x = Math.round(location.getX());
								double y = Math.round(location.getY());
								double z = Math.round(location.getZ());
								String coordinates = "x " + x + " y " + y + " z " + z;
								sender.sendMessage(PREFIX + player.getName() + " is located at " + coordinates);
							}
							else
							{
								sender.sendMessage(PREFIX + ChatColor.RED + player.getName() + " does not allow other players to get location.");
							}
						}
						else
						{
							sender.sendMessage(PREFIX + ChatColor.RED + data[0] + " is not in the server.");
						}
					}
					else
					{
						sender.sendMessage(PREFIX + ChatColor.RED + "You cannot get locations of others when you do not allow other players to get your location.");
					}
				}
				else
				{
					if (sender instanceof Player)
					{
						if (data[0].equalsIgnoreCase("true"))
						{
							stalkingStatus(sender, (Player) sender, true);
						}
						else
						{
							stalkingStatus(sender, (Player) sender, false);
						}
					}
					else
					{
						sender.sendMessage(PREFIX + ChatColor.RED + "Only players can run this command.");
					}
				}
			}
			else if (data.length == 2)
			{
				if (sender.isOp())
				{
					if (OnlinePlayer.containsKey(data[0]))
					{
						Player player = OnlinePlayer.get(data[0]);
						if (data[1].equalsIgnoreCase("true") || data[1].equalsIgnoreCase("false"))
						{
							if (data[1].equalsIgnoreCase("true"))
							{
								stalkingStatus(sender, player, true);
							}
							else
							{
								stalkingStatus(sender, player, false);
							}
						}
						else
						{
							sender.sendMessage(PREFIX + ChatColor.AQUA + "/stk [player] [true/false]");
						}
					}
				}
				else
				{
					sender.sendMessage(PREFIX + ChatColor.RED + "You do not have permission to run this command.");
				}
			}
			return true;
		}
		return false;
	}
	
	private static void stalkingStatus(CommandSender sender, Player player, boolean status)
	{
		if (sender.isOp())
		{
			if (status)
			{
				if (!StalkingDisabled.contains(player))
				{
					sender.sendMessage(PREFIX + ChatColor.RED + "Stalking " + player.getName() + " is already enabled.");
				}
				else
				{
					StalkingDisabled.remove(player);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Enabled stalking " + player.getName() + ".");
				}
			}
			else
			{
				if (StalkingDisabled.contains(player))
				{
					sender.sendMessage(PREFIX + ChatColor.RED + "Stalking " + player.getName() + " is already disabled.");
				}
				else
				{
					StalkingDisabled.add(player);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Disabled stalking " + player.getName() + ".");
				}
			}
		}
		else
		{
			if (status == true)
			{
				if (!StalkingDisabled.contains(player))
				{
					sender.sendMessage(PREFIX + ChatColor.RED + "Stalking is already enabled.");
				}
				else
				{
					StalkingDisabled.remove(player);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Enabled stalking.");
				}
			}
			else
			{
				if (StalkingDisabled.contains(player))
				{
					sender.sendMessage(PREFIX + ChatColor.RED + "Stalking is already disabled.");
				}
				else
				{
					StalkingDisabled.add(player);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Disabled stalking.");
				}
			}
		}
	}
}
