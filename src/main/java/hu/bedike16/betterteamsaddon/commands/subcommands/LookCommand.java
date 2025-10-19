package hu.bedike16.betterteamsaddon.commands.subcommands;

import com.booksaw.betterTeams.Team;
import hu.bedike16.betterteamsaddon.commands.SubCommand;
import hu.bedike16.betterteamsaddon.messages.Message;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LookCommand implements SubCommand {
    private static final DecimalFormat decimal = new DecimalFormat("0.#");
    @Override
    public String getName() {
        return "look";
    }

    @Override
    public String getDescription() {
        return "Look Team's statistics";
    }

    @Override
    public String getSyntax() {
        return "/betterteamsaddon look <team | player> <kills | deaths | damages>";
    }

    @Override
    public String getPermission() {
        return "betterteamsaddon.command.look";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        if(args.length == 2) {
            List<String> teams = Team.getTeamManager().getLoadedTeamListClone().values().stream().map(Team::getName).collect(Collectors.toList());
            teams.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return teams;
        }
        else if (args.length == 3) {
            return Arrays.asList("kills", "deaths", "damages");
        }
        return new ArrayList<>();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(args.length == 2) {
            Team team = Team.getTeam(args[0]);
            if(team == null) {
                team = Team.getTeam(Bukkit.getOfflinePlayer(args[0]));
            }
            if(team == null && Bukkit.getPlayer(args[0])!= null) {
                team = Team.getTeam(Bukkit.getPlayer(args[0]));
            }
            if(team == null) {
                Message.TEAM_NOT_FOUND.builder().send(sender);
                return;
            }

            ITeamHolder holder = TeamHolder.getTeamHolder(team);

            switch (args[1].toLowerCase()) {
                case "kills": {
                    Message.LOOK_KILLS.builder().setTeam(team).setValue(holder.getKills() + "").send(sender);
                    break;
                }
                case "deaths": {
                    Message.LOOK_DEATHS.builder().setTeam(team).setValue(holder.getDeaths() + "").send(sender);
                    break;
                }
                case "damages": {
                    Message.LOOK_DAMAGE.builder().setTeam(team).setValue(decimal.format(holder.getDamages())).send(sender);
                    break;
                }
            }
        }
    }
}
