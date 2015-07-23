package net.huntersharpe.multiworld;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.*;
import org.spongepowered.api.world.gen.GeneratorPopulator;

/**
 * Created by user on 7/22/2015.
 */
public class WorldHandler {

    private Game game = MultiWorld.getInstance().getGame();

    private WorldBuilder builder = game.getRegistry().getWorldBuilder();

    private World world;

    private Text prefix = Texts.of(
            TextColors.DARK_GRAY,
            "[",
            TextColors.BLUE,
            "MultiWorld",
            TextColors.DARK_GRAY,
            "] "
    );

    private static WorldHandler instance;

    public static WorldHandler getInstance(){
        return instance;
    }

    //Create default world with custom name.
    public void createDimension(Player p, String name){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(prefix, Texts.of(TextColors.RED, "World already exists!"));
            return;
        }
        world = (World) builder.name(name).build();
        game.getServer().createWorld(world.getCreationSettings());
        sendCreated(p);
        game.getServer().loadWorld(world.getProperties());
        sendLoaded(p);
    }

    //Create world with: Name-Custom World
    public void createDimension(Player p, String name, String type){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(prefix, Texts.of(TextColors.RED, "World already exists!"));
            return;
        }
        if(type != "-t"){
            p.sendMessage(prefix, Texts.of(TextColors.RED, "Incorrect World Type! Use /mw help."));
            return;
        }
    }

    //Create world with: Name-Dimension type
    public void createDimension(Player p, String name, DimensionType type){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(prefix, Texts.of(TextColors.RED, "World already exists!"));
            return;
        }
        world = (World) builder.name(name).dimensionType(type).build();
        game.getServer().createWorld(world.getCreationSettings());
        sendCreated(p);
        game.getServer().loadWorld(world.getProperties());
        sendLoaded(p);
    }

    //Create world with: Name-Dimension-Seed
    public void createDimension(Player p, String name, DimensionType type, Long seed){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(prefix, Texts.of(TextColors.RED, "World already exists!"));
            return;
        }
        world = (World) builder.name(name).dimensionType(type).seed(seed).enabled(true).build();
        game.getServer().createWorld(world.getCreationSettings());
        sendCreated(p);
        game.getServer().loadWorld(world.getProperties());
        sendLoaded(p);
    }

    public void sendCreated(Player player){
        player.sendMessage(prefix, Texts.of(TextColors.GRAY, "Creating World..."));
        player.sendMessage(prefix, Texts.of(TextColors.GREEN, "Created!"));
    }

    public void sendLoaded(Player player){
        player.sendMessage(prefix, Texts.of(TextColors.GRAY, "Loading World..."));
        player.sendMessage(prefix, Texts.of(TextColors.GREEN, "Loaded"));
    }

}
