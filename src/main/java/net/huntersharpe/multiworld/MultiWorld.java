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
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.ConfigDir;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandMapping;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.util.command.spec.CommandSpec;

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
    private File defaultConf;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Game game;

    private ConfigurationNode config = null;

    private static MultiWorld instance = new MultiWorld();

    public MultiWorld(){
        instance = this;
    }

    public static MultiWorld getInstance(){
        return instance;
    }
    /*
    // /mw create <name> [type] [seed] Command
    CommandSpec create = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .executor(new Command())
            .arguments(GenericArguments.seq(
                    GenericArguments.string(Texts.of("name")),
                    GenericArguments.dimension(Texts.of("type"), game),
                    GenericArguments.integer(Texts.of("seed"))
            ))
            .build();
    // /mw delete <name> command
    CommandSpec delete = CommandSpec.builder()
            .description(Texts.of("Delete a world"))
            .executor(new Command())
            .arguments(GenericArguments.string(Texts.of("name")))
            .build();
    // /mw help command
    CommandSpec help = CommandSpec.builder()
            .description(Texts.of("MultiWorld help Command"))
            .executor(new Command())
            .build();
    // /mw modify <type> <value> command
    Map<String, Boolean> choices = new HashMap<String, Boolean>();
    CommandSpec modify = CommandSpec.builder()
            .description(Texts.of("Modify your current world"))
            .executor(new Command())
            .arguments(GenericArguments.seq(GenericArguments.string(Texts.of("type")), GenericArguments.choices(Texts.of("value"), choices)))
            .build();
    // /mw version command
    CommandSpec version = CommandSpec.builder()
            .description(Texts.of("MultiWorld version"))
            .executor(new Command())
            .build();
    // /tp <world> <x> <y> <z> command.
    CommandSpec tp = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .executor(new Command())
            .arguments(GenericArguments.seq(GenericArguments.world(Texts.of("world"), game), GenericArguments.integer(Texts.of("x")), GenericArguments.integer(Texts.of("y")), GenericArguments.integer(Texts.of("z"))))
            .build();
    // /join <world> command
    CommandSpec join = CommandSpec.builder()
            .description(Texts.of("Join a world at its spawn."))
            .executor(new Command())
            .arguments(GenericArguments.world(Texts.of("world"), game))
            .build();
    CommandSpec mwCommandSpec = CommandSpec.builder()
            .description(Texts.of("MultiWorld Command"))
            .executor(new Command())
            /*.child(create, "create")
            .child(delete, "delete")
            .child(modify, "modify")
            .child(version, "version")
            .child(tp, "tp")
            .child(join, "join")
            .child(help, "help")
            .build();*/

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
        logger.info("Multi World Loaded!");
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
                configManager.save(config);
            }
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }
}
