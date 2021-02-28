package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.*;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsNameChange;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

/**
 * MassiveCoreFactions implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 10:53
 */
public class MassiveCoreFactionsListener implements Listener {

    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onBunchOfEvents(EventFactionsChunksChange event) {
        if (event.getMPlayer().getFaction().getLandCount() == 0) {
            IClaimUnclaimAllEvent bridgeEvent = new IClaimUnclaimAllEvent(
                    api.getFaction(event.getMPlayer().getFaction().getId()),
                    api.getFactionPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            return;
        }
        for (PS chunk : event.getChunks()) {
            IClaimClaimEvent bridgeEvent = new IClaimClaimEvent(
                    api.getClaimAt(chunk.asBukkitChunk()),
                    api.getClaimAt(chunk.asBukkitChunk()).getFaction(),
                    api.getFactionPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
        }
        for (Map.Entry<PS, Faction> entry : event.getOldChunkFaction().entrySet()) {
            IClaimUnclaimEvent bridgeEvent = new IClaimUnclaimEvent(
                    api.getClaimAt(entry.getKey().asBukkitChunk()),
                    api.getFaction(entry.getValue().getId()),
                    api.getFactionPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
        }
    }

    @EventHandler
    public void onCreate(EventFactionsCreate event) {
        IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                event.getFactionId(),
                event.getMPlayer().getPlayer(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onDisband(EventFactionsDisband event) {
        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                api.getFactionPlayer(event.getMPlayer().getPlayer()),
                api.getFaction(event.getFactionId()),
                IFactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

    @EventHandler
    public void onRename(EventFactionsNameChange event) {
        IFactionRenameEvent bridgeEvent = new IFactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getNewName(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
    }

}
