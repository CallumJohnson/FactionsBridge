package factionsuuid.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import factionsuuid.FactionsUUIDClaim;
import factionsuuid.FactionsUUIDFPlayer;
import factionsuuid.FactionsUUIDFaction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getScheduler;

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
                new FactionsUUIDFPlayer(event.getfPlayer()),
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
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getfPlayer()),
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
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getfPlayer()),
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
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDFPlayer(event.getfPlayer()),
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
                new FactionsUUIDFPlayer(event.getfPlayer()),
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
                    new FactionsUUIDFaction(event.getFaction()),
                    new FactionsUUIDFPlayer(event.getFPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            if (event instanceof Cancellable) ((Cancellable) event).setCancelled(bridgeEvent.isCancelled());
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
                new FactionsUUIDFPlayer(event.getFPlayer()),
                new FactionsUUIDFaction(event.getFaction()),
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
                new FactionsUUIDFaction(event.getFaction()),
                event.getFactionTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
