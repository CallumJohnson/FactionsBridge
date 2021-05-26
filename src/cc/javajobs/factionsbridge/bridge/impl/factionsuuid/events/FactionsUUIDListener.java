package cc.javajobs.factionsbridge.bridge.impl.factionsuuid.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDPlayer;
import com.massivecraft.factions.event.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

/**
 * FactionsUUID implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 09:02
 */
public class FactionsUUIDListener implements Listener {

    private static final String PlayerDisbandReason = "com.massivecraft.factions.event.FactionDisbandEvent.PlayerDisbandReason";
    private final IFactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(LandClaimEvent event) {
        IClaimClaimEvent bridgeEvent = new IClaimClaimEvent(
                api.getClaimAt(event.getLocation().getChunk()),
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDPlayer(event.getfPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(FPlayerJoinEvent event) {
        IFactionPlayerJoinIFactionEvent bridgeEvent = new IFactionPlayerJoinIFactionEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDPlayer(event.getfPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(FPlayerLeaveEvent event) {
        IFactionPlayerLeaveIFactionEvent bridgeEvent = new IFactionPlayerLeaveIFactionEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDPlayer(event.getfPlayer()),
                IFactionPlayerLeaveIFactionEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaimAll(LandUnclaimAllEvent event) {
        IClaimUnclaimAllEvent bridgeEvent = new IClaimUnclaimAllEvent(
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDPlayer(event.getfPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(LandUnclaimEvent event) {
        IClaimUnclaimEvent bridgeEvent = new IClaimUnclaimEvent(
                api.getClaimAt(event.getLocation().getChunk()),
                new FactionsUUIDFaction(event.getFaction()),
                new FactionsUUIDPlayer(event.getfPlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        Bukkit.getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            IFactionCreateEvent bridgeEvent = new IFactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    new FactionsUUIDPlayer(event.getFPlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            if (event instanceof Cancellable) ((Cancellable) event).setCancelled(bridgeEvent.isCancelled());
        }, 20);
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        IFactionDisbandEvent.DisbandReason reason;
        try {
            Class<?> clazz = Class.forName(PlayerDisbandReason);
            if (!clazz.isEnum()) {
                reason = IFactionDisbandEvent.DisbandReason.UNKNOWN;
            } else {
                Method getReason = event.getClass().getMethod("getReason");
                Object reasonObj = getReason.invoke(event);
                Method name = clazz.getMethod("name");
                String reasonName = (String) name.invoke(reasonObj);
                reason = IFactionDisbandEvent.DisbandReason.fromString(reasonName);
            }
        } catch (ReflectiveOperationException ex) {
            reason = IFactionDisbandEvent.DisbandReason.UNKNOWN;
        }

        IFactionDisbandEvent bridgeEvent = new IFactionDisbandEvent(
                event.getPlayer(),
                new FactionsUUIDFaction(event.getFaction()),
                reason,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onRename(FactionRenameEvent event) {
        IFactionRenameEvent bridgeEvent = new IFactionRenameEvent(
                new FactionsUUIDFaction(event.getFaction()),
                event.getFactionTag(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
