package net.huntersharpe.multiworld;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.spec.CommandSpec;

/**
 * Created by user on 7/24/2015.
 */
public class CommandRegistry {

    private static String[] worlds = MultiWorld.getInstance().getGame().getServer().getWorlds().toString().split(" ");

    private static CommandRegistry instance;

    public static CommandRegistry getInstance(){
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
}
