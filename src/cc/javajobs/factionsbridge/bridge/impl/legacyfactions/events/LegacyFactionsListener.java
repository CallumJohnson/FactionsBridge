package cc.javajobs.factionsbridge.bridge.impl.legacyfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.event.*;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Callum Johnson
 * @since 04/05/2021 - 10:13
 */
public class LegacyFactionsListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onCreate(EventFactionsCreate event) {
        Bukkit.getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    api.getFPlayer(event.getFPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }, 20L);
    }

    @EventHandler
    public void onDisband(EventFactionsDisband event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getFPlayer().getPlayer()),
                api.getFaction(event.getFaction().getId()),
                FactionDisbandEvent.DisbandReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onClaimAndUnclaim(EventFactionsLandChange event) {
        List<FactionEvent> eventList = new ArrayList<>();
        for (Map.Entry<Locality, Faction> entry : event.transactions().entrySet()) {
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
        long amount = eventList.stream().filter(e -> e instanceof FactionUnclaimEvent).count();
        long claims = event.getFPlayer().getFaction().getAllClaims().size();
        if (amount == claims) { // Unclaim all
            eventList.removeIf(e -> e instanceof FactionUnclaimEvent);
            eventList.add(new FactionUnclaimAllEvent(
                    api.getFaction(event.getFPlayer().getFaction().getId()),
                    api.getFPlayer(event.getFPlayer().getPlayer()),
                    event
            ));
        }
        eventList.forEach(bridgeEvent -> Bukkit.getPluginManager().callEvent(bridgeEvent));
        if (eventList.stream().anyMatch(FactionEvent::isCancelled)) {
            event.setCancelled(true);
            eventList.forEach(ev -> ev.setCancelled(true));
        }
    }

    @EventHandler
    public void onChange(EventFactionsChange event) {
        FactionJoinEvent bridgeEvent_1 = new FactionJoinEvent(
                api.getFaction(event.getFactionNew().getId()),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                event
        );
        FactionLeaveEvent bridgeEvent_2 = new FactionLeaveEvent(
                api.getFaction(event.getFactionOld().getId()),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent_1);
        event.setCancelled(bridgeEvent_1.isCancelled());
        Bukkit.getPluginManager().callEvent(bridgeEvent_2);
        event.setCancelled(bridgeEvent_2.isCancelled());
        bridgeEvent_1.setCancelled(bridgeEvent_2.isCancelled());
    }

    @EventHandler
    public void onRename(EventFactionsNameChange event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getFactionTag(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
