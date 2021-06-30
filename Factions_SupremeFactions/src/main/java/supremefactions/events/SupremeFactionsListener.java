package supremefactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getScheduler;

/**
 * SupremeFactions implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 29/06/2021 - 10:52
 */
public class SupremeFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

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
                api.getClaim(event.getLocation().getChunk()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                api.getFPlayer(UUID.fromString(event.getfPlayer().getId())),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link com.massivecraft.factions.event.FPlayerJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull com.massivecraft.factions.event.FPlayerJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                api.getFPlayer(UUID.fromString(event.getfPlayer().getId())),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link com.massivecraft.factions.event.FPlayerLeaveEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull com.massivecraft.factions.event.FPlayerLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                api.getFPlayer(UUID.fromString(event.getfPlayer().getId())),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
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
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                api.getFPlayer(UUID.fromString(event.getfPlayer().getId())),
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
                api.getClaim(event.getLocation().getChunk()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                api.getFPlayer(UUID.fromString(event.getfPlayer().getId())),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link com.massivecraft.factions.event.FactionCreateEvent}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onFactionCreate(@NotNull com.massivecraft.factions.event.FactionCreateEvent event) {
        getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                    Objects.requireNonNull(api.getFactionByTag(event.getFactionTag())),
                    api.getFPlayer(UUID.fromString(event.getFPlayer().getId())),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }, 20);
    }

    /**
     * Listener for the {@link com.massivecraft.factions.event.FactionDisbandEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onFactionDisband(@NotNull com.massivecraft.factions.event.FactionDisbandEvent event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getPlayer()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link com.massivecraft.factions.event.FactionRenameEvent}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull com.massivecraft.factions.event.FactionRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getId())),
                event.getFactionTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
