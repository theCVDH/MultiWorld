package net.huntersharpe.multiworld.subcommands;

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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 7/26/2015.
 */
public class Tp implements CommandCallable {

    private final Optional<Text> desc = Optional.of((Text) Texts.of("MultiWorld TP Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to teleport to worlds."));
    private final Text usage = (Text) Texts.of("<world> <x> <y> <z>");

    public CommandResult process(CommandSource commandSource, String s) throws CommandException {
        if(!(commandSource instanceof Player)){
            return CommandResult.success();
        }
        Player player = ((Player) commandSource).getPlayer().get();
        String[] args = s.split(" ");
        if(args.length != 4){
            sendHelp(player);
            return CommandResult.success();
        }
        if(!MultiWorld.getInstance().getGame().getServer().getWorld(args[0]).isPresent()){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World does not exist!"));
            return CommandResult.success();
        }
        World world = MultiWorld.getInstance().getGame().getServer().getWorld(args[0]).get();
        if(!isNumeric(args[1]) || !isNumeric(args[2]) || !isNumeric(args[3])){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Not a valid location!"));
            return CommandResult.success();
        }
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        Location loc = new Location(world, x, y, z);
        player.setLocation(loc);
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Joining World: ", world.getName()));
        return CommandResult.success();
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use.tp");
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
