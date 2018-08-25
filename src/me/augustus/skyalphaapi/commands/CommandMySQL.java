package me.augustus.skyalphaapi.commands;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.tag.TAGMethods;
import me.augustus.skyalphaapi.tag.Tags;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandMySQL implements CommandExecutor, Listener {

    private static List<Player> secondverification = new ArrayList<Player>();
    private static String table;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

        if (cmd.getName().equalsIgnoreCase("mysql")) {
            if (!(sender instanceof Player)) return true;
                Player p = (Player)sender;
                if (TAGMethods.getTag(p) < Tags.owner) {
                    p.sendMessage(CoreMethods.noperm);
                    return true;
            }
            if (args.length < 2) {
                sender.sendMessage(CoreMethods.mysqlprefix + "Use: §c/mysql (deletetable) (tablename)");
                return true;
            }

            if (args[0].equalsIgnoreCase("deletetable")) {
                secondverification.add(p);
                p.sendMessage(CoreMethods.mysqlprefix + "Digite o nome da tabela para confirmar.");
                table = args[1];
            }

        }

        return false;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (secondverification.contains(p)) {
            if (e.getMessage().equals(table)) {
                MySQLMethods.deleteTable(table);
                p.sendMessage(CoreMethods.mysqlprefix + "Table §c" + table + " §7deleted with sucess");
                e.setCancelled(true);
            } else {
                secondverification.remove(p);
                p.sendMessage(CoreMethods.mysqlprefix + "You answer wrong the verification, cancelling the drop.");
                e.setCancelled(true);
            }

        }
    }


}
