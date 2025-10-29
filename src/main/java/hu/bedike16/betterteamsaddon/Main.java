package hu.bedike16.betterteamsaddon;

import com.booksaw.betterTeams.Team;
import hu.bedike16.betterteamsaddon.utils.UpdateChecker;
import lombok.Getter;
import lombok.NonNull;
import hu.bedike16.betterteamsaddon.commands.commands.BetterTeamsAddonCommand;
import hu.bedike16.betterteamsaddon.files.MessageFile;
import hu.bedike16.betterteamsaddon.files.TeamData;
import hu.bedike16.betterteamsaddon.listeners.PlayerDamageListener;
import hu.bedike16.betterteamsaddon.listeners.PlayerDeathListener;
import hu.bedike16.betterteamsaddon.listeners.PlayerJoinListener;
import hu.bedike16.betterteamsaddon.listeners.TeamManageListener;
import hu.bedike16.betterteamsaddon.messages.Message;
import hu.bedike16.betterteamsaddon.objects.ITeamHolder;
import hu.bedike16.betterteamsaddon.objects.TeamHolder;
import hu.bedike16.betterteamsaddon.placeholders.BetterTeamsPlaceholders;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class Main extends JavaPlugin {

    @Getter private static Main instance;
    private Map<UUID, ITeamHolder> teams;
    private BetterTeamsPlaceholders placeholders;
    private BukkitAudiences adventure;
    private ConfigData configData;
    private TeamData teamData;
    @Getter private String latestVersion;

    public static Main getInstance() {
        return instance;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public Map<UUID, ITeamHolder> getTeams() {
        return teams;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.teams = new HashMap<>();

        new UpdateChecker(this, 119246).getVersion(version -> {
            this.latestVersion = version;
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("No new version available.");
            } else {
                getLogger().info("There is a new update available. Download it here: https://www.spigotmc.org/resources/betterteamsaddon.119246/");
            }
        });

        int pluginId = 23207;
        Metrics metrics = new Metrics(this, pluginId);

        if(!new File(getDataFolder(), "config.yml").exists())
            saveResource("config.yml", false);

        if (getServer().getPluginManager().getPlugin("BetterTeams") == null) {
            Bukkit.getLogger().severe("This plugin requires BetterTeams installed on server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getLogger().severe("This plugin requires PlaceholderAPI installed on server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.adventure = BukkitAudiences.create(this);

        this.configData = new ConfigData();

        this.placeholders = new BetterTeamsPlaceholders();
        this.placeholders.register();

        teamData = new TeamData("teamdata.yml");
        new MessageFile("messages.yml");

        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new TeamManageListener(), this);

        BetterTeamsAddonCommand cmd = new BetterTeamsAddonCommand();
        cmd.registerMainCommand(this, "betterteamsaddon");

        Message.values();

        ConfigurationSection sec = teamData.getConfig().getConfigurationSection("teams");
        if(sec == null) return;
        for (String key : sec.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            Team t = Team.getTeam(uuid);
            if(t == null) continue;
            new TeamHolder(t);
        }
        System.out.println(" ___      _   _          _____                    _      _    _          ");
        System.out.println("| _ ) ___| |_| |_ ___ _ |_   _|__ __ _ _ __  ___ /_\\  __| |__| |___ _ _  ");
        System.out.println("| _ \\/ -_)  _|  _/ -_) '_|| |/ -_) _` | '  \\(_-</ _ \\/ _` / _` / _ \\ ' \\");
        System.out.println("|___/\\___|\\__|\\__\\___|_|  |_|\\___\\__,_|_|_|_/__/_/ \\_\\__,_\\__,_\\___/_||_|");
        System.out.println("BetterTeamsAddon V2 by bedike16");
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        teams.values().forEach(ITeamHolder::save);

        if(this.placeholders != null) {
            this.placeholders.unregister();
        }
    }
}