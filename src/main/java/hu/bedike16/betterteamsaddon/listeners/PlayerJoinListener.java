package hu.bedike16.betterteamsaddon.listeners;

import hu.bedike16.betterteamsaddon.Main;
import hu.bedike16.betterteamsaddon.messages.Message;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        TeamHolder.getTeamHolder(player);

        Main plugin = Main.getInstance();
        if (!plugin.getConfig().getBoolean("op-update-alert", true)) return;
        if (!player.hasPermission("betterteamsaddon.updatealert")) return;
        String latest = plugin.getLatestVersion();
        if (latest != null && !plugin.getDescription().getVersion().equals(latest)) {
            Message.UPDATE_AVAILABLE.builder()
                    .replace("%version%", latest)
                    .send(player);
        }
    }
}