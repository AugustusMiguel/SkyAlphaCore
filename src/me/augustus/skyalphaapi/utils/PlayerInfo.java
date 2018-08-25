package me.augustus.skyalphaapi.utils;

import me.augustus.skyalphaapi.api.Configs;
import org.bukkit.entity.Player;

public class PlayerInfo {

    public static boolean isPremium(Player p) {
        Boolean premium = false;

        if (p.getUniqueId() == null) {
            premium = false;
        } else {
            premium = true;
        }
        return premium;
    }

    public static boolean existPlayerInConfig(Player p) {
        if (CoreMethods.players.contains(p.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public static void createPlayerInConfig(Player p) {
        if (!existPlayerInConfig(p)) {
            Configs players = CoreMethods.players;
            players.set(p.getName() + ".matchesWinned", 0);
            players.set(p.getName() + ".matchesLose", 0);
        }
    }

}
