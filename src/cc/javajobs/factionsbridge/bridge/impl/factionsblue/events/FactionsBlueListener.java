package cc.javajobs.factionsbridge.bridge.impl.factionsblue.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.IClaimClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.IFactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks.FactionsBlueTasks;
import cc.javajobs.factionsbridge.util.Communicator;
import me.zysea.factions.events.FPlayerClaimEvent;
import me.zysea.factions.events.FactionDisbandEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * FactionsBlue implementation of the Bridges needed to handle all Custom Events.
 *
 * @see FactionsBlueTasks Other Events
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 09:36
 */
public class FactionsBlueListener implements Listener, Communicator {

    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(FPlayerClaimEvent event) {
        IClaimClaimEvent bridgeEvent = new IClaimClaimEvent(
                api.getClaimAt(event.getClaim().asChunk()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFactionPlayer(event.getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        if (!(event.getSender() instanceof Player)) {
            warn("A Faction has been deleted by something other than a Player.");
            warn("This is not supported behaviour and will therefore cause issues.");
            return;
        }
        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                (Player) event.getSender(),
                api.getFaction(event.getFaction().getId().toString()),
                IFactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

}
