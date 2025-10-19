package hu.bedike16.betterteamsaddon.listeners;

import com.booksaw.betterTeams.customEvents.CreateTeamEvent;
import com.booksaw.betterTeams.customEvents.DisbandTeamEvent;
import hu.bedike16.betterteamsaddon.Main;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamManageListener implements Listener {

    @EventHandler
    public void onTeamCreate(CreateTeamEvent e) {
        TeamHolder.getTeamHolder(e.getTeam());
    }

    @EventHandler
    public void onTeamDisband(DisbandTeamEvent e) {
        Main.getInstance().getTeams().remove(e.getTeam().getID());
    }
}
