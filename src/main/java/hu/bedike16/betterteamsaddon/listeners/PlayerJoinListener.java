package hu.bedike16.betterteamsaddon.listeners;

import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        TeamHolder.getTeamHolder(e.getPlayer());
    }
}
