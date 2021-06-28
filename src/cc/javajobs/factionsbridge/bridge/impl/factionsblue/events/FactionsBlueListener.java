package cc.javajobs.factionsbridge.bridge.impl.factionsblue.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.impl.factionsblue.tasks.FactionsBlueTasks;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.util.Communicator;
import me.zysea.factions.events.FPlayerClaimEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * FactionsBlue implementation of the Bridges needed to handle all Custom Events.
 *
 * @see FactionsBlueTasks Other Events
 * @author Callum Johnson
 * @since 28/02/2021 - 09:36
 */
public class FactionsBlueListener implements Listener, Communicator {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link FPlayerClaimEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull FPlayerClaimEvent event) {
        final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getClaim().asChunk()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link me.zysea.factions.events.FactionDisbandEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull me.zysea.factions.events.FactionDisbandEvent event) {
        if (!(event.getSender() instanceof Player)) {
            warn("A Faction has been deleted by something other than a Player.");
            warn("This is not supported behaviour and will therefore cause issues.");
            return;
        }
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer((OfflinePlayer) event.getSender()),
                api.getFaction(event.getFaction().getId().toString()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
