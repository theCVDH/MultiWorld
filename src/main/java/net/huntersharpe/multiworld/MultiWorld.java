package net.huntersharpe.multiworld;

import com.google.inject.Inject;
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
import org.spongepowered.api.util.command.spec.CommandSpec;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by user on 7/22/2015.
 */
@Plugin(id = "multiworld", name = "MultiWorld", version = "1.0")
public class MultiWorld {

    public double mwVersion = 1.4;

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

    private static MultiWorld instance;

    public static MultiWorld getInstance(){
        return instance;
    }

    // /mw create <name> [type] [seed] Commad
    CommandSpec createSeed = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .permission("multiword.use.create")
            .executor(new Command())
            .build();
    CommandSpec createType = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .permission("multiword.use.create")
            .executor(new Command())
            .child(createSeed)
            .build();
    CommandSpec createName = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .permission("multiword.use.create")
            .executor(new Command())
            .child(createType)
            .build();
    CommandSpec create = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .permission("multiword.use.create")
            .executor(new Command())
            .child(createName)
            .build();
    // /mw delete <name> command
    CommandSpec delName = CommandSpec.builder()
            .description(Texts.of("Delete a world"))
            .permission("multiword.use.delete")
            .executor(new Command())
            .build();
    CommandSpec delete = CommandSpec.builder()
            .description(Texts.of("Delete a world"))
            .permission("multiword.use.delete")
            .executor(new Command())
            .child(delName)
            .build();
    // /mw help command
    CommandSpec help = CommandSpec.builder()
            .description(Texts.of("MultiWorld help Command"))
            .permission("multiword.help")
            .executor(new Command())
            .build();
    // /mw modify <type> <value> command
    CommandSpec modValue = CommandSpec.builder()
            .description(Texts.of("Modify your current world"))
            .permission("multiword.modify")
            .executor(new Command())
            .build();
    CommandSpec modType = CommandSpec.builder()
            .description(Texts.of("Modify your current world"))
            .permission("multiworld.modify")
            .executor(new Command())
            .child(modValue)
            .build();
    CommandSpec modify = CommandSpec.builder()
            .description(Texts.of("Modify your current world"))
            .permission("multiword.modify")
            .executor(new Command())
            .child(modType)
            .build();
    // /mw version command
    CommandSpec version = CommandSpec.builder()
            .description(Texts.of("MultiWorld version"))
            .permission("multiword.version")
            .executor(new Command())
            .build();
    // /tp <world> <x> <y> <z> command.
    CommandSpec tpZ = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .permission("multiword.tp")
            .executor(new Command())
            .build();
    CommandSpec tpY = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .permission("multiword.tp")
            .executor(new Command())
            .child(tpZ)
            .build();
    CommandSpec tpX = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .permission("multiword.tp")
            .executor(new Command())
            .child(tpY)
            .build();
    CommandSpec tpWorld = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .permission("multiword.tp")
            .executor(new Command())
            .child(tpX)
            .build();
    CommandSpec tp = CommandSpec.builder()
            .description(Texts.of("Teleport to a world at an exact location"))
            .permission("multiword.tp")
            .executor(new Command())
            .child(tpWorld)
            .build();
    // /join <world> command
    CommandSpec joinWorld = CommandSpec.builder()
            .description(Texts.of("Join a world at its spawn."))
            .permission("multiword.join")
            .executor(new Command())
            .build();
    CommandSpec join = CommandSpec.builder()
            .description(Texts.of("Join a world at its spawn."))
            .permission("multiword.join")
            .executor(new Command())
            .child(joinWorld)
            .build();
    CommandSpec mwCommandSpec = CommandSpec.builder()
            .description(Texts.of("MultiWorld Command"))
            .permission("multiword.use")
            .executor(new Command())
            //TODO: Add all children
            .child(create, "create")
            .child(delete, "delete")
            .child(modify, "modify")
            .child(version, "version")
            .child(tp, "tp")
            .child(join, "join")
            .child(help, "help")
            .build();

    @Subscribe
    public void onPreInit(PreInitializationEvent e){
        setupConfig();
    }

    @Subscribe
    public void onInit(InitializationEvent e){
        game.getCommandDispatcher().register(this, mwCommandSpec, "mw", "multiworld");
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
