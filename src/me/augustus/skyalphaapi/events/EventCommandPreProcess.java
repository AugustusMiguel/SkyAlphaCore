package me.augustus.skyalphaapi.events;

import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EventCommandPreProcess implements Listener {

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        CoreMethods.sendOwnerMSG(CoreMethods.prefix + "Player §c" + p.getName() + " §7executed command §c" + e.getMessage());
    }
}
