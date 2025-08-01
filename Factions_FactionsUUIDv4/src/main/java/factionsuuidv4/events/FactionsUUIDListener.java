package factionsuuidv4.events;

import cc.javajobs.factionsbridge.bridge.events.*;
import dev.kitteh.factions.FPlayer;
import dev.kitteh.factions.event.LandClaimEvent;
import dev.kitteh.factions.event.LandUnclaimAllEvent;
import dev.kitteh.factions.event.LandUnclaimEvent;
import factionsuuidv4.FactionsUUIDClaim;
import factionsuuidv4.FactionsUUIDFPlayer;
import factionsuuidv4.FactionsUUIDFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * FactionsUUID implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 09:02
 */
public class FactionsUUIDListener implements Listener {

    /**
     * Listener for the {@link LandClaimEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull LandClaimEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                new FactionsUUIDClaim(event.getLocation()),
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getFPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link dev.kitteh.factions.event.FPlayerJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull dev.kitteh.factions.event.FPlayerJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getFPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        if (event.isCancellable()) {
            event.setCancelled(bridgeEvent.isCancelled());
        }
    }

    /**
     * Listener for the {@link dev.kitteh.factions.event.FPlayerLeaveEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull dev.kitteh.factions.event.FPlayerLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getFPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        if (event.isCancellable()) {
            event.setCancelled(bridgeEvent.isCancelled());
        }
    }

    /**
     * Listener for the {@link LandUnclaimAllEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimAllEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaimAll(@NotNull LandUnclaimAllEvent event) {
        FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getFPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link LandUnclaimEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull LandUnclaimEvent event) {
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                new FactionsUUIDClaim(event.getLocation()),
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getFPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link dev.kitteh.factions.event.FactionCreateEvent}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onFactionCreate(@NotNull dev.kitteh.factions.event.FactionCreateEvent event) {
        FPlayer fPlayer = event.getFPlayer();
        if (fPlayer == null) { // Plugin-created, not fitting API spec for bridge event
            return;
        }
        FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(fPlayer),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link dev.kitteh.factions.event.FactionDisbandEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onFactionDisband(@NotNull dev.kitteh.factions.event.FactionDisbandEvent event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                new FactionsUUIDFPlayer(event.getFPlayer()),
                new FactionsUUIDFaction(event.getFaction()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link dev.kitteh.factions.event.FactionRenameEvent}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull dev.kitteh.factions.event.FactionRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                new FactionsUUIDFaction(event.getFaction()),
                event.getFactionTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
