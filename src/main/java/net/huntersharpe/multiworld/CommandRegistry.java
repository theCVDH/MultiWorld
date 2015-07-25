package net.huntersharpe.multiworld;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 7/25/2015.
 */
public class CommandRegistry {

    public static CommandRegistry registry;

    public static CommandRegistry getRegistry(){
        return registry;
    }

    // /mw create <name> [type] [seed] Commad
    CommandSpec create = CommandSpec.builder()
            .description(Texts.of("Create a world"))
            .executor(new Command())
            .arguments(GenericArguments.seq(
                    GenericArguments.string(Texts.of("name")),
                    GenericArguments.dimension(Texts.of("type"), MultiWorld.getInstance().getGame()),
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
            .arguments(GenericArguments.seq(GenericArguments.world(Texts.of("world"), MultiWorld.getInstance().getGame()), GenericArguments.integer(Texts.of("x")), GenericArguments.integer(Texts.of("y")), GenericArguments.integer(Texts.of("z"))))
            .build();
    // /join <world> command
    CommandSpec join = CommandSpec.builder()
            .description(Texts.of("Join a world at its spawn."))
            .executor(new Command())
            .arguments(GenericArguments.world(Texts.of("world"), MultiWorld.getInstance().getGame()))
            .build();
    CommandSpec mwCommandSpec = CommandSpec.builder()
            .description(Texts.of("MultiWorld Command"))
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

}
