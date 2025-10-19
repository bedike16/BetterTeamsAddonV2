package hu.bedike16.betterteamsaddon.objects;

import com.booksaw.betterTeams.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import hu.bedike16.betterteamsaddon.Main;
import hu.bedike16.betterteamsaddon.files.TeamData;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
public class TeamHolder implements ITeamHolder {

    private Team team;
    private int kills;
    private int deaths;
    private double damages;

    public TeamHolder(Team team) {
        this(team, 0, 0, 0D);

        Main.getInstance().getTeams().put(this.team.getID(), this);
        load();
    }

    @Override
    public void load() {
        String path = "teams." + this.team.getID() + ".";
        this.kills = Main.getInstance().getTeamData().getConfig().getInt(path + "kills", 0);
        this.deaths = Main.getInstance().getTeamData().getConfig().getInt(path + "deaths", 0);
        this.damages = Main.getInstance().getTeamData().getConfig().getInt(path + "damages", 0);
        Main.getInstance().getTeamData().save();
    }

    @Override
    public void save() {
        String path = "teams." + this.team.getID() + ".";
        Main.getInstance().getTeamData().getConfig().set(path + "kills", this.kills);
        Main.getInstance().getTeamData().getConfig().set(path + "deaths", this.deaths);
        Main.getInstance().getTeamData().getConfig().set(path + "damages", this.damages);

        Main.getInstance().getTeamData().save();
    }

    @Override
    public int setKills(int kills) {
        return this.kills = Math.max(0, kills);
    }

    @Override
    public int setDeaths(int deaths) {
        return this.deaths = Math.max(0, deaths);
    }

    @Override
    public double setDamages(double damage) {
        return this.damages = Math.max(0, damage);
    }

    @Override
    public int addKills() {
        return this.kills += 1;
    }

    @Override
    public int addDeaths() {
        return this.deaths += 1;
    }

    @Override
    public double addDamage(double damage) {
        this.damages += damage;
        if(this.damages > Integer.MAX_VALUE) {
            this.damages = Integer.MAX_VALUE;
            return this.damages;
        }
        return this.damages;
    }

    @NonNull
    public static ITeamHolder getTeamHolder(Team team) {
        if(Main.getInstance().getTeams().containsKey(team.getID())) {
            return Main.getInstance().getTeams().get(team.getID());
        }
        return new TeamHolder(team);
    }

    @Nullable
    public static ITeamHolder getTeamHolder(Player player) {
        Team team = Team.getTeam(player);
        if(team == null) return null;

        return getTeamHolder(team);
    }

    @Nullable
    public static ITeamHolder getTeamHolder(OfflinePlayer player) {
        Team team = Team.getTeam(player);
        if(team == null) return null;

        return getTeamHolder(team);
    }

    @Nullable
    public static ITeamHolder getTeamHolder(String teamName) {
        Team team = Team.getTeam(teamName);
        if(team == null) return null;

        return getTeamHolder(team);
    }
}
