package cc.javajobs.factionsbridge.bridge.impl.factionsuuid.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
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
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    @EventHandler
    public void onClaim(LandClaimEvent event) {
        FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getLocation().getChunk()),
                api.getFaction(event.getFaction().getId()),
                api.getFPlayer(event.getfPlayer().getOfflinePlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onJoin(com.massivecraft.factions.event.FPlayerJoinEvent event) {
        FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                api.getFaction(event.getFaction().getId()),
                api.getFPlayer(event.getfPlayer().getOfflinePlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onLeave(com.massivecraft.factions.event.FPlayerLeaveEvent event) {
        FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                api.getFaction(event.getFaction().getId()),
                api.getFPlayer(event.getfPlayer().getOfflinePlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaimAll(LandUnclaimAllEvent event) {
        FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                api.getFaction(event.getFaction().getId()),
                api.getFPlayer(event.getfPlayer().getOfflinePlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onUnclaim(LandUnclaimEvent event) {
        FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getLocation().getChunk()),
                api.getFaction(event.getFaction().getId()),
                api.getFPlayer(event.getfPlayer().getOfflinePlayer()),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onFactionCreate(com.massivecraft.factions.event.FactionCreateEvent event) {
        Bukkit.getScheduler().runTaskLater(FactionsBridge.get().getDevelopmentPlugin(), () -> {
            FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                    api.getFactionByName(event.getFactionTag()),
                    api.getFPlayer(event.getFPlayer().getOfflinePlayer()),
                    event
            );
            Bukkit.getPluginManager().callEvent(bridgeEvent);
            if (event instanceof Cancellable) ((Cancellable) event).setCancelled(bridgeEvent.isCancelled());
        }, 20);
    }

    @EventHandler
    public void onFactionDisband(com.massivecraft.factions.event.FactionDisbandEvent event) {
        FactionDisbandEvent.DisbandReason reason;
        try {
            Class<?> clazz = Class.forName(PlayerDisbandReason);
            if (!clazz.isEnum()) {
                reason = FactionDisbandEvent.DisbandReason.UNKNOWN;
            } else {
                Method getReason = event.getClass().getMethod("getReason");
                Object reasonObj = getReason.invoke(event);
                Method name = clazz.getMethod("name");
                String reasonName = (String) name.invoke(reasonObj);
                reason = FactionDisbandEvent.DisbandReason.fromString(reasonName);
            }
        } catch (ReflectiveOperationException ex) {
            reason = FactionDisbandEvent.DisbandReason.UNKNOWN;
        }

        FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                event.getPlayer(),
                api.getFaction(event.getFaction().getId()),
                reason,
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    @EventHandler
    public void onRename(com.massivecraft.factions.event.FactionRenameEvent event) {
        FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getFactionTag(),
                event
        );
        Bukkit.getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
