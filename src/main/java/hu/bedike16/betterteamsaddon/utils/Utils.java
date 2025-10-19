package hu.bedike16.betterteamsaddon.utils;

import com.booksaw.betterTeams.Team;
import hu.bedike16.betterteamsaddon.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public static void broadcastTeam(Team team, Component comp) {
        if(comp == null) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team playerTeam = Team.getTeam(player);

            if(playerTeam == null) continue;
            if(!team.getID().equals(playerTeam.getID())) continue;

            Main.getInstance().adventure().player(player).sendMessage(comp);
        }
    }
}
