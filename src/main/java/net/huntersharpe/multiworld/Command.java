package net.huntersharpe.multiworld;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 7/22/2015.
 */
public class Command implements CommandCallable {

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to create/delete/modify worlds."));
    private final Text usage = (Text) Texts.of("<create|delete|modify|join|tp|help>");

    private final Server server;

    public Command(Server server){
        this.server = server;
    }

    public CommandResult process(CommandSource src, String arguments){
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of("You cannot use these subcommands."));
            return CommandResult.success();
        }
        Player player = ((Player) src).getPlayer().get();
        if(!player.hasPermission("multiworld.use")){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
            return CommandResult.success();
        }
        String[] args = arguments.toString().split(" ");
        if(args.length > 5 || args.length == 0){
            sendHelp(player);
            return CommandResult.success();
        }
        player.sendMessage(Texts.of(TextColors.BLUE, "MultiWorld version: " + MultiWorld.getInstance().mwVersion));
        return CommandResult.success();
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use");
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

    public void sendHelp(Player player){
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
    }

    public void helpMenu(Player player){

    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

}
