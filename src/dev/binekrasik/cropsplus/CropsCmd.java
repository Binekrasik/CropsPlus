package dev.binekrasik.cropsplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CropsCmd implements CommandExecutor {
	private CropsPlus plugin = CropsPlus.getPlugin(CropsPlus.class);
	private final String helpMessage = 
			"&6- - - - - &6&lCrops&e&l++ &8| &fCommands (1 of 1) &6- - - - -\n"+
			 "&6> &f/crops reload &e-> &fReloads the plugin.\n"+
			 "&6> &f/crops help &e[page] -> &fShows this list of commands\n"+
			 "&6- - - - - - - - - - - - - - - - - - - - - - - - - - -";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("crops")) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("cropsplus.reload") || sender.isOp()) {
						plugin.reloadConfig();
						sender.sendMessage(Utils.cc(Utils.prefix + "CropsPlus successfully reloaded."));
					} else {
						noPermission(sender);
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					if (args.length >= 2) {
						if (args[1].equalsIgnoreCase("1")) {
							sender.sendMessage(Utils.cc(helpMessage));
						}
					} else {
						sender.sendMessage(Utils.cc(helpMessage));
					}
				} else {
					sender.sendMessage(Utils.cc(Utils.prefix + "Invalid arguments. Try &e/crops help &ffor list of subcommands."));
				}
			} else {
				sender.sendMessage(Utils.cc(helpMessage));
			}
		} else {
			sender.sendMessage(Utils.cc("&cThat's impossible"));
		}
		return false;
	}
	
	private void noPermission(CommandSender sender) {
		sender.sendMessage(Utils.cc(Utils.prefix + "You don't have permissions to do this."));
	}

}
