package hu.bedike16.betterteamsaddon.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import hu.bedike16.betterteamsaddon.Main;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BetterTeamsPlaceholders extends PlaceholderExpansion {
    private static final DecimalFormat decimal = new DecimalFormat("0.#");
    private final LeaderboardComparator.KillsComparator killsComparator = new LeaderboardComparator.KillsComparator();
    private final LeaderboardComparator.DeathsComparator deathsComparator = new LeaderboardComparator.DeathsComparator();
    private final LeaderboardComparator.DamagesComparator damagesComparator = new LeaderboardComparator.DamagesComparator();


    @Override
    public @NotNull String getIdentifier() {
        return "betterteamsaddon";
    }

    @Override
    public @NotNull String getAuthor() {
        return "koba1";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.2";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        // %betterteamsaddon_kdr%
        // %betterteamsaddon_kdr_<name>%
        // %betterteamsaddon_kills%
        // %betterteamsaddon_kills_<name>%
        // %betterteamsaddon_deaths_<name>%
        // %betterteamsaddon_deaths%
        // %betterteamsaddon_damages_<name>%
        // %betterteamsaddon_damages%

        // %betterteamsaddon_leaderboard_kills_1_name%
        // %betterteamsaddon_leaderboard_kills_1_value%

        // %betterteamsaddon_leaderboard_deaths_3_name%
        // %betterteamsaddon_leaderboard_deaths_3_value%

        // %betterteamsaddon_leaderboard_damages_7_name%
        // %betterteamsaddon_leaderboard_damages_7_value%

        String[] args = params.split("_");
        if(args.length == 4 && args[0].equalsIgnoreCase("leaderboard")) {
            return parseLeaderboard(Arrays.copyOfRange(args, 1, args.length));
        }

        ITeamHolder holder = null;
        if (args.length == 1) {
            holder = TeamHolder.getTeamHolder(player);
        } else if (args.length == 2) {
            holder = TeamHolder.getTeamHolder(args[1]);
        }

        if (holder == null) {
            return Main.getInstance().getConfigData().getNoTeamPlaceholder();
        }

        switch (args[0]) {
            case "kills": {
                return holder.getKills() + "";
            }
            case "deaths": {
                return holder.getDeaths() + "";
            }
            case "damages": {
                return decimal.format(holder.getDamages());
            }
            case "kdr": {
                double kdr = (double) holder.getKills() / holder.getDeaths();
                return roundToNDecimalPlaces(kdr, Main.getInstance().getConfigData().getKdrRound()) + "";
            }
        }

        return null;
    }

    private double roundToNDecimalPlaces(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("Decimal places cannot be negative");

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);  // Rounds using "half up" rounding method
        return bd.doubleValue();
    }

    public String parseLeaderboard(String[] args) {
        Comparator<ITeamHolder> comparator = null;
        switch (args[0].toLowerCase()) {
            case "kills":
                comparator = killsComparator;
                break;
            case "deaths":
                comparator = deathsComparator;
                break;
            case "damages":
                comparator = damagesComparator;
                break;
        }

        if(comparator == null) return null;

        int index = Integer.parseInt(args[1]) - 1;
        if(index < 0) return null;

        List<ITeamHolder> teams = Main.getInstance().getTeams().values().stream().sorted(comparator).collect(Collectors.toList());

        if(teams.size() <= index) return Main.getInstance().getConfigData().getNoTeamPlaceholder();

        if(args[2].equalsIgnoreCase("name")) {
            return teams.get(index).getTeam().getDisplayName();
        } else if(args[2].equalsIgnoreCase("value")) {
            switch (args[0].toLowerCase()) {
                case "kills":
                    return teams.get(index).getKills() + "";
                case "deaths":
                    return teams.get(index).getDeaths() + "";
                case "damages":
                    return decimal.format(teams.get(index).getDamages());
            }
        }

        return null;
    }
}
