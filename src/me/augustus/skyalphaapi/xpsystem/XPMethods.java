package me.augustus.skyalphaapi.xpsystem;

import me.augustus.skyalphaapi.Main;
import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.utils.ActionBar;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XPMethods extends MySQLMethods {

    public static String xpprefix = "§a§lXP §a>> §7";

    public static Integer nextLevel = 0;

    public static void setNextLevel(Player p) {
        XPMethods.nextLevel = XPMethods.getXP(p) / 2 * 2;
    }

    public static Integer getXP(Player p) {
        if (existsPlayer(p)) {
            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement("SELECT * FROM `playerdata` WHERE `uuid` = ?");
                stm.setString(1, p.getUniqueId().toString());
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    return rs.getInt("xp");
                }
                return 0;
            } catch (SQLException e) {
                return 0;
            }
        } else {
            createPlayer(p);
            return 0;
        }
    }

    public static void setXP(Player p, Integer xp) {
        if (existsPlayer(p)) {
            PreparedStatement stm = null;
            try {
                stm = con.prepareStatement("UPDATE `playerdata` SET `xp` = ? WHERE `uuid` = ?");
                stm.setInt(1, xp);
                stm.setString(2, p.getUniqueId().toString());
                stm.executeUpdate();
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Set the xp for §c" + p.getName() + " to §c" + xp.toString());
                Main.cs.sendMessage(CoreMethods.mysqlprefix + "Set the xp for §c" + p.getName() + " to §c" + xp.toString());
                if (p.isOnline()) {
                    ActionBar bar = new ActionBar(xpprefix + xp);
                    bar.sendToPlayer(p);
                }
            } catch (SQLException e) {
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Fail set the xp for §c" + p.getName() + " to §c" + xp.toString());
                Main.cs.sendMessage(CoreMethods.mysqlprefix + "Fail set the xp for §c" + p.getName() + " to §c" + xp.toString());

            }
        } else {
            createPlayer(p);
        }
    }

    public static void addXP(Player p, Integer xpToAdd) {
        if (existsPlayer(p)) {
            setXP(p, getXP(p) + xpToAdd);
            if (p.isOnline()) {
                ActionBar bar = new ActionBar(xpprefix + "+" + xpToAdd);
                bar.sendToPlayer(p);
            }
        } else {
            createPlayer(p);
        }
    }

    public static void removeXP(Player p, Integer xpToRemove) {
        if (existsPlayer(p)) {
            setXP(p, getXP(p) - xpToRemove);
            if (p.isOnline()) {
                ActionBar bar = new ActionBar(xpprefix + "-" + xpToRemove);
                bar.sendToPlayer(p);
            }
        }
    }

    public static void payXp(Player p, Player t, Integer xp) {
        if (existsPlayer(p)) {
            if (getXP(p) < xp) {
                p.sendMessage(xpprefix + "You don't have enough to pay.");
                return;
            }
            Integer finalxp = (xp/100)*75;
            removeXP(p, xp);
            addXP(t, finalxp);
        }
    }


    public static List<String> getTops() {
        PreparedStatement stm = null;
        List<String> tops = new ArrayList<String>();

        try {
            stm = con.prepareStatement("SELECT * FROM `playerdata` ORDER BY `xp` DESC");
            ResultSet rs = stm.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (i <= 10) {
                    i++;
                    tops.add("§b" + 1 + "º §7" + rs.getString("name") + " - §c" + rs.getInt("xp"));
                }
            }
        } catch (SQLException e) {
            Main.cs.sendMessage(CoreMethods.mysqlprefix + "Fail to load XP's tops.");
        }
        return tops;
    }


}
