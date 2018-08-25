package me.augustus.skyalphaapi.utils;

import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class PlayerInfo {

    public static boolean isPremium(Player p) {
        Boolean premium = false;

        try {
            URL url = new URL("http://www.minecraft.net/haspaid.jsp?user=" + p);
            String pr = new BufferedReader(new InputStreamReader(url.openStream())).readLine().toUpperCase();
            premium = Boolean.valueOf(pr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return premium;
    }

}
