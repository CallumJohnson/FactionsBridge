package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.*;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

/**
 * MassiveCoreFactions implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:53
 */
public class MassiveCoreFactionsListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onBunchOfEvents(EventFactionsChunksChange event) {
        if (event.getMPlayer().getFaction().getLandCount() == 0) {
            FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                    api.getFaction(event.getMPlayer().getFaction().getId()),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        for (PS chunk : event.getChunks()) {
            FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                    api.getClaim(chunk.asBukkitChunk()),
                    api.getClaim(chunk.asBukkitChunk()).getFaction(),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }
        for (Map.Entry<PS, Faction> entry : event.getOldChunkFaction().entrySet()) {
            FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                    api.getClaim(entry.getKey().asBukkitChunk()),
                    api.getFaction(entry.getValue().getId()),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }
    }

    @EventHandler
    public void onCreate(EventFactionsCreate event) {
        FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(event.getFactionId()),
                api.getFPlayer(event.getMPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onDisband(EventFactionsDisband event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getMPlayer().getPlayer()),
                api.getFaction(event.getFactionId()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onRename(EventFactionsNameChange event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getNewName(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(EventFactionsMembershipChange event) {
        switch (event.getReason()) {
            case JOIN:
                FactionJoinEvent joinEvent = new FactionJoinEvent(
                        api.getFaction(event.getNewFaction().getId()),
                        api.getFPlayer(event.getMPlayer().getPlayer()),
                        event
                );
                Bukkit.getPluginManager().callEvent(joinEvent);
                event.setCancelled(joinEvent.isCancelled());
                return;
            case LEAVE:
            case KICK:
                FactionLeaveEvent leaveEvent = new FactionLeaveEvent(
                        api.getFaction(event.getMPlayer().getFaction().getId()),
                        api.getFPlayer(event.getMPlayer().getPlayer()),
                        FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                        event
                );
                Bukkit.getPluginManager().callEvent(leaveEvent);
                event.setCancelled(leaveEvent.isCancelled());
                return;
            default:
                break;
        }
    }

}
