package hu.bedike16.betterteamsaddon.commands.subcommands;

import hu.bedike16.betterteamsaddon.Main;
import hu.bedike16.betterteamsaddon.commands.SubCommand;
import hu.bedike16.betterteamsaddon.files.MessageFile;
import hu.bedike16.betterteamsaddon.messages.Message;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload configuration files";
    }

    @Override
    public String getSyntax() {
        return "/betterteamsaddon reload";
    }

    @Override
    public String getPermission() {
        return "betterteamsaddon.command.reload";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Main.getInstance().reloadConfig();
        MessageFile.getConfig().reload();

        Main.getInstance().getConfigData().reload();
        Main.getInstance().adventure().sender(sender).sendMessage(Message.CONFIG_RELOAD.builder().getComponent());
    }
}
