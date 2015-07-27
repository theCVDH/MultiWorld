package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import net.huntersharpe.multiworld.MultiWorld;
import net.huntersharpe.multiworld.WorldHandler;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 7/26/2015.
 */
public class Delete implements CommandCallable{

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World create Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to delete worlds."));
    private final Text usage = (Text) Texts.of("<name>");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        if(!(src instanceof Player)){
            sendHelp(src);
            return CommandResult.success();
        }
        String[] args = s.split(" ");
        Player player = ((Player) src).getPlayer().get();
        if(args.length != 1){
            sendHelp(player);
            return CommandResult.success();
        }
        if(!MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).isPresent()){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World: ", args[2], " does not exist!"));
            return CommandResult.success();
        }
        World world = MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).get();
        WorldHandler.getInstance().deleteDimension(player, world);
        //TODO Finish Delete Command.
        return CommandResult.success();
    }

    private void sendHelp(CommandSource src) {
        src.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use.delete");
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

