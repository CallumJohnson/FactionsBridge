package cc.javajobs.factionsbridge.bridge.commands;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.Provider;
import cc.javajobs.factionsbridge.util.ACommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static cc.javajobs.factionsbridge.util.FontMetrics.obtainCenteredMessage;

/**
 * The About command shows the command-sender intrinsic values about the FactionsBridge plugin.
 * <p>
 *     This command is only registered when using the standalone project, if shaded, the command is not registered.
 * </p>
 */
public class About extends ACommand {

    /**
     * Constructor to initialise a command with the given name.
     */
    public About() {
        super("About", "Displays information about FactionsBridge.");
    }

    /**
     * Method to 'execute' the given command.
     *
     * @param sender who sent the command.
     * @param args   of the command.
     */
    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(translate("&b=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
        sender.sendMessage("");
        sender.sendMessage(obtainCenteredMessage("&fFactionsBridge &7v&b" + FactionsBridge.getVersion()));
        sender.sendMessage("");
        sender.sendMessage(translate("  &cStatus&7: " + (FactionsBridge.get().connected() ? "&aConnected" : "&cDisconnected")));
        sender.sendMessage(translate("  &cRegistered&7: " + (FactionsBridge.get().registered ? "&aYes" : "&cNo")));
        Provider provider = Provider.getFromAPI(FactionsBridge.getFactionsAPI().getClass().getName());
        if (provider == null) {
            sender.sendMessage(translate("  &cProvider&7: &cN/A"));
        } else {
            sender.sendMessage(translate("  &cProvider&7: &a" + provider.name()));
            sender.sendMessage(translate("  &cReadable Name&7: &f" + provider.fancy()));
            String authors = String.join("&7, &f", provider.getAuthors());
            final int width = 70;
            final int distance = 56;
            if (authors.length() > distance) {
                sender.sendMessage(translate("  &cDeveloper(s)&7: &f" + authors.substring(0, distance)));
                String rest = authors.substring(distance);
                while (rest.length() != 0) {
                    sender.sendMessage(translate("  " + rest.substring(0, Math.min(width, rest.length())).trim()));
                    rest = rest.substring(Math.min(width, rest.length())).trim();
                }
            } else {
                sender.sendMessage(translate("  &cDeveloper(s)&7: &f" + authors));
            }
            sender.sendMessage(translate("  &cPlugin&7: &f'" + provider.getPlugin().getName() + "'"));
        }
        sender.sendMessage("");
        sender.sendMessage(obtainCenteredMessage("&bDeveloped by &7'&fC A L L U M#4160&7' &8(&dDiscord&8)"));
        sender.sendMessage("");
        sender.sendMessage(translate("&b=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
    }

}
