package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.util.Communicator;
import cc.javajobs.factionsbridge.util.Updater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Callum Johnson
 * @since 17/04/2021 - 09:00
 */
public class BridgePlugin extends JavaPlugin implements Communicator {

    public void onEnable() {
        FactionsBridge bridge = new FactionsBridge();
        bridge.connect(this);

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

}
