package cc.javajobs.factionsbridge.bridge.impl.kingdoms.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.*;
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

    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(ClaimLandEvent event) {
        IClaimClaimEvent bridgeEvent = new IClaimClaimEvent(
                api.getClaimAt(event.getLand().getLocation().toChunk()),
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()), // Assumed.
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(UnclaimLandEvent event) {
        if (event.getKingdomPlayer().getKingdom().getLands().size() == 0) {
            IClaimUnclaimAllEvent bridgeEvent = new IClaimUnclaimAllEvent(
                    api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                    api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        IClaimUnclaimEvent bridgeEvent = new IClaimUnclaimEvent(
                api.getClaimAt(event.getLand().getLocation().toChunk()),
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onCreate(KingdomCreateEvent event) {
        IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        if (event instanceof Cancellable) ((Cancellable)event).setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onDelete(KingdomDisbandEvent event) {
        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()),
                api.getFaction(event.getKingdom().getId().toString()),
                IFactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onRename(KingdomRenameEvent event) {
        IFactionRenameEvent bridgeEvent = new IFactionRenameEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                event.getName(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(KingdomJoinEvent event) {
        IFactionPlayerJoinIFactionEvent bridgeEvent = new IFactionPlayerJoinIFactionEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFactionPlayer(event.getPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(KingdomLeaveEvent event) {
        IFactionPlayerLeaveIFactionEvent bridgeEvent = new IFactionPlayerLeaveIFactionEvent(
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                IFactionPlayerLeaveIFactionEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
