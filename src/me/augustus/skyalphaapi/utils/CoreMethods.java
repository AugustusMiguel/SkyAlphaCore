package me.augustus.skyalphaapi.utils;

import me.augustus.skyalphaapi.api.Configs;
import me.augustus.skyalphaapi.commands.CommandLog;
import me.augustus.skyalphaapi.tag.TAGMethods;
import me.augustus.skyalphaapi.tag.Tags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CoreMethods {

    public static String prefix = "§c§lSkyAlphaAPI §c>> §7";
    public static String mysqlprefix = "§c§lSkyAlphaAPI MySQL §c>> §7";
    public static String noperm = prefix + "You don't have permission.";

    public static Configs players = new Configs("players.yml");

    public static void sendOwnerMSG(String message) {
        for (Player s : Bukkit.getServer().getOnlinePlayers()) {
            CommandLog.getLog(s);
            if (CommandLog.isLog()) {
                if (TAGMethods.getTag(s) == Tags.owner) {
                    s.sendMessage(message);
                }
            }
        }
    }
}
