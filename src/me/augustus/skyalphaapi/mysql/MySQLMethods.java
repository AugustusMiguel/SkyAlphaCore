package me.augustus.skyalphaapi.mysql;

import me.augustus.skyalphaapi.Main;
import me.augustus.skyalphaapi.tag.Tags;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.*;

public class MySQLMethods {

    protected static Connection con = null;
    public static ConsoleCommandSender cs = Main.cs;

    public static void openConnection() {
        String url = "jdbc:mysql://localhost:3306/skyalpha";
        String user = "root";
        String password = "";

        try {
            con = DriverManager.getConnection(url, user, password);
            cs.sendMessage("");
            cs.sendMessage("");
            cs.sendMessage("");
            cs.sendMessage(CoreMethods.mysqlprefix + "Connection opened with no errors.");
            CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Connection opened with no errors.");
            createTable();
        } catch (SQLException e) {
            cs.sendMessage(CoreMethods.mysqlprefix + "Failed to open connection, disabling plugin and all it depencencies may not work.");
            CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Failed to open connection, disabling plugin and all it depencencies may not work.");
            Main.getPlugin().getPluginLoader().disablePlugin(Main.getPlugin());
        }
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                cs.sendMessage(CoreMethods.mysqlprefix + "Connection closed with sucess.");
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Connection closed with sucess.");
            } catch (SQLException e) {
                cs.sendMessage(CoreMethods.mysqlprefix + "Fail to close connection.");
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Fail to close connection.");

            }
        }
    }

    public static void createTable() {
        if (con != null) {
            PreparedStatement stm = null;
            try {

                stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `playerdata`(`uuid` VARCHAR(36) NOT NULL, `name` VARCHAR(30) NOT NULL, `tag` TINYINT NOT NULL, `xp` " +
                        "MEDIUMINT NOT NULL, `xplevel` SMALLINT NOT NULL)");
                 stm.executeUpdate();
                cs.sendMessage(CoreMethods.mysqlprefix + "Table created with sucess.");
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Table created with sucess.");
            } catch (SQLException e) {
                cs.sendMessage(CoreMethods.mysqlprefix + "Fail to create table.");
                CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Fail to create table.");
            }
        }
    }

    public static void deleteTable(String table) {
        if (con != null) {
            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement("DROP TABLE `" + table.toLowerCase() + "`");
                stm.executeUpdate();
                cs.sendMessage(CoreMethods.mysqlprefix + "Table §c" + table + " §7deleted with sucess.");
            } catch (SQLException e) {
            }
        }
    }

    public static void createPlayer(Player p) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("INSERT INTO `playerdata`(`uuid`, `name`, `tag`, `xp`) VALUES (?,?,?,?,?)");
            stm.setString(1, p.getUniqueId().toString());
            stm.setString(2, p.getName());
            stm.setInt(3, Tags.member);
            stm.setInt(4, 0);
            stm.setInt(5, 1);
            stm.executeUpdate();
            cs.sendMessage(CoreMethods.mysqlprefix + "Player §c" + p.getName() + " §7created with sucess.");
            CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Player §c" + p.getName() + " §7created with sucess.");
        } catch (SQLException e) {
            cs.sendMessage(CoreMethods.mysqlprefix + "Failed to create player §c" + p.getName());
            CoreMethods.sendOwnerMSG(CoreMethods.mysqlprefix + "Failed to create player §c" + p.getName());
        }
    }

    public static void deletePlayer(Player p) {
        if (existsPlayer(p)) {
            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement("DELETE FROM `playerdata` WHERE `uuid` = ?");
                stm.setString(1, p.getUniqueId().toString());
                stm.executeUpdate();
            } catch (SQLException e) {
            }

        }
    }

    public static boolean existsPlayer(Player p) {
        PreparedStatement stm = null;

        try {
            stm = con.prepareStatement("SELECT * FROM `playerdata` WHERE `uuid` = ?");
            stm.setString(1, p.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

}
