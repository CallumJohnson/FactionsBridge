package cc.javajobs.factionsbridge.bridge.impl.factionsx.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.*;
import net.prosavage.factionsx.event.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * FactionsX implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 10:23
 */
public class FactionsXListener implements Listener {

    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(FactionPreClaimEvent event) {
        IClaimClaimEvent bridgeEvent = new IClaimClaimEvent(
                api.getClaimAt(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionClaiming().getId())),
                api.getFactionPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onUnclaimAll(FactionUnClaimAllEvent event) {
        IClaimUnclaimAllEvent bridgeEvent = new IClaimUnclaimAllEvent(
                api.getFaction(String.valueOf(event.getUnclaimingFaction().getId())),
                api.getFactionPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onUnclaim(FactionUnClaimEvent event) {
        IClaimUnclaimEvent bridgeEvent = new IClaimUnclaimEvent(
                api.getClaimAt(event.getFLocation().getChunk()),
                api.getFaction(String.valueOf(event.getFactionUnClaiming().getId())),
                api.getFactionPlayer(event.getFplayer().getPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onCreate(FactionCreateEvent event) {
        IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                String.valueOf(event.getFaction().getId()),
                event.getFPlayer().getPlayer(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onDisband(FactionDisbandEvent event) {
        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                api.getFactionPlayer(event.getFPlayer().getPlayer()),
                api.getFaction(String.valueOf(event.getFaction().getId())),
                IFactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

}
