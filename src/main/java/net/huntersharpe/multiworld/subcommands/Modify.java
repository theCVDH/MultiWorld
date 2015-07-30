package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import net.huntersharpe.multiworld.Command;
import net.huntersharpe.multiworld.MultiWorld;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameModes;
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
public class Modify implements CommandCallable {

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World modify Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to modify worlds."));
    private final Text usage = (Text) Texts.of("<type> <value>");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        //TODO: Finish Command
        if(!(src instanceof Player)){
            sendHelp(src);
            return CommandResult.success();
        }
        Player player = ((Player) src).getPlayer().get();
        String[] args = s.split(" ", 2);
        if(s.isEmpty() || args.length != 2){
            sendHelp(src);
            return CommandResult.success();
        }
        if(!args[0].equalsIgnoreCase("gamemode")
                || !args[0].equalsIgnoreCase("animals")
                || !args[0].equalsIgnoreCase("monsters")
                || !args[0].equalsIgnoreCase("pvp")
                || !args[0].equalsIgnoreCase("weather")){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[0], " is not a valid modifier!"));
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("gamemode")){
            if(!args[1].equalsIgnoreCase("creative") || !args[1].equalsIgnoreCase("survivial") || !args[1].equalsIgnoreCase("adventure")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[1], "is not a valid value!"));
                return CommandResult.success();
            }
            if(args[1].equalsIgnoreCase("creative")){
                World world = player.getWorld();
                world.getProperties().setGameMode(GameModes.CREATIVE);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "world updated successfully."));
                return CommandResult.success();
            }
            if(args[1].equalsIgnoreCase("adventure")){
                World world = player.getWorld();
                world.getProperties().setGameMode(GameModes.ADVENTURE);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "world updated successfully."));
                return CommandResult.success();
            }
            if(args[1].equalsIgnoreCase("survival")){
                World world = player.getWorld();
                world.getProperties().setGameMode(GameModes.SURVIVAL);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "world updated successfully."));
                return CommandResult.success();
            }
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("monsters")){
            if(!isBoolean(args[1])){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[1], "is not a valid value!"));
                return CommandResult.success();
            }
            if(args[1].equalsIgnoreCase("true")){
                //TODO: Add disable/enable mobs.
                MultiWorld.getInstance().getConfigNode().getNode("multiworld", player.getWorld().getProperties().getWorldName(), "mobs").setValue(true);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "world updated successfully."));
                return CommandResult.success();
            }
            if(args[1].equalsIgnoreCase("false")){
                MultiWorld.getInstance().getConfigNode().getNode("multiworld", player.getWorld().getProperties().getWorldName(), "mobs").setValue(false);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "world updated successfully."));
                return CommandResult.success();
            }
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("animals")){
            if(!isBoolean(args[1])){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[1], "is not a valid value!"));
                return CommandResult.success();
            }
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("pvp")){
            if(!isBoolean(args[1])){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[1], "is not a valid value!"));
                return CommandResult.success();
            }
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("weather")){
            if(!args[1].equalsIgnoreCase("clear") || !args[1].equalsIgnoreCase("sunny") || !args[1].equalsIgnoreCase("rainy") || !args[1].equalsIgnoreCase("cloudy") || !args[1].equalsIgnoreCase("stormy")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[1], "is not a valid value!"));
                return CommandResult.success();
            }
            //TOOD: Add weather modifications.
            return CommandResult.success();
        }
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
    public void sendHelp(CommandSource source){
        source.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }

    public boolean isBoolean(String argument){
        if(argument.equalsIgnoreCase("true") || argument.equalsIgnoreCase("false")){
            return true;
        }else{
            return false;
        }
    }
}
