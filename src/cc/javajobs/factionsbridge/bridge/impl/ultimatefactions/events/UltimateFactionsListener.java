package cc.javajobs.factionsbridge.bridge.impl.ultimatefactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import de.miinoo.factions.events.FactionCreateEvent;
import de.miinoo.factions.events.FactionDisbandEvent;
import de.miinoo.factions.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * Listener to bridge Plugin-Events from UltimateFactions to the FactionsBridge.
 */
public class UltimateFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link FactionChangeNameEvent}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull FactionChangeNameEvent event) {
        final FactionRenameEvent renameEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId().toString()),
                event.getNewName(),
                event
        );
        getPluginManager().callEvent(renameEvent);
    }

    /**
     * Listener for the {@link FactionClaimChunkEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull FactionClaimChunkEvent event) {
        final FactionClaimEvent claimEvent = new FactionClaimEvent(
                api.getClaim(event.getChunk()),
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(claimEvent);
    }

    /**
     * Listener for the {@link FactionCreateEvent}.
     * <p>
     *     This listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull FactionCreateEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent createEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent(
                        api.getFaction(event.getFaction().getId().toString()),
                        api.getFPlayer(event.getPlayer()),
                        event
                );
        getPluginManager().callEvent(createEvent);
    }

    /**
     * Listener for the {@link FactionDisbandEvent}.
     * <p>
     *     This listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull FactionDisbandEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent disbandEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent(
                        api.getFPlayer(event.getPlayer()),
                        api.getFaction(event.getFaction().getId().toString()),
                        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent.DisbandReason.COMMAND,
                        event
                );
        getPluginManager().callEvent(disbandEvent);
    }

    /**
     * Listener for the {@link FactionPlayerJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull FactionPlayerJoinEvent event) {
        final FactionJoinEvent joinEvent = new FactionJoinEvent(
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(joinEvent);
    }

    /**
     * Listener for the {@link FactionPlayerLeaveEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull FactionPlayerLeaveEvent event) {
        final FactionLeaveEvent leaveEvent = new FactionLeaveEvent(
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                FactionLeaveEvent.LeaveReason.LEAVE,
                event
        );
        getPluginManager().callEvent(leaveEvent);
    }

    /**
     * Listener for the {@link FactionUnclaimChunkEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull FactionUnclaimChunkEvent event) {
        final FactionUnclaimEvent unclaimEvent = new FactionUnclaimEvent(
                api.getClaim(event.getChunk()),
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(unclaimEvent);
    }

}
