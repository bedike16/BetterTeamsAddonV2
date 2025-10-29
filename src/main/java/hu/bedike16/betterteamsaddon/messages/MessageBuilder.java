package hu.bedike16.betterteamsaddon.messages;

import com.booksaw.betterTeams.Team;
import lombok.Getter;
import hu.bedike16.betterteamsaddon.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageBuilder {

    private String message;
    private List<String> messages;

    public MessageBuilder(String message) {
        this.message = message;
    }

    public MessageBuilder(List<String> messages) {
        this.messages = messages;
    }

    public MessageBuilder replace(String target, String replacement) {
        if (this.message != null) {
            this.message = this.message.replace(target, replacement);
        } else {
            this.messages = replaceList(target, replacement);
        }
        return this;
    }


    private List<String> replaceList(String target, String replacement) {
        List<String> str = new ArrayList<>();
        for (String s : this.messages) {
            str.add(s.replace(target, replacement));
        }
        return str;
    }

    public MessageBuilder setPlayer(Player player) {
        this.replace("%player%", player.getName());
        return this;
    }

    public MessageBuilder setTeam(Team team) {
        this.replace("%team%", team.getName());
        return this;
    }

    public MessageBuilder setTarget(Player target) {
        this.replace("%target%", target.getName());
        return this;
    }

    public MessageBuilder setKiller(Player killer) {
        this.replace("%killer%", killer.getName());
        return this;
    }

    public MessageBuilder setValue(String value) {
        this.replace("%value%", value);
        return this;
    }

    public MessageBuilder setUsage(String usage) {
        this.replace("%usage%", usage);
        return this;
    }

    public MessageBuilder setPrefix() {
        this.replace("%prefix%", Message.PREFIX.getMessage());
        return this;
    }

    public void send(CommandSender sender) {
        if(this.getMessage() != null) {
            Main.getInstance().adventure().sender(sender).sendMessage(ComponentUtil.formatToComponent(this.message));
        } else {
            Main.getInstance().adventure().sender(sender).sendMessage(ComponentUtil.formatToComponent(this.messages));
        }
    }

    public Component getComponent() {
        if(this.getMessage() != null && this.getMessage().isEmpty()) {
            return null;
        }
        if(this.getMessage() != null) {
            return ComponentUtil.formatToComponent(this.message);
        } else if(!this.getMessages().isEmpty()) {
            return ComponentUtil.formatToComponent(this.messages);
        }
        return null;
    }
}
