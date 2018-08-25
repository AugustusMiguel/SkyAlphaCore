package me.augustus.skyalphaapi.tag;

import me.augustus.skyalphaapi.utils.CoreMethods;
import me.augustus.skyalphaapi.mysql.MySQLMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TAGMethods extends MySQLMethods {

    public static void setTag(Player p, Integer tag) {
        if (existsPlayer(p)) {
            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement("UPDATE playerdata SET tag = ? WHERE uuid = ?");
                stm.setInt(1 , tag);
                stm.setString(2, p.getUniqueId().toString());
                stm.executeUpdate();
                cs.sendMessage(CoreMethods.mysqlprefix + "Tag of §c" + p.getName() + " §7has sucefully setted to §c" + tag);
            } catch (SQLException e) {
                cs.sendMessage(CoreMethods.mysqlprefix + "Fail to set tag of player §c" + p.getName());
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Fail to set tag of player §c" + p.getName());
            }
        } else {
            createPlayer(p);
        }
    }

    public static Integer getTag(Player p) {
        if (existsPlayer(p)) {
            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement("SELECT * FROM `playerdata` WHERE `uuid` = ?");
                stm.setString(1, p.getUniqueId().toString());
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    return rs.getInt("tag");
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

    public static void refreshPlayer(Player p) {
        p.kickPlayer(CoreMethods.prefix + "Reload to update tag.");
        updatePlayer(p);
    }

    public static void updatePlayer(Player p) {
        Integer tag = TAGMethods.getTag(p);

        if (tag == Tags.owner) {
            p.setDisplayName("§c§lOwner §b§l" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§c§lOwner §b§l" + p.getName() + "§7");
        }
        if (tag == Tags.admin) {
            p.setDisplayName("§6§lAdministrator §6" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§6§lAdministrator §6" + p.getName() + "§7");
        }
        if (tag == Tags.moderator) {
            p.setDisplayName("§aModerator§a" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§aModerator§a" + p.getName() + "§7");
        }
        if (tag == Tags.vipplus) {
            p.setDisplayName("§bVIP+§b" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§bVIP+§b" + p.getName() + "§7");
        }
        if (tag == Tags.vip) {
            p.setDisplayName("§9VIP§7" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§9VIP§7" + p.getName() + "§7");
        }
        if (tag == Tags.helper) {
            p.setDisplayName("§eHelper §7" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§eHelper §7" + p.getName() + "§7");
        }
        if (tag == Tags.member) {
            p.setDisplayName("§7Member" + p.getName() + "§7");
            Bukkit.getPlayer(p.getUniqueId()).setPlayerListName("§7Member" + p.getName() + "§7");
        }

    }


}
