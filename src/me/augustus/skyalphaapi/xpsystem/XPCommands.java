package me.augustus.skyalphaapi.xpsystem;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
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

import static me.augustus.skyalphaapi.xpsystem.XPMethods.xpprefix;

public class XPCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("xp")) {
            if (args.length == 0) {
                p.sendMessage(xpprefix + XPMethods.getXP(p).toString());
                ActionBar ab = new ActionBar(XPMethods.xpprefix + "To next level §a" + XPMethods.nextLevel + "xp");
                ab.sendToPlayer(p);
                return true;
            }

            if (args[0].equalsIgnoreCase("tops")) {
                List<String> top = XPMethods.getTops();
                p.sendMessage("§3§m---------------------§b§lTOP LIST§3§m-----------------------");
                for (String messages : top) {
                    p.sendMessage(messages);
                }
                p.sendMessage("§3§m-----------------------------------------------------");
            }
            if (args[0].equalsIgnoreCase("pay")) {
                Player t = Bukkit.getPlayer(args[1]);
                if (MySQLMethods.existsPlayer(t)) {
                    XPMethods.payXp(p, t, Integer.valueOf(args[2]));
                }
            }


            if (TAGMethods.getTag(p) < Tags.admin) {
                p.sendMessage(CoreMethods.noperm);
                return true;
            }




        }
        return false;
    }
}
