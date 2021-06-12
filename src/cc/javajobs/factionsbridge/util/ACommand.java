package cc.javajobs.factionsbridge.util;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Callum Johnson
 * @since 12/06/2021 - 12:11
 */
public abstract class ACommand implements Communicator {

    private final String name;
    private final String description;

    /**
     * Constructor to initialise a command with the given name.
     *
     * @param name of the command.
     */
    public ACommand(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Method to determine if a given string is the command.
     *
     * @param query to test.
     * @return {@code true} if it is.
     */
    public boolean isCommand(@NotNull String query) {
        return name.equalsIgnoreCase(query);
    }

    /**
     * Method to get the name of the command.
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the description of the command.
     * @return string desc
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to 'execute' the given command.
     *
     * @param sender who sent the command.
     * @param args of the command.
     */
    public abstract void execute(@NotNull CommandSender sender, @NotNull String[] args);

}
