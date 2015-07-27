package net.huntersharpe.multiworld.subcommands;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import net.huntersharpe.multiworld.MultiWorld;
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
public class Join implements CommandCallable {

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World join Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to join worlds."));
    private final Text usage = (Text) Texts.of("<world>");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of("You cant use this command!"));
            return CommandResult.success();
        }
        Player player = ((Player) src).getPlayer().get();
        String[] args = s.split(" ");
        if(args.length != 1){
            sendHelp(player);
            return CommandResult.success();
        }
        if(!MultiWorld.getInstance().getGame().getServer().getWorld(args[0]).isPresent()){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World does not exist!"));
            return CommandResult.success();
        }
        World world = MultiWorld.getInstance().getGame().getServer().getWorld(args[0]).get();
        Vector3d vec = world.getSpawnLocation().getPosition();
        player.transferToWorld(world.getName(), vec);
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Joining World: ", world.getName()));
        return CommandResult.success();
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use.join");
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

    public void sendHelp(CommandSource source){
        source.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }
}
