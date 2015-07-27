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
 * Created by user on 7/26/2015.
 */
public class Modify implements CommandCallable {

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World modify Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to modify worlds."));
    private final Text usage = (Text) Texts.of("<type> <value>");

    public CommandResult process(CommandSource commandSource, String s) throws CommandException {
        //TODO: Finish Command
        return CommandResult.success();
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use.modify");
    }

    public Optional<Text> getShortDescription(CommandSource source) {
        return desc;
    }

    public Optional<Text> getHelp(CommandSource source) {
        return help;
    }

    public Text getUsage(CommandSource source) {
        return usage;
    }

    public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
        return Collections.emptyList();
    }
}
