package hu.bedike16.betterteamsaddon.commands.subcommands;

import com.booksaw.betterTeams.Team;
import hu.bedike16.betterteamsaddon.commands.SubCommand;
import hu.bedike16.betterteamsaddon.messages.Message;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetCommand implements SubCommand {
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Set Team's statistics";
    }

    @Override
    public String getSyntax() {
        return "/betterteamsaddon set <team | player> <kills | deaths | damages> <value>";
    }

    @Override
    public String getPermission() {
        return "betterteamsaddon.command.set";
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
        if(args.length == 3) {
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

            if(!args[2].matches("^[0-9]+$")) {
                Message.NOT_A_INTEGER.builder().send(sender);
                return;
            }

            int newValue = Integer.parseInt(args[2]);
            switch (args[1].toLowerCase()) {
                case "kills": {
                    holder.setKills(newValue);
                    Message.SET_KILLS.builder().setTeam(team).setValue(newValue + "").send(sender);
                    break;
                }
                case "deaths": {
                    holder.setDeaths(newValue);
                    Message.SET_DEATHS.builder().setTeam(team).setValue(newValue + "").send(sender);
                    break;
                }
                case "damages": {
                    holder.setDamages(newValue);
                    Message.SET_DAMAGE.builder().setTeam(team).setValue(newValue + "").send(sender);
                    break;
                }
            }
        }
    }
}
