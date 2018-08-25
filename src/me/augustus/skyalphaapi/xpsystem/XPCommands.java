package me.augustus.skyalphaapi.xpsystem;

import me.augustus.skyalphaapi.tag.TAGMethods;
import me.augustus.skyalphaapi.tag.Tags;
import me.augustus.skyalphaapi.utils.ActionBar;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class XPCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("xp")) {
            if (args.length == 0) {
                p.sendMessage(XPMethods.xpprefix + String.valueOf(XPMethods.getXp(p)));
                ActionBar bar = new ActionBar(XPMethods.xpprefix + "XP to next level §a" + XPMethods.getXpToNextInt(p));
                bar.sendToPlayer(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("level")) {
                p.sendMessage(XPMethods.xpprefix + String.valueOf(XPMethods.getCurrentLevel(p)));
                return true;
            }

            if (args[0].equalsIgnoreCase("top")) {
                List<String> tops = XPMethods.getXpTops();
                p.sendMessage("§3§m---------------------§b§lTOP LIST§3§m-----------------------");
                for (String messages : tops) {
                    p.sendMessage(messages);
                }
                p.sendMessage("§3§m-----------------------------------------------------");
                return true;
            }

            if (TAGMethods.getTag(p) < Tags.admin) {
                p.sendMessage(CoreMethods.noperm);
                return true;
            }

            if (args.length < 3) {
                p.sendMessage(XPMethods.xpprefix + "Use: §c/xp (set) (player) (new xp level)");
            }

            if (args[0].equalsIgnoreCase("set")) {
                Player t = Bukkit.getPlayer(args[1]);
                Integer xpLevel = Integer.valueOf(args[2]);
                if (t == null) {
                    p.sendMessage(XPMethods.xpprefix + "This player does not exist or is offline.");
                    return true;
                }
                XPMethods.setXp(t, xpLevel);
            }
        }

        return false;
    }
}
