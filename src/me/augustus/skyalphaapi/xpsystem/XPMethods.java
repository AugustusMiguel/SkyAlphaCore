package me.augustus.skyalphaapi.xpsystem;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XPMethods extends MySQLMethods {

    public static String xpprefix = "§a§lXP §a>> §7";
    static ConsoleCommandSender cs = Bukkit.getConsoleSender();
    public static HashMap<Player, Integer> xpToNextInt = new HashMap<Player, Integer>();

    public static void setXpToNextInt(Player p, Integer xp) {
        xpToNextInt.put(p, xp);
    }


    public static Integer getCurrentLevel(Player p) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("SELECT * FROM `playerdata` WHERE `uuid` = ?");
            stm.setString(1, p.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("xplevel");
            }
        } catch (SQLException e) {
            cs.sendMessage(xpprefix + "Failture to get XPLevel from §c" + p.getName());
            CoreMethods.sendOwnerMSG(xpprefix + "Failture to get XPLevel from §c" + p.getName());
        }
        return 0;
    }

    public static Integer getXp(Player p) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("SELECT * FROM `playerdata` WHERE `uuid` = ?");
            stm.setString(1, p.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                return rs.getInt("xp");
            }
        } catch (SQLException e) {
            cs.sendMessage(xpprefix + "Failture to get XP from §c" + p.getName());
            CoreMethods.sendOwnerMSG(xpprefix + "Failture to get XP from §c" + p.getName());
        }
        return 0;
    }

    public static void setCurrentLevel(Player p, Integer xp) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("UPDATE `playerdata` SET `xplevel` = ? WHERE `uuid` = ?");
            stm.setInt(1, xp);
            stm.setString(2, p.getUniqueId().toString());
            stm.executeUpdate();
            setXp(p, (getXp(p)/2)*2);
            xpToNextInt.put(p, (getXp(p)/2)*2);
        } catch (SQLException e) {
            cs.sendMessage(xpprefix + "Failture to set XPLevel to §c" + p.getName());
            CoreMethods.sendOwnerMSG(xpprefix + "Failture to set XPLevel to §c" + p.getName());
        }
    }

    public static void setXp(Player p, Integer xp) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("UPDATE `playerdata` SET `xp` = ? WHERE `uuid` = ?");
            stm.setInt(1, xp);
            stm.setString(2, p.getUniqueId().toString());
            stm.executeUpdate();

            if (getXp(p) == getXpToNextInt(p)) {
                setCurrentLevel(p, getCurrentLevel(p) + 1);
            }
        } catch (SQLException e) {
            cs.sendMessage(xpprefix + "Failture to set XP to §c" + p.getName());
            CoreMethods.sendOwnerMSG(xpprefix + "Failture to set XP to §c" + p.getName());
        }
    }

    public static Integer getXpToNextInt(Player p) {
        return xpToNextInt.get(p);
    }

    public static List<String> getXpTops() {
        PreparedStatement stm = null;
        List<String> tops = new ArrayList<String>();
        try {
            stm = con.prepareStatement("SELECT * FROM `playerdata` ORDER BY `xp` DESC");
            ResultSet rs = stm.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (i <= 10) {
                    i++;
                    tops.add("§7" + i + "º §b" + rs.getString("name") + " §c" + rs.getInt("xp"));
                }
            }
        } catch (SQLException e) {
            cs.sendMessage(xpprefix + "Failture to get XPTops to §c");
            CoreMethods.sendOwnerMSG(xpprefix + "Failture to get XPTops to §c");
        }
        return tops;
    }

}
