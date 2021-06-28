package cc.javajobs.factionsbridge.bridge.impl.legacyfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.event.*;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getScheduler;

/**
 * Listener to bridge Plugin-Events from LegacyFactions to the FactionsBridge.
 *
 * @author Callum Johnson
 * @since 04/05/2021 - 10:13
 */
public class LegacyFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link EventFactionsCreate}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull EventFactionsCreate event) {
        getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    api.getFPlayer(event.getFPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }, 20L);
    }

    /**
     * Listener for the {@link EventFactionsDisband}.
     * <p>
     *     This listener calls the {@link EventFactionsDisband}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull EventFactionsDisband event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getFPlayer().getPlayer()),
                api.getFaction(event.getFaction().getId()),
                FactionDisbandEvent.DisbandReason.fromString(event.getReason().name()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link EventFactionsLandChange}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.<br>
     *     This listener calls the {@link FactionUnclaimEvent}.<br>
     *     This listener calls the {@link FactionUnclaimAllEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaimAndUnclaim(@NotNull EventFactionsLandChange event) {
        final List<FactionEvent> eventList = new ArrayList<>();
        for (final Map.Entry<Locality, Faction> entry : event.transactions().entrySet()) {
            if (
                    event.getCause().equals(EventFactionsLandChange.LandChangeCause.Claim) &&
                    event.getFPlayer().getFaction().equals(entry.getValue())
            ) {
                eventList.add(new FactionClaimEvent(
                        api.getClaim(entry.getKey().getLocation()),
                        api.getFaction(entry.getValue().getId()),
                        api.getFPlayer(event.getFPlayer().getPlayer()),
                        event
                ));
            } else if (
                    event.getCause().equals(EventFactionsLandChange.LandChangeCause.Unclaim) &&
                    !event.getFPlayer().getFaction().equals(entry.getValue())
            ) {
                eventList.add(new FactionUnclaimEvent(
                        api.getClaim(entry.getKey().getLocation()),
                        api.getFaction(entry.getValue().getId()),
                        api.getFPlayer(event.getFPlayer().getPlayer()),
                        event
                ));
            }
        }
        final long amount = eventList.stream().filter(e -> e instanceof FactionUnclaimEvent).count();
        final long claims = event.getFPlayer().getFaction().getAllClaims().size();
        if (amount == claims) { // Unclaim all
            eventList.removeIf(e -> e instanceof FactionUnclaimEvent);
            eventList.add(new FactionUnclaimAllEvent(
                    api.getFaction(event.getFPlayer().getFaction().getId()),
                    api.getFPlayer(event.getFPlayer().getPlayer()),
                    event
            ));
        }
        eventList.forEach(bridgeEvent -> getPluginManager().callEvent(bridgeEvent));
        if (eventList.stream().anyMatch(FactionEvent::isCancelled)) {
            event.setCancelled(true);
            eventList.forEach(ev -> ev.setCancelled(true));
        }
    }

    /**
     * Listener for the {@link EventFactionsChange}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.<br>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onChange(@NotNull EventFactionsChange event) {
        final FactionJoinEvent bridgeEvent_1 = new FactionJoinEvent(
                api.getFaction(event.getFactionNew().getId()),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                event
        );
        final FactionLeaveEvent bridgeEvent_2 = new FactionLeaveEvent(
                api.getFaction(event.getFactionOld().getId()),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        getPluginManager().callEvent(bridgeEvent_1);
        event.setCancelled(bridgeEvent_1.isCancelled());
        getPluginManager().callEvent(bridgeEvent_2);
        event.setCancelled(bridgeEvent_2.isCancelled());
        bridgeEvent_1.setCancelled(bridgeEvent_2.isCancelled());
    }

    /**
     * Listener for the {@link EventFactionsNameChange}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull EventFactionsNameChange event) {
        final FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getFactionTag(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
