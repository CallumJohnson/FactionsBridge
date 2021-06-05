package cc.javajobs.factionsbridge.bridge.impl.factionsx.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import net.prosavage.factionsx.event.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * FactionsX implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:23
 */
public class FactionsXListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(FactionPreClaimEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionClaiming().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaimAll(FactionUnClaimAllEvent event) {
        FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                api.getFaction(String.valueOf(event.getUnclaimingFaction().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(FactionUnClaimEvent event) {
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionUnClaiming().getId())),
                api.getFPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onCreate(net.prosavage.factionsx.event.FactionCreateEvent event) {
        FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getOfflinePlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);

    }

    @EventHandler
    public void onDisband(net.prosavage.factionsx.event.FactionDisbandEvent event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getFPlayer().getPlayer()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onRename(net.prosavage.factionsx.event.FactionRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                event.getNewTag(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onJoin(FPlayerFactionJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onLeave(FPlayerFactionJoinEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(String.valueOf(event.getFaction().getId())),
                api.getFPlayer(event.getFPlayer().getPlayer()),
                (event.isAdmin() ? FactionLeaveEvent.LeaveReason.KICK : FactionLeaveEvent.LeaveReason.LEAVE),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

}
