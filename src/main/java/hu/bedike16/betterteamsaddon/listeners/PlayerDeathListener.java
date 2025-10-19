package hu.bedike16.betterteamsaddon.listeners;

import com.booksaw.betterTeams.Team;
import hu.bedike16.betterteamsaddon.customevents.TeamDeathEvent;
import hu.bedike16.betterteamsaddon.customevents.TeamKillEvent;
import hu.bedike16.betterteamsaddon.messages.Message;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import hu.bedike16.betterteamsaddon.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ITeamHolder deathTeam = TeamHolder.getTeamHolder(e.getEntity());
        if(deathTeam != null) {
            deathTeam.addDeaths();

            TeamDeathEvent deathEvent = new TeamDeathEvent(deathTeam, e.getEntity());
            deathEvent.callEvent();

            if(e.getEntity().getKiller() == null) {
                Utils.broadcastTeam(deathTeam.getTeam(), Message.TEAM_DEATH.builder().setPlayer(e.getEntity()).getComponent());
            } else {
                Utils.broadcastTeam(deathTeam.getTeam(), Message.TEAM_DEATH_KILLER.builder().setPlayer(e.getEntity()).setKiller(e.getEntity().getKiller()).getComponent());
            }
        }

        if(e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            ITeamHolder killerTeam = TeamHolder.getTeamHolder(killer);
            if(killerTeam == null) return;
            killerTeam.addKills();

            TeamKillEvent killEvent = new TeamKillEvent(killerTeam, e.getEntity(), killer);
            killEvent.callEvent();

            Team killerBetterTeam = killerTeam.getTeam();
            Utils.broadcastTeam(killerBetterTeam, Message.TEAM_KILL.builder().setPlayer(e.getEntity()).setKiller(killer).getComponent());
        }
    }
}
