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

    public double version = 1.1;

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

    CommandSpec mwCommandSpec = CommandSpec.builder()
            .description(Texts.of("MultiWorld Command"))
            .permission("multiword.use")
            .executor(new Command())
            //TODO: Add all children
            .child(CommandRegistry.getInstance().create, "create")
            .child(CommandRegistry.getInstance().delete, "delete")
            .child(CommandRegistry.getInstance().modify, "modify")
            .child(CommandRegistry.getInstance().version, "version")
            .child(CommandRegistry.getInstance().tp, "tp")
            .child(CommandRegistry.getInstance().join, "join")
            .child(CommandRegistry.getInstance().help, "help")
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
                config.getNode("multiworld", "version").setValue(version);
                configManager.save(config);
            }
            config = configManager.load();
        } catch (IOException e) {
            logger.warning("Default Config could not be loaded/created!");
        }
    }
}
