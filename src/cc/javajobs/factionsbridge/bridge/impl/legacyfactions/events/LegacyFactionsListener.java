package cc.javajobs.factionsbridge.bridge.impl.legacyfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.LegacyFactionsClaim;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.LegacyFactionsFaction;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.LegacyFactionsPlayer;
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

    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onCreate(EventFactionsCreate event) {
        Bukkit.getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    new LegacyFactionsPlayer(event.getFPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }, 20L);
    }

    @EventHandler
    public void onDisband(EventFactionsDisband event) {
        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                new LegacyFactionsPlayer(event.getFPlayer()),
                new LegacyFactionsFaction(event.getFaction()),
                IFactionDisbandEvent.DisbandReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onClaimAndUnclaim(EventFactionsLandChange event) {
        List<IFactionEvent> eventList = new ArrayList<>();
        for (Map.Entry<Locality, Faction> entry : event.transactions().entrySet()) {
            if (
                    event.getCause().equals(EventFactionsLandChange.LandChangeCause.Claim) &&
                    event.getFPlayer().getFaction().equals(entry.getValue())
            ) {
                eventList.add(new IClaimClaimEvent(
                        new LegacyFactionsClaim(entry.getKey()),
                        new LegacyFactionsFaction(entry.getValue()),
                        new LegacyFactionsPlayer(event.getFPlayer()),
                        event
                ));
            } else if (
                    event.getCause().equals(EventFactionsLandChange.LandChangeCause.Unclaim) &&
                    !event.getFPlayer().getFaction().equals(entry.getValue())
            ) {
                eventList.add(new IClaimUnclaimEvent(
                        new LegacyFactionsClaim(entry.getKey()),
                        new LegacyFactionsFaction(event.getFPlayer().getFaction()),
                        new LegacyFactionsPlayer(event.getFPlayer()),
                        event
                ));
            }
        }
        long amount = eventList.stream().filter(e -> e instanceof IClaimUnclaimEvent).count();
        long claims = event.getFPlayer().getFaction().getAllClaims().size();
        if (amount == claims) { // Unclaim all
            eventList.removeIf(e -> e instanceof IClaimUnclaimEvent);
            eventList.add(new IClaimUnclaimAllEvent(
                    new LegacyFactionsFaction(event.getFPlayer().getFaction()),
                    new LegacyFactionsPlayer(event.getFPlayer()),
                    event
            ));
        }
        eventList.forEach(bridgeEvent -> {
            Bukkit.getPluginManager().callEvent(bridgeEvent);
        });
        if (eventList.stream().anyMatch(IFactionEvent::isCancelled)) {
            event.setCancelled(true);
            eventList.forEach(ev -> ev.setCancelled(true));
        }
    }

    @EventHandler
    public void onChange(EventFactionsChange event) {
        IFactionPlayerJoinIFactionEvent bridgeEvent_1 = new IFactionPlayerJoinIFactionEvent(
                new LegacyFactionsFaction(event.getFactionNew()),
                new LegacyFactionsPlayer(event.getFPlayer()),
                event
        );
        IFactionPlayerLeaveIFactionEvent bridgeEvent_2 = new IFactionPlayerLeaveIFactionEvent(
                new LegacyFactionsFaction(event.getFactionOld()),
                new LegacyFactionsPlayer(event.getFPlayer()),
                IFactionPlayerLeaveIFactionEvent.LeaveReason.fromString(event.getReason().name()),
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
        IFactionRenameEvent bridgeEvent = new IFactionRenameEvent(
                new LegacyFactionsFaction(event.getFaction()),
                event.getFactionTag(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
