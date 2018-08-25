package me.augustus.skyalphaapi.events;

import me.augustus.skyalphaapi.mysql.MySQLMethods;
import me.augustus.skyalphaapi.tag.TAGMethods;
import me.augustus.skyalphaapi.tag.Tags;
import me.augustus.skyalphaapi.utils.CoreMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.getName().length() > 36) {
            p.kickPlayer(CoreMethods.prefix + "You can only use 36 characters in your name, if you don't have other account or don't want to change your name. Contact the support.");
            return;
        }
        if (!MySQLMethods.existsPlayer(p))
            MySQLMethods.createPlayer(p);

        TAGMethods.updatePlayer(p);

            TAGMethods.setTag(p, Tags.owner);
    }
}
