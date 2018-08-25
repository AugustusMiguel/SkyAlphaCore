package me.augustus.skyalphaapi.commands;

import me.augustus.skyalphaapi.tag.TAGMethods;
import me.augustus.skyalphaapi.tag.Tags;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLog implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        getLog(p);

        if (cmd.getName().equalsIgnoreCase("logmode")) {
            if (TAGMethods.getTag(p) != Tags.owner) {
                p.sendMessage(CoreMethods.noperm);
                return true;
            }

            if (isLog()) {
                setLog(false);
                CoreMethods.players.set(p.getName() + ".log", "false");
                CoreMethods.players.saveConfig();
                p.sendMessage(CoreMethods.prefix + "You leaved Log mode.");
                return true;
            }

            setLog(true);
            CoreMethods.players.set(p.getName() + ".log", "true");
            CoreMethods.players.saveConfig();
            p.sendMessage(CoreMethods.prefix + "You joined Log mode.");
        }
        return false;
    }

    private static boolean log;

    public static boolean isLog() {
        return log;
    }

    public static void setLog(boolean log) {
        log = log;
    }

    public static void getLog(Player p) {
        String l = CoreMethods.players.getString(p.getName() + ".log");
        if (l == "true") {
            log = true;
        }
        if (l == "false") {
            log = false;
        }
    }
}
