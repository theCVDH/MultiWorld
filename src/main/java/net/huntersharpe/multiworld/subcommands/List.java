package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import net.huntersharpe.multiworld.MultiWorld;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;

import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 7/27/2015.
 */
public class List implements CommandCallable{

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World list Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to display list of worlds."));
    private final Text usage = (Text) Texts.of("");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        Iterable<String> splitIter = Splitter.on(" ").split(s);
        if(!s.equals(null) && !s.isEmpty()){
            sendHelp(src);
            return CommandResult.success();
        }
        Iterator<World> iterator = MultiWorld.getInstance().getGame().getServer().getWorlds().iterator();
        while(iterator.hasNext()){
            //TODO: Send more user friendly name
            String name = iterator.next().getProperties().getWorldName();
            src.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, name));
            iterator.remove();
        }
        return CommandResult.success();
    }

    public java.util.List<String> getSuggestions(CommandSource commandSource, String s) throws CommandException {
        return Collections.emptyList();
    }

    public boolean testPermission(CommandSource commandSource) {
        return commandSource.hasPermission("multiworld.list");
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

    private void sendHelp(CommandSource src) {
        src.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }

}