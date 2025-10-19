package hu.bedike16.betterteamsaddon.customevents;

import lombok.Getter;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import org.bukkit.entity.Player;

@Getter
public class TeamDeathEvent extends TeamEvent {

    private final Player player;

    /**
     * This event fires when a team member dead.
     * @param team TeamHolder
     * @param player Victim
     */
    public TeamDeathEvent(ITeamHolder team, Player player) {
        super(team);
        this.player = player;
    }
}
