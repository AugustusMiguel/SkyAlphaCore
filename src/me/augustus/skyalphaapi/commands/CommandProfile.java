package me.augustus.skyalphaapi.commands;

import me.augustus.skyalphaapi.utils.PlayerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandProfile implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {


        if (cmd.getName().equalsIgnoreCase("profile")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) return true;
                Player p = (Player)sender;

                p.sendMessage("§3§m---------------------§b§lPROFILE§3§m-----------------------");
                p.sendMessage("");
                p.sendMessage("§7Player: §c" + p.getName());
                p.sendMessage("§7UUID: §c" + p.getUniqueId().toString());
                p.sendMessage("§7Is premium account: " + String.valueOf(PlayerInfo.isPremium(p)));
                p.sendMessage("§7Experience: §c");

            }
        }
        return false;
    }
}
