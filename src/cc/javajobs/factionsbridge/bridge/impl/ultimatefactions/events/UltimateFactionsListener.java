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

public class UltimateFactionsListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onRename(FactionChangeNameEvent event) {
        FactionRenameEvent renameEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId().toString()),
                event.getNewName(),
                event
        );
        Bukkit.getPluginManager().callEvent(renameEvent);
    }

    @EventHandler
    public void onClaim(FactionClaimChunkEvent event) {
        FactionClaimEvent claimEvent = new FactionClaimEvent(
                api.getClaim(event.getChunk()),
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(claimEvent);
    }

    @EventHandler
    public void onCreate(FactionCreateEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent createEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent(
                        api.getFaction(event.getFaction().getId().toString()),
                        api.getFPlayer(event.getPlayer()),
                        event
                );
        Bukkit.getPluginManager().callEvent(createEvent);
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent disbandEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent(
                        api.getFPlayer(event.getPlayer()),
                        api.getFaction(event.getFaction().getId().toString()),
                        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent.DisbandReason.COMMAND,
                        event
                );
        Bukkit.getPluginManager().callEvent(disbandEvent);
    }

    @EventHandler
    public void onJoin(FactionPlayerJoinEvent event) {
        FactionJoinEvent joinEvent = new FactionJoinEvent(
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(joinEvent);
    }

    @EventHandler
    public void onLeave(FactionPlayerLeaveEvent event) {
        FactionLeaveEvent leaveEvent = new FactionLeaveEvent(
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                FactionLeaveEvent.LeaveReason.LEAVE,
                event
        );
        Bukkit.getPluginManager().callEvent(leaveEvent);
    }

    @EventHandler
    public void onUnclaim(FactionUnclaimChunkEvent event) {
        FactionUnclaimEvent unclaimEvent = new FactionUnclaimEvent(
                api.getClaim(event.getChunk()),
                api.getFaction(event.getFaction().getId().toString()),
                api.getFPlayer(event.getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(unclaimEvent);
    }

}
