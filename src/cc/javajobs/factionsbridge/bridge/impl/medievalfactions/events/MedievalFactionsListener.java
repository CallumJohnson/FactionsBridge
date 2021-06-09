package cc.javajobs.factionsbridge.bridge.impl.medievalfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import dansplugins.factionsystem.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MedievalFactionsListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(FactionClaimEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent claimEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent(
                        api.getClaim(event.getChunk()),
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        Bukkit.getPluginManager().callEvent(claimEvent);
        event.setCancelled(claimEvent.isCancelled());
    }

    @EventHandler
    public void onCreate(FactionCreateEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent createEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent(
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        Bukkit.getPluginManager().callEvent(createEvent);
        event.setCancelled(createEvent.isCancelled());
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent disbandEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent(
                        api.getFPlayer(event.getOfflinePlayer()),
                        api.getFaction(event.getFaction().getName()),
                        cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent.DisbandReason.COMMAND,
                        event
                );
        Bukkit.getPluginManager().callEvent(disbandEvent);
        event.setCancelled(disbandEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(FactionJoinEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent joinEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent(
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        Bukkit.getPluginManager().callEvent(joinEvent);
        event.setCancelled(joinEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(FactionLeaveEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent leaveEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent(
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent.LeaveReason.LEAVE,
                        event
                );
        Bukkit.getPluginManager().callEvent(leaveEvent);
        event.setCancelled(leaveEvent.isCancelled());
    }

    @EventHandler
    public void onRename(FactionRenameEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent renameEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent(
                        api.getFaction(event.getFaction().getName()),
                        event.getProposedName(),
                        event
                );
        Bukkit.getPluginManager().callEvent(renameEvent);
        event.setCancelled(renameEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(FactionUnclaimEvent event) {
        cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent unclaimEvent =
                new cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent(
                        api.getClaim(event.getChunk()),
                        api.getFaction(event.getFaction().getName()),
                        api.getFPlayer(event.getOfflinePlayer()),
                        event
                );
        Bukkit.getPluginManager().callEvent(unclaimEvent);
        event.setCancelled(unclaimEvent.isCancelled());
    }

}
