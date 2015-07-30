package net.huntersharpe.multiworld;

import com.google.inject.Inject;
import net.huntersharpe.multiworld.subcommands.*;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandMapping;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.util.command.spec.CommandSpec;
import org.spongepowered.api.world.World;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by user on 7/22/2015.
 */
@Plugin(id = "multiworld", name = "MultiWorld", version = "1.9")
public class MultiWorld {

    public double mwVersion = 1.9;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    public File defaultConf;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Game game;

    public ConfigurationNode config = null;

    private static MultiWorld instance = new MultiWorld();

    public MultiWorld(){
        instance = this;
    }

    public static MultiWorld getInstance(){
        return instance;
    }

    @Subscribe
    public void onPreInit(PreInitializationEvent e){
        setupConfig();
    }



    @Subscribe
    public void onInit(InitializationEvent e){
        CommandCallable create = new Create();
        CommandCallable delete = new Delete();
        CommandCallable modify = new Modify();
        CommandCallable join = new Join();
        CommandCallable tp = new Tp();
        CommandCallable list = new List();
        CommandCallable help = new Help();
        CommandCallable info = new Info();
        SimpleDispatcher mw = new SimpleDispatcher();
        mw.register(create, "create");
        mw.register(delete, "delete", "del");
        mw.register(modify, "modify");
        mw.register(join, "join");
        mw.register(tp, "tp");
        mw.register(list, "list");
        mw.register(help, "help");
        mw.register(info, "info");
        game.getCommandDispatcher().register(this, mw, "mw", "multiworld");
    }

    @Subscribe
    public void onServerStart(ServerStartingEvent e){
        for(World world : game.getServer().getWorlds()){
            game.getServer().loadWorld(world.getProperties().getWorldName());
        }
        logger.info("Multi World Loaded!");
    }

    @Subscribe
    public void onServerStopping(ServerStoppingEvent e){
        for(World world : game.getServer().getWorlds()){
            String name = world.getProperties().getWorldName();
            deleteLockFiles(name);
        }
    }

    public Game getGame(){
        return game;
    }

    private void setupConfig() {
        try {
            if (!defaultConf.getParentFile().exists()) {
                defaultConf.getParentFile().mkdir();
            }
            if (!defaultConf.exists()) {
                defaultConf.createNewFile();
                config = configManager.load();

                //TODO: Add config values
                config.getNode("multiworld", "version").setValue(mwVersion);
                config.getNode("multiworld", "worlds").setValue("");
                configManager.save(config);
            }
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }
    public void deleteLockFiles(String name){
        File lockFile = null;
        try{
            lockFile = new File("../world/" + name + "/session.lock");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(lockFile.exists()){
            lockFile.delete();
        }
    }

    public ConfigurationNode getConfigNode(){
        return config;
    }
}
