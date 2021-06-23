package cc.javajobs.factionsbridge.bridge.impl.kingdoms.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.general.KingdomCreateEvent;
import org.kingdoms.events.general.KingdomDisbandEvent;
import org.kingdoms.events.general.KingdomRenameEvent;
import org.kingdoms.events.lands.ClaimLandEvent;
import org.kingdoms.events.lands.UnclaimLandEvent;
import org.kingdoms.events.members.KingdomJoinEvent;
import org.kingdoms.events.members.KingdomLeaveEvent;

/**
 * Kingdoms implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:33
 */
public class KingdomsListener implements Listener {

    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(ClaimLandEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getLand().getLocation().toChunk()),
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFPlayer(event.getKingdom().getKing().getPlayer()), // Assumed.
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(UnclaimLandEvent event) {
        if (event.getKingdomPlayer().getKingdom().getLands().size() == 0) {
            FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                    api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                    api.getFPlayer(event.getKingdomPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getLand().getLocation().toChunk()),
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFPlayer(event.getKingdomPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onCreate(KingdomCreateEvent event) {
        FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFPlayer(event.getKingdom().getKing().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        if (event instanceof Cancellable) ((Cancellable)event).setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onDelete(KingdomDisbandEvent event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getKingdom().getKing().getPlayer()),
                api.getFaction(event.getKingdom().getId().toString()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onRename(KingdomRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                event.getName(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(KingdomJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFPlayer(event.getPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(KingdomLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFPlayer(event.getKingdomPlayer().getPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
