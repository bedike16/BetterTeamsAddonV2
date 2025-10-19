package hu.bedike16.betterteamsaddon.placeholders;

import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class LeaderboardComparator {
    public static class KillsComparator implements Comparator<ITeamHolder> {
        @Override
        public int compare(ITeamHolder o1, ITeamHolder o2) {
            return Integer.compare(o2.getKills(), o1.getKills());
        }
    }

    public static class DeathsComparator implements Comparator<ITeamHolder> {
        @Override
        public int compare(ITeamHolder o1, ITeamHolder o2) {
            return Integer.compare(o2.getDeaths(), o1.getDeaths());
        }
    }

    public static class DamagesComparator implements Comparator<ITeamHolder> {
        @Override
        public int compare(ITeamHolder o1, ITeamHolder o2) {
            return Double.compare(o2.getDamages(), o1.getDamages());
        }
    }
}
