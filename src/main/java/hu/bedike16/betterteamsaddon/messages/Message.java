package hu.bedike16.betterteamsaddon.messages;

import lombok.Getter;
import hu.bedike16.betterteamsaddon.files.MessageFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Message {

    private static final Map<String, Message> messages = new HashMap<String, Message>();

    public static final Message PREFIX = new Message("prefix",
            "<b><gradient:#3494E6:#EC6EAD>BetterTeamsAddon</gradient></b> <dark_gray>></dark_gray> ");

    public static final Message CONFIG_RELOAD = new Message("command.reload",
            "%prefix% <green>Configuration files has been reloaded.</green>");

    public static final Message NO_PERMISSION = new Message("error.no_permission",
            "%prefix%<red>You don't have permission!</red>");

    public static final Message COMMAND_USAGE = new Message("error.command_usage",
            "%prefix%<red>Usage: %usage%</red>");

    public static final Message NOT_A_INTEGER = new Message("error.not_a_integer",
            "%prefix%<red>This is not an integer.</red>");

    public static final Message TEAM_NOT_FOUND = new Message("error.team_not_found",
            "%prefix%<red>Team not found.</red>");

    public static final Message SET_KILLS = new Message("command.set.kills",
            "%prefix%<green>You set</green> <red>%team%</red> <green>kills to</green> <red>%value%</red>");

    public static final Message SET_DEATHS = new Message("command.set.deaths",
            "%prefix%<green>You set</green> <red>%team%</red> <green>deaths to</green> <red>%value%</red>");

    public static final Message SET_DAMAGE = new Message("command.set.damages",
            "%prefix%<green>You set</green> <red>%team%</red> <green>damage to</green> <red>%value%</red>");

    public static final Message LOOK_KILLS = new Message("command.look.kills",
            "%prefix%<green>Team</green> <red>%team%</red> <green>has</green> <red>%value%</red> <green>kill(s)</green>");

    public static final Message LOOK_DEATHS = new Message("command.look.deaths",
            "%prefix%<green>Team</green> <red>%team%</red> <green>has</green> <red>%value%</red> <green>death(s)</green>");

    public static final Message LOOK_DAMAGE = new Message("command.look.damages",
            "%prefix%<green>Team</green> <red>%team%</red> <green>damages</green> <red>%value%</red>");

    public static final Message TEAM_KILL = new Message("teams.kill",
            "%prefix%<red>%player%</red> <gray>has been killed by</gray> <red>%killer%</red>");

    public static final Message TEAM_DEATH_KILLER = new Message("teams.death_killer",
            "%prefix%<red>%player%</red> <gray>has been killed by</gray> <red>%killer%</red>");

    public static final Message TEAM_DEATH = new Message("teams.death",
            "%prefix%<red>%player%</red> <gray>died.</gray>");


    private final String key;
    private String message;
    private List<String> messageList;

    public Message(String key, String message) {
        this.key = key;
        this.message = message;
        this.messageList = new ArrayList<String>();

        messages.put(key, this);

        load();
    }

    public Message(String key, List<String> messageList) {
        this.key = key;
        this.message = null;
        this.messageList = messageList;

        messages.put(key, this);

        load();
    }

    private void load() {
        FileConfiguration config = MessageFile.getConfig().getFile();

        if(config.contains(this.key)) {
            this.messageList = config.getStringList(this.key);
            if(this.messageList.isEmpty()) {
                this.message = config.getString(this.key);
                return;
            }
            return;
        }

        save();
    }

    private void save() {
        FileConfiguration config = MessageFile.getConfig().getFile();

        if(!config.contains(this.key)) {
            if(this.message != null) {
                config.set(this.key, this.message);
            } else {
                config.set(this.key, this.messageList);
            }
        }
        MessageFile.getConfig().save();
    }

    public static Message[] values() {
        return messages.values().toArray(new Message[0]);
    }

    public MessageBuilder builder() {
        if(this.message == null) {
            return new MessageBuilder(this.messageList).setPrefix();
        }
        return new MessageBuilder(this.message).setPrefix();
    }
}
