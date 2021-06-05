package cc.javajobs.factionsbridge.bridge.impl.kingdoms.events;

import org.bukkit.event.Listener;

/**
 * Kingdoms implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:33
 */
public class KingdomsListener implements Listener {
/*
    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(ClaimLandEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
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
            FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                    api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                    api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
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
        FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(event.getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        if (event instanceof Cancellable) ((Cancellable)event).setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onDelete(KingdomDisbandEvent event) {
        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()),
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
                api.getFactionPlayer(event.getPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(KingdomLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }*/

}
