package cc.javajobs.factionsbridge;

import cc.javajobs.factionsbridge.util.Communicator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Callum Johnson
 * @since 17/04/2021 - 09:00
 */
public class BridgePlugin extends JavaPlugin implements Communicator {

    public void onEnable() {
        FactionsBridge bridge = new FactionsBridge();
        bridge.connect(this);

        // API test.
        int loaded = FactionsBridge.getFactionsAPI().getAllFactions().size();
        warn(loaded + " factions have been loaded.");

    }

}
