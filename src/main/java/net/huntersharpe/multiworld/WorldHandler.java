package net.huntersharpe.multiworld;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.*;
import org.spongepowered.api.world.gen.WorldGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemAlreadyExistsException;

/**
 * Created by user on 7/22/2015.
 */
public class WorldHandler {

    private Game game = MultiWorld.getInstance().getGame();

    private WorldBuilder builder = game.getRegistry().getWorldBuilder();

    private World world;

    private static WorldHandler instance = new WorldHandler();

    public static WorldHandler getInstance(){
        return instance;
    }

    public WorldHandler(){
        instance = this;
    }

    //Create default world with custom name.
    public void createDimension(Player p, String name, boolean flat){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World already exists!"));
            return;
        }
        GeneratorType generatorType = null;
        if(flat == true){
            generatorType = GeneratorTypes.FLAT;
        }
        builder.name(name).enabled(true).generator(generatorType).keepsSpawnLoaded(true).loadsOnStartup(true).build();
        game.getServer().createWorld(builder.buildSettings());
        sendCreated(p);
        game.getServer().loadWorld(builder.buildSettings().getWorldName());
        sendLoaded(p);
    }

    //Create world with: Name-Dimension type
    public void createDimension(Player p, String name, DimensionType type, boolean flat){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World already exists!"));
            return;
        }
        GeneratorType generatorType = null;
        if(type.equals(DimensionTypes.OVERWORLD)){
            if(flat == true){
                generatorType = GeneratorTypes.FLAT;
            }else{
                generatorType = GeneratorTypes.OVERWORLD;
            }
            generatorType = GeneratorTypes.OVERWORLD;
        }else if(type.equals(DimensionTypes.NETHER)){
            generatorType = GeneratorTypes.NETHER;
        }else if(type.equals(DimensionTypes.END)){
            generatorType = GeneratorTypes.END;
        }
        builder.name(name).dimensionType(type).enabled(true).keepsSpawnLoaded(true).loadsOnStartup(true).generator(generatorType).build();
        game.getServer().createWorld(builder.buildSettings());
        sendCreated(p);
        game.getServer().loadWorld(builder.buildSettings().getWorldName());
        sendLoaded(p);
    }

    //Create world with: Name-Dimension-Seed
    public void createDimension(Player p, String name, DimensionType type, Long seed, boolean flat){
        if(game.getServer().getWorld(name).isPresent()){
            p.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.RED, "World already exists!"));
            return;
        }
        GeneratorType generatorType = null;
        if(type.equals(DimensionTypes.OVERWORLD)){
            if(flat == true){
                generatorType = GeneratorTypes.FLAT;
            }else{
                generatorType = GeneratorTypes.OVERWORLD;
            }
            generatorType = GeneratorTypes.OVERWORLD;
        }else if(type.equals(DimensionTypes.NETHER)){
            generatorType = GeneratorTypes.NETHER;
        }else if(type.equals(DimensionTypes.END)){
            generatorType = GeneratorTypes.END;
        }
        builder.name(name).dimensionType(type).seed(seed).enabled(true).keepsSpawnLoaded(true).loadsOnStartup(true).generator(generatorType).build();
        game.getServer().createWorld(builder.buildSettings());
        sendCreated(p);
        game.getServer().loadWorld(builder.buildSettings().getWorldName());
        sendLoaded(p);
    }

    public void sendCreated(Player player){
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GRAY, "Creating World..."));
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Created!"));
    }

    public void sendLoaded(Player player){
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GRAY, "Loading World..."));
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Loaded"));
    }

    public void deleteDimension(Player player, World world) {
        game.getServer().unloadWorld(world);
        //TODO: Actually Delete world files.
        //TODO: Confirmation message & Scheduler that only allows 30 seconds to delete.
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Deleting world..."));
        player.sendMessage(Texts.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MultiWorld", TextColors.DARK_GRAY, "] ", TextColors.GREEN, "Deleted world"));
    }
    /*
    public void setConfigValues(World world, String name){
        //TODO: Fix configuration writing.
        File potentialFile = new File("../mods/bountyhunter.conf");
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setFile(potentialFile).build();
        ConfigurationNode rootNode;
        try {
            rootNode = loader.load();
        } catch(IOException e) {
            rootNode = null;
        }
        rootNode.getNode("multiworld", "worlds").setValue(name);
        rootNode.getNode("multiworld", "worlds", name).setValue("type");
        //TODO: Fix this shit.
        rootNode.getNode("multiworld", "worlds", world.getName(), "type").setValue(world.getDimension().getType().toString());
        rootNode.getNode("multiworld", "worlds", world.getName(), "seed").setValue(world.getProperties().getSeed());
        rootNode.getNode("multiworld", "worlds", world.getName(), "gamemode").setValue(world.getProperties().getGameMode().toString());
        rootNode.getNode("multiworld", "worlds", world.getName(), "difficulty").setValue(world.getProperties().getDifficulty().toString());
        rootNode.getNode("multiworld", "worlds", world.getName(), "spawn_location", "x").setValue(world.getSpawnLocation().getX());
        rootNode.getNode("multiworld", "worlds", world.getName(), "spawn_location", "y").setValue(world.getSpawnLocation().getY());
        rootNode.getNode("multiworld", "worlds", world.getName(), "spawn_location", "z").setValue(world.getSpawnLocation().getZ());

    }*/
}
