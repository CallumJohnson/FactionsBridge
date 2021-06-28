package cc.javajobs.factionsbridge.bridge.impl.medievalfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import dansplugins.factionsystem.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * Listener to bridge Plugin-Events from MedievalFactions to the FactionsBridge.
 */
public class MedievalFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link FactionClaimEvent}.
     * <p>
     *     This listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull FactionClaimEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent claimEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent(
                        api.getClaim(event.getChunk()),
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        getPluginManager().callEvent(claimEvent);
        event.setCancelled(claimEvent.isCancelled());
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
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        getPluginManager().callEvent(createEvent);
        event.setCancelled(createEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionDisbandEvent}.
     * <p>
     *     The listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull FactionDisbandEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent disbandEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent(
                        api.getFPlayer(event.getOfflinePlayer()),
                        api.getFaction(event.getFaction().getName()),
                        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent.DisbandReason.COMMAND,
                        event
                );
        getPluginManager().callEvent(disbandEvent);
        event.setCancelled(disbandEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionJoinEvent}.
     * <p>
     *    The listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull FactionJoinEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent joinEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent(
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        getPluginManager().callEvent(joinEvent);
        event.setCancelled(joinEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionLeaveEvent}.
     * <p>
     *     The listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull FactionLeaveEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent leaveEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent(
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent.LeaveReason.LEAVE,
                        event
                );
        getPluginManager().callEvent(leaveEvent);
        event.setCancelled(leaveEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionRenameEvent}.
     * <p>
     *     This listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull FactionRenameEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent renameEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent(
                        api.getFaction(event.getFaction().getName()),
                        event.getProposedName(),
                        event
                );
        getPluginManager().callEvent(renameEvent);
        event.setCancelled(renameEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionUnclaimEvent}.
     * <p>
     *     This listener calls the {@link cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull FactionUnclaimEvent event) {
        final cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent unclaimEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent(
                        api.getClaim(event.getChunk()),
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        getPluginManager().callEvent(unclaimEvent);
        event.setCancelled(unclaimEvent.isCancelled());
    }

}
