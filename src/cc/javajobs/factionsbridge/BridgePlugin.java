package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.bridge.commands.About;
import cc.javajobs.factionsbridge.util.ACommand;
import cc.javajobs.factionsbridge.util.Communicator;
import cc.javajobs.factionsbridge.util.Updater;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * The Bridge Plugin is a plugin implementation of FactionsBridge,
 * this allows developers to build plugins without shading.
 *
 * @author Callum Johnson
 * @since 17/04/2021 - 09:00
 */
public class BridgePlugin extends JavaPlugin implements Communicator, CommandExecutor {

    /**
     * Commands for the Bridge Plugin.
     */
    private final ACommand[] commands = new ACommand[] {
            new About()
    };

    /**
     * Method which is called by {@link JavaPlugin} methods when loading the plugin.
     */
    public void onEnable() {
        FactionsBridge bridge = new FactionsBridge();
        bridge.connect(this);
        try {
            Objects.requireNonNull(getCommand("factionsbridge")).setExecutor(this);
        } catch (Exception e) {
            exception(e, "Callum is an idiot.");
        }
        if (!FactionsBridge.get().connected()) {
            log("FactionsBridge didn't connect.");
            return;
        }

        // API test.
        int loaded = FactionsBridge.getFactionsAPI().getFactions().size();
        warn(loaded + " factions have been loaded.");

        // Check for updates.
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            log("Checking for Updates.");
            try {
                Updater updater = new Updater(getDescription().getVersion());
                switch (updater.getStatus()) {
                    case BETA:
                        log("You're using a Beta version of FactionsBridge.");
                        break;
                    case UP_TO_DATE:
                        log("You're all up to date. No issues here!");
                        break;
                    case OUT_OF_DATE_MAJOR:
                        error("The version of FactionsBridge you're currently using is majorly out of date.");
                        break;
                    case OUT_OF_DATE_MINOR:
                        warn("The version of FactionsBridge you're currently using is out of date.");
                        break;
                    case OUT_OF_DATE_MINI:
                        warn("The version of FactionsBridge you're currently using is slightly out of date.");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + updater.getStatus());
                }
            } catch (Exception e) {
                warn("Couldn't check for updates.");
            }
        });

    }

    /**
     * Command handler to redirect commands to their command-handling {@link ACommand} implementation.
     *
     * @param sender who sent the command.
     * @param command which was called.
     * @param label of the command.
     * @param args to control the functionality of the command.
     * @return {@code true} upon success.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            for (ACommand aCommand : commands) {
                if (aCommand.isCommand(args[0])) {
                    String[] arguments = new String[args.length - 1];
                    System.arraycopy(args, 1, arguments, 0, args.length - 1); // Remove one entry from the beginning.
                    aCommand.execute(sender, arguments);
                    return true;
                }
            }
        }
        Arrays.stream(commands)
                .map(aCommand -> translate("&b" + aCommand.getName() + " &7- &f" + aCommand.getDescription()))
                .forEach(sender::sendMessage);
        return false;
    }

}
