package sk.addon.noMobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && "reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("nomobs.reload")) {
                NoMobs.getInstance().reloadConfig();
                NoMobs.loadAllowedMobs();
                sender.sendMessage("§aConfig reloaded!");
                return true;
            } else {
                sender.sendMessage("§cYou don't have permission to use this command.");
                return false;
            }
        }
        return false;
    }
}
