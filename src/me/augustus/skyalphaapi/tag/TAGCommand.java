package me.augustus.skyalphaapi.tag;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TAGCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("tag")) {
            if (TAGMethods.getTag(p) < Tags.admin) {
                p.sendMessage(CoreMethods.noperm);
                return true;
            }
            if (args.length < 3) {
                p.sendMessage(CoreMethods.prefix + "Use: §c/tag (set) (player) (tag)");
                return true;
            }

            if (!MySQLMethods.existsPlayer(Bukkit.getPlayer(args[1]))) {

            }


            String tag = args[2];
            Player t = Bukkit.getPlayer(args[1]);

            switch (tag) {
                case ("owner"):
                    TAGMethods.setTag(t, Tags.owner);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("admin"):
                    TAGMethods.setTag(t, Tags.admin);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("moderator"):
                    TAGMethods.setTag(t, Tags.moderator);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("vip+"):
                    TAGMethods.setTag(t, Tags.vipplus);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("vip"):
                    TAGMethods.setTag(t, Tags.vip);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("helper"):
                    TAGMethods.setTag(t, Tags.helper);
                    TAGMethods.refreshPlayer(t);
                    break;
                case ("member"):
                    TAGMethods.setTag(t, Tags.member);
                    TAGMethods.refreshPlayer(t);
                    break;
            }


        }
        return false;
    }

}
