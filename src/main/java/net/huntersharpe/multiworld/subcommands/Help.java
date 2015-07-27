package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 7/27/2015.
 */
public class Help implements CommandCallable{

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World help Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to display help."));
    private final Text usage = (Text) Texts.of("");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        //TODO: Display Help Menu
        return CommandResult.success();
    }

    public List<String> getSuggestions(CommandSource commandSource, String s) throws CommandException {
        return Collections.emptyList();
    }

    public boolean testPermission(CommandSource commandSource) {
        return commandSource.hasPermission("multiworld.help");
    }

    public Optional<? extends Text> getShortDescription(CommandSource commandSource) {
        return desc;
    }

    public Optional<? extends Text> getHelp(CommandSource commandSource) {
        return help;
    }

    public Text getUsage(CommandSource commandSource) {
        return usage;
    }
}
