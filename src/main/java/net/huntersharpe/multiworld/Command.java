package net.huntersharpe.multiworld;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;

/**
 * Created by user on 7/22/2015.
 */
public class Command implements CommandExecutor {

    private Text prefix = Texts.of(
            TextColors.DARK_GRAY,
            "[",
            TextColors.BLUE,
            "MultiWorld",
            TextColors.DARK_GRAY,
            "] "
    );

    public CommandResult execute(CommandSource src, CommandContext arguments){
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of("You cannot use these commands."));
            return CommandResult.success();
        }
        Player player = (Player)((Player) src).getPlayer();
        if(!player.hasPermission("multiworld.use")){
            player.sendMessage(prefix, Texts.of(TextColors.RED, "You do not have permission!"));
            return CommandResult.success();
        }
        String[] args = arguments.toString().split(" ");
        if(args.length > 4 || args.length == 0){
            sendHelp(player);
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("create")){
            if(!player.hasPermission("multiworld.use.create")){
                player.sendMessage(prefix, Texts.of(TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            if(args.length > 4){
                sendHelp(player);
                return CommandResult.success();
            }else if(args.length == 2){
                WorldHandler.getInstance().createDimension(player, args[1]);
                return CommandResult.success();
            }else if(args.length == 3){
                //TODO: Add flat world.
                if(!args[2].equalsIgnoreCase("normal") || !args[2].equalsIgnoreCase("end") || !args[2].equalsIgnoreCase("nether")){
                    sendHelp(player);
                    player.sendMessage(prefix, Texts.of(TextColors.RED, args[2], " is not a valid dimension type!"));
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
            } else if(args.length == 4){
                if(!args[2].equalsIgnoreCase("normal") || !args[2].equalsIgnoreCase("end") || !args[2].equalsIgnoreCase("nether")){
                    sendHelp(player);
                    player.sendMessage(prefix, Texts.of(TextColors.RED, args[2], " is not a valid dimension type!"));
                    return CommandResult.success();
                }
                if(!isNumeric(args[3])){
                    sendHelp(player);
                    player.sendMessage(prefix, Texts.of(TextColors.RED, args[3], " is not a valid seed!"));
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
        if(args[0].equalsIgnoreCase("delete")){
            if(!player.hasPermission("multiworld.use.delete")){
                player.sendMessage(prefix, Texts.of(TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }

            //TODO Finish Delete Command.
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("modify")){
            if(!player.hasPermission("multiworld.use.modify")){
                player.sendMessage(prefix, Texts.of(TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            //TODO: Finish Modify Command.
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("help")){
            if(!player.hasPermission("multiworld.help")){
                player.sendMessage(prefix, Texts.of(TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            //TODO: Finish helpMenu method and Help command.
            return CommandResult.success();
        }
        return CommandResult.success();
    }

    public void sendHelp(Player player){
        player.sendMessage(prefix, Texts.of(TextColors.RED, "Incorrect Usage! Use /mw help for more info."));
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
