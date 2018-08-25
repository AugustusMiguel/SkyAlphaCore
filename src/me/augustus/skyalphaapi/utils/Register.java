package me.augustus.skyalphaapi.utils;

import me.augustus.skyalphaapi.Main;
import me.augustus.skyalphaapi.commands.CommandLog;
import me.augustus.skyalphaapi.commands.CommandMySQL;
import me.augustus.skyalphaapi.commands.CommandProfile;
import me.augustus.skyalphaapi.events.EventCommandPreProcess;
import me.augustus.skyalphaapi.events.EventPlayerJoin;
import me.augustus.skyalphaapi.tag.TAGCommand;
import me.augustus.skyalphaapi.xpsystem.XPCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class Register {

    public Register() {
        //Commands
        command("logmode", new CommandLog());
        command("mysql", new CommandMySQL());
        command("xp", new XPCommands());
        command("tag", new TAGCommand());
        command("profile", new CommandProfile());

        //Events
        event(new EventPlayerJoin());
        event(new EventCommandPreProcess());
        event(new CommandMySQL());

    }

    void event(Listener lclass) {
        Bukkit.getPluginManager().registerEvents(lclass, Main.getPlugin());
    }

    void command(String command, CommandExecutor cmeclass) {
        Main.getPlugin().getCommand(command).setExecutor(cmeclass);
    }
}
