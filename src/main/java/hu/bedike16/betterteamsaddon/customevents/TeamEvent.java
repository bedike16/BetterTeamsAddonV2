package hu.bedike16.betterteamsaddon.customevents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class TeamEvent extends Event {

    private final ITeamHolder team;

    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }
}
