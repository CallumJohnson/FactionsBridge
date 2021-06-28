package cc.javajobs.factionsbridge.bridge.impl.factionsx.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import net.prosavage.factionsx.event.FPlayerFactionJoinEvent;
import net.prosavage.factionsx.event.FactionPreClaimEvent;
import net.prosavage.factionsx.event.FactionUnClaimAllEvent;
import net.prosavage.factionsx.event.FactionUnClaimEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * FactionsX implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:23
 */
public class FactionsXListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link FactionPreClaimEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull FactionPreClaimEvent event) {
        final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionClaiming().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionUnClaimAllEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimAllEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaimAll(@NotNull FactionUnClaimAllEvent event) {
        final FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                api.getFaction(String.valueOf(event.getUnclaimingFaction().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link FactionUnClaimAllEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull FactionUnClaimEvent event) {
        final FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionUnClaiming().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link net.prosavage.factionsx.event.FactionCreateEvent}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull net.prosavage.factionsx.event.FactionCreateEvent event) {
        final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getOfflinePlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);

    }

    /**
     * Listener for the {@link net.prosavage.factionsx.event.FactionDisbandEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull net.prosavage.factionsx.event.FactionDisbandEvent event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getFPlayer().getPlayer()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link net.prosavage.factionsx.event.FactionRenameEvent}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull net.prosavage.factionsx.event.FactionRenameEvent event) {
        final FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                event.getNewTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link FPlayerFactionJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull FPlayerFactionJoinEvent event) {
        final FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link FPlayerFactionJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull FPlayerFactionJoinEvent event) {
        final FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                (event.isAdmin() ? FactionLeaveEvent.LeaveReason.KICK : FactionLeaveEvent.LeaveReason.LEAVE),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

}
