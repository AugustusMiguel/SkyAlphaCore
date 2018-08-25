package me.augustus.skyalphaapi;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.utils.CoreMethods;
import me.augustus.skyalphaapi.utils.Register;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static ConsoleCommandSender cs = Bukkit.getServer().getConsoleSender();

    @Override
    public void onEnable() {
        MySQLMethods.openConnection();
        cs.sendMessage(CoreMethods.prefix + "SkyAlphaAPI loaded with sucess.");
        cs.sendMessage("");
        cs.sendMessage("");
        cs.sendMessage("");
        new Register();
        if (!CoreMethods.players.exists())
            CoreMethods.players.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        MySQLMethods.closeConnection();
    }

    public static Main getPlugin() {
        return getPlugin(Main.class);
    }
}
