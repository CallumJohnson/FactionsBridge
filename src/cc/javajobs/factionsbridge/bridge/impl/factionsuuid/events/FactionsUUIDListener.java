package cc.javajobs.factionsbridge.bridge.impl.factionsuuid.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.UUID;

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
    public void onClaim(LandClaimEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getLocation().getChunk()),
                api.getFaction(event.getFaction().getId()),
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
    public void onJoin(com.massivecraft.factions.event.FPlayerJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                api.getFaction(event.getFaction().getId()),
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
    public void onLeave(com.massivecraft.factions.event.FPlayerLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(event.getFaction().getId()),
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
    public void onUnclaimAll(LandUnclaimAllEvent event) {
        FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                api.getFaction(event.getFaction().getId()),
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
    public void onUnclaim(LandUnclaimEvent event) {
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getLocation().getChunk()),
                api.getFaction(event.getFaction().getId()),
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
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onFactionCreate(com.massivecraft.factions.event.FactionCreateEvent event) {
        getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    api.getFPlayer(UUID.fromString(event.getFPlayer().getId())),
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
    public void onFactionDisband(com.massivecraft.factions.event.FactionDisbandEvent event) {
        FactionDisbandEvent.DisbandReason reason;
        try {
            Class<?> clazz = Class.forName("com.massivecraft.factions.event.FactionDisbandEvent.PlayerDisbandReason");
            if (!clazz.isEnum()) {
                reason = FactionDisbandEvent.DisbandReason.UNKNOWN;
            } else {
                Method getReason = event.getClass().getMethod("getReason");
                Object reasonObj = getReason.invoke(event);
                Method name = clazz.getMethod("name");
                String reasonName = (String) name.invoke(reasonObj);
                reason = FactionDisbandEvent.DisbandReason.fromString(reasonName);
            }
        } catch (ReflectiveOperationException ex) {
            reason = FactionDisbandEvent.DisbandReason.UNKNOWN;
        }

        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getPlayer()),
                api.getFaction(event.getFaction().getId()),
                reason,
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
    public void onRename(com.massivecraft.factions.event.FactionRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getFactionTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
