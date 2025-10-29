package hu.bedike16.betterteamsaddon.commands.commands;

import hu.bedike16.betterteamsaddon.commands.MainCommand;
import hu.bedike16.betterteamsaddon.commands.argumentMatchers.ContainingAllCharsOfStringArgumentMatcher;
import hu.bedike16.betterteamsaddon.commands.leaderboardsubcommands.Kills;
import hu.bedike16.betterteamsaddon.commands.subcommands.LookCommand;
import hu.bedike16.betterteamsaddon.commands.subcommands.ReloadCommand;
import hu.bedike16.betterteamsaddon.commands.subcommands.SetCommand;
import hu.bedike16.betterteamsaddon.messages.Message;

public class TeamLeaderBoardCommand extends MainCommand {

    public TeamLeaderBoardCommand() {
        super(Message.NO_PERMISSION.builder().getMessage(), new ContainingAllCharsOfStringArgumentMatcher());
    }

    @Override
    protected void registerSubCommands() {
        subCommands.add(new Kills());
    }
}
