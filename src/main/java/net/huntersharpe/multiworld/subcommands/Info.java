package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import java.util.*;

/**
 * Created by user on 7/28/2015.
 */
public class Info implements CommandCallable{

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World list Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to display world info."));
    private final Text usage = (Text) Texts.of("");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        if(!(src instanceof Player)){
            sendHelp(src);
            return CommandResult.success();
        }
        if(!s.equals(null) && !s.isEmpty()){
            sendHelp(src);
            return CommandResult.success();
        }
        Player player = ((Player) src).getPlayer().get();
        player.sendMessage(Texts.of(
                TextColors.DARK_GRAY,
                "----------",
                TextColors.BLUE,
                player.getWorld().getName(),
                " Info",
                TextColors.DARK_GRAY,
                "----------"
        ));
        player.sendMessage(Texts.of(TextColors.GREEN, "World Name: ", TextColors.BLUE, player.getWorld().getName()));
        player.sendMessage(Texts.of(TextColors.GREEN, "GameMode: ", TextColors.BLUE, player.getWorld().getProperties().getGameMode().getName()));
        player.sendMessage(Texts.of(TextColors.GREEN, "Difficulty: ", TextColors.BLUE, player.getWorld().getDifficulty().getName()));
        player.sendMessage(Texts.of(TextColors.GREEN, "Spawn Location: ", TextColors.BLUE, player.getWorld().getSpawnLocation().toString()));
        player.sendMessage(Texts.of(TextColors.GREEN, "World Seed: ", TextColors.BLUE, String.valueOf(player.getWorld().getProperties().getSeed())));
        return CommandResult.success();
    }

    public java.util.List<String> getSuggestions(CommandSource commandSource, String s) throws CommandException {
        return Collections.emptyList();
    }

    public boolean testPermission(CommandSource commandSource) {
        return commandSource.hasPermission("multiworld.info");
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

    public void sendHelp(CommandSource source){
        source.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }

}