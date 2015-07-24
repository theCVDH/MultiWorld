package net.huntersharpe.multiworld;

import com.flowpowered.math.vector.Vector3d;
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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Created by user on 7/22/2015.
 */
public class Command implements CommandExecutor {

    public CommandResult execute(CommandSource src, CommandContext arguments){
        if(!(src instanceof Player)){
            src.sendMessage(Texts.of("You cannot use these commands."));
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
        if(args[0].equalsIgnoreCase("create")){
            if(!player.hasPermission("multiworld.use.create")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
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
            } else if(args.length == 4){
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
        if(args[0].equalsIgnoreCase("delete")){
            if(!player.hasPermission("multiworld.use.delete")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            if(args.length != 2){
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
        if(args[0].equalsIgnoreCase("modify")){
            if(!player.hasPermission("multiworld.use.modify")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            //TODO: Finish Modify Command.
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("help")){
            if(!player.hasPermission("multiworld.help")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            //TODO: Finish helpMenu method and Help command.
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("tp")){
            if(!player.hasPermission("multiworld.tp")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            if(args.length != 5){
                sendHelp(player);
                return CommandResult.success();
            }
            if(!MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).isPresent()){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World does not exist!"));
                return CommandResult.success();
            }
            World world = MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).get();
            if(!isNumeric(args[1]) || !isNumeric(args[2]) || !isNumeric(args[3])){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "Not a valid location!"));
                return CommandResult.success();
            }
            double x = Double.parseDouble(args[2]);
            double y = Double.parseDouble(args[3]);
            double z = Double.parseDouble(args[4]);
            Location loc = new Location(world, x, y, z);
            player.setLocation(loc);
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Joining World: ", world.getName()));
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("join")){
            if(!player.hasPermission("multiworld.join")){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "You do not have permission!"));
                return CommandResult.success();
            }
            if(args.length != 2){
                sendHelp(player);
                return CommandResult.success();
            }
            if(!MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).isPresent()){
                player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World does not exist!"));
                return CommandResult.success();
            }
            World world = MultiWorld.getInstance().getGame().getServer().getWorld(args[1]).get();
            Vector3d vec = world.getSpawnLocation().getPosition();
            player.transferToWorld(world.getName(), vec);
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Joining World: ", world.getName()));
            return CommandResult.success();
        }
        if(args[0].equalsIgnoreCase("version")){
            player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", MultiWorld.getInstance().mwVersion));
        }
        return CommandResult.success();
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
