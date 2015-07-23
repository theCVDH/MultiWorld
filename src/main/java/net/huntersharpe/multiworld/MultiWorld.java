package net.huntersharpe.multiworld;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
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

    @Inject private Game game;

    @Inject private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader configManager;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File defaultConfig;

    private static MultiWorld instance;

    public static MultiWorld getInstance(){
        return instance;
    }

    CommandSpec mwCommandSpec = CommandSpec.builder()
            .description(Texts.of("MultiWorld Command"))
            .permission("multiword.use")
            .executor(new Command())
            .build();

    @Subscribe
    public void onPreInit(PreInitializationEvent e){
        ConfigurationNode config = null;

        try {
            if (!defaultConfig.exists()) {
                defaultConfig.createNewFile();
                config = configManager.load();
                //TODO: Add more configurable options.
                config.getNode("version").setValue(1);
                configManager.save(config);
                logger.log(Level.INFO, "Configuration Loaded.");
            }
            config = configManager.load();
            logger.log(Level.INFO, "Configuration Loaded.");
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "The default configuration could not be loaded or created!");
        }
        game.getCommandDispatcher().register(this, mwCommandSpec, "mw", "multiworld");
    }

    @Subscribe
    public void onServerStopping(ServerStoppingEvent e){

    }

    public Game getGame(){
        return game;
    }

}
