package net.huntersharpe.multiworld.subcommands;

import com.google.common.base.Optional;
import net.huntersharpe.multiworld.WorldHandler;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;

import java.util.Collections;
import java.util.List;

/**
 * Created by user on 7/26/2015.
 */
public class Create implements CommandCallable{

    private final Optional<Text> desc = Optional.of((Text) Texts.of("Multi World create Command."));
    private final Optional<Text> help = Optional.of((Text) Texts.of("Used to create worlds."));
    private final Text usage = (Text) Texts.of("<name> [type] [seed]");

    public CommandResult process(CommandSource src, String s) throws CommandException {
        if(!(src instanceof Player)){
            sendHelp(src);
        }
        Player player = ((Player) src).getPlayer().get();
        String[] args = s.split(" ");
        if(args.length > 3 || args.length == 0){
            sendHelp(src);
            return CommandResult.success();
        }
        if(args.length == 1){
            WorldHandler.getInstance().createDimension(player, args[1]);
            return CommandResult.success();
        }else if(args.length == 2){
            //TODO: Add flat world.
            if(!args[2].equalsIgnoreCase("normal") || !args[2].equalsIgnoreCase("end") || !args[2].equalsIgnoreCase("nether")){
                sendHelp(player);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[2], " is not a valid dimension type!"));
                return CommandResult.success();
            }else{
                if(args[2].equalsIgnoreCase("normal")){
                    DimensionType type = DimensionTypes.OVERWORLD;
                    WorldHandler.getInstance().createDimension(player, args[1], type);
                    return CommandResult.success();
                }
                if(args[2].equalsIgnoreCase("nether")){
                    DimensionType type = DimensionTypes.NETHER;
                    WorldHandler.getInstance().createDimension(player, args[1], type);
                    return CommandResult.success();
                }
                if(args[2].equalsIgnoreCase("end")){
                    DimensionType type = DimensionTypes.END;
                    WorldHandler.getInstance().createDimension(player, args[1], type);
                    CommandResult.success();
                }
                return CommandResult.success();
            }
        } else if(args.length == 3){
            if(!args[2].equalsIgnoreCase("normal") || !args[2].equalsIgnoreCase("end") || !args[2].equalsIgnoreCase("nether")){
                sendHelp(player);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[2], " is not a valid dimension type!"));
                return CommandResult.success();
            }
            if(!isNumeric(args[3])){
                sendHelp(player);
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, args[3], " is not a valid seed!"));
                return CommandResult.success();
            }
            if(args[2].equalsIgnoreCase("normal")){
                DimensionType type = DimensionTypes.OVERWORLD;
                long seed = Long.parseLong(args[3]);
                WorldHandler.getInstance().createDimension(player, args[1], type, seed);
                return CommandResult.success();
            }
            if(args[2].equalsIgnoreCase("nether")){
                DimensionType type = DimensionTypes.NETHER;
                long seed = Long.parseLong(args[3]);
                WorldHandler.getInstance().createDimension(player, args[1], type, seed);
                return CommandResult.success();
            }
            if(args[2].equalsIgnoreCase("end")){
                DimensionType type = DimensionTypes.END;
                long seed = Long.parseLong(args[3]);
                WorldHandler.getInstance().createDimension(player, args[1], type, seed);
                CommandResult.success();
            }
            return CommandResult.success();
        }
        return CommandResult.success();
    }

    public boolean testPermission(CommandSource source) {
        return source.hasPermission("multiworld.use.create");
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
