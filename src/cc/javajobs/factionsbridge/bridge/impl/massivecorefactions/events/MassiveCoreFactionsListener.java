package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.*;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static org.bukkit.Bukkit.getPluginManager;

/**
 *  Listener to bridge Plugin-Events from MassiveCore to the FactionsBridge.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:53
 */
public class MassiveCoreFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link EventFactionsChunksChange}.
     * <p>
     *     This listener calls the {@link FactionUnclaimAllEvent}.<br>
     *     This listener calls the {@link FactionClaimEvent}.<br>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onBunchOfEvents(@NotNull EventFactionsChunksChange event) {
        if (event.getMPlayer().getFaction().getLandCount() == 0) {
            final FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                    api.getFaction(event.getMPlayer().getFaction().getId()),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        for (PS chunk : event.getChunks()) {
            final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                    api.getClaim(chunk.asBukkitChunk()),
                    api.getClaim(chunk.asBukkitChunk()).getFaction(),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }
        for (Map.Entry<PS, Faction> entry : event.getOldChunkFaction().entrySet()) {
            final FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                    api.getClaim(entry.getKey().asBukkitChunk()),
                    api.getFaction(entry.getValue().getId()),
                    api.getFPlayer(event.getMPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        }
    }

    /**
     * Listener for the {@link EventFactionsCreate}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull EventFactionsCreate event) {
        final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                api.getFaction(event.getFactionId()),
                api.getFPlayer(event.getMPlayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link EventFactionsDisband}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull EventFactionsDisband event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getMPlayer().getPlayer()),
                api.getFaction(event.getFactionId()),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link EventFactionsNameChange}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull EventFactionsNameChange event) {
        final FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                api.getFaction(event.getFaction().getId()),
                event.getNewName(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link EventFactionsMembershipChange}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.<br>
     *     This Listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull EventFactionsMembershipChange event) {
        switch (event.getReason()) {
            case JOIN:
                final FactionJoinEvent joinEvent = new FactionJoinEvent(
                        api.getFaction(event.getNewFaction().getId()),
                        api.getFPlayer(event.getMPlayer().getPlayer()),
                        event
                );
                getPluginManager().callEvent(joinEvent);
                event.setCancelled(joinEvent.isCancelled());
                return;
            case LEAVE:
            case KICK:
                final FactionLeaveEvent leaveEvent = new FactionLeaveEvent(
                        api.getFaction(event.getMPlayer().getFaction().getId()),
                        api.getFPlayer(event.getMPlayer().getPlayer()),
                        FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                        event
                );
                getPluginManager().callEvent(leaveEvent);
                event.setCancelled(leaveEvent.isCancelled());
                return;
            default:
                break;
        }
    }

}
