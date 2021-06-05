package cc.javajobs.factionsbridge.bridge.impl.factionsblue.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks.FactionsBlueTasks;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import me.zysea.factions.events.FPlayerClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * FactionsBlue implementation of the Bridges needed to handle all Custom Events.
 *
 * @see FactionsBlueTasks Other Events
 * @author Callum Johnson
 * @since 28/02/2021 - 09:36
 */
public class FactionsBlueListener implements Listener, Communicator {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(FPlayerClaimEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getClaim().asChunk()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onDisband(me.zysea.factions.events.FactionDisbandEvent event) {
        if (!(event.getSender() instanceof Player)) {
            warn("A Faction has been deleted by something other than a Player.");
            warn("This is not supported behaviour and will therefore cause issues.");
            return;
        }
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                (Player) event.getSender(),
                api.getFaction(event.getFaction().getId().toString()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
