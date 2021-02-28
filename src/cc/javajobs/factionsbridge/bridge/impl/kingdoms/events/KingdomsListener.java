package cc.javajobs.factionsbridge.bridge.impl.kingdoms.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.IClaimClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.IClaimUnclaimEvent;
import cc.javajobs.factionsbridge.bridge.events.IFactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.IFactionDisbandEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kingdoms.events.general.KingdomCreateEvent;
import org.kingdoms.events.general.KingdomDisbandEvent;
import org.kingdoms.events.lands.ClaimLandEvent;
import org.kingdoms.events.lands.UnclaimLandEvent;

/**
 * Kingdoms implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @version 1.0
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
    }

    @EventHandler
    public void onUnclaim(UnclaimLandEvent event) {
        IClaimUnclaimEvent bridgeEvent = new IClaimUnclaimEvent(
                api.getClaimAt(event.getLand().getLocation().toChunk()),
                api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString()),
                api.getFactionPlayer(event.getKingdomPlayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onCreate(KingdomCreateEvent event) {
        IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                api.getFactionPlayer(event.getKingdom().getKing().getPlayer()),
                api.getFaction(event.getKingdom().getId().toString()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
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
    }

}
