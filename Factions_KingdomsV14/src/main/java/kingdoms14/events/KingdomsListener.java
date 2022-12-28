package kingdoms14.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.Land;
import org.kingdoms.events.general.KingdomCreateEvent;
import org.kingdoms.events.general.KingdomDisbandEvent;
import org.kingdoms.events.general.KingdomRenameEvent;
import org.kingdoms.events.lands.ClaimLandEvent;
import org.kingdoms.events.lands.UnclaimLandEvent;
import org.kingdoms.events.members.KingdomJoinEvent;
import org.kingdoms.events.members.KingdomLeaveEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * Kingdoms implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:33
 */
public class KingdomsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link ClaimLandEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull ClaimLandEvent event) {
        final Collection<Land> lands = event.getLands();
        lands.forEach(land -> {
            final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                    api.getClaim(land.getLocation().toChunk()),
                    Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                    api.getFPlayer(event.getKingdom().getKing().getPlayer()), // Assumed.
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        });
    }

    /**
     * Listener for the {@link UnclaimLandEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimAllEvent}.<br>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull UnclaimLandEvent event) {
        if (event.getKingdom().getLands().size() == 0) {
            final FactionUnclaimAllEvent bridgeEvent = new FactionUnclaimAllEvent(
                    Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                    api.getFPlayer(event.getPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
            return;
        }
        final Collection<Land> lands = event.getLands();
        lands.forEach(land -> {
            final FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                    api.getClaim(land.getLocation().toChunk()),
                    Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                    api.getFPlayer(event.getPlayer().getPlayer()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
            event.setCancelled(bridgeEvent.isCancelled());
        });
    }

    /**
     * Listener for the {@link KingdomCreateEvent}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull KingdomCreateEvent event) {
        final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                api.getFPlayer(event.getKingdom().getKing().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        if (event instanceof Cancellable) ((Cancellable)event).setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link KingdomDisbandEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDelete(@NotNull KingdomDisbandEvent event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getKingdom().getKing().getPlayer()),
                Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link KingdomRenameEvent}.
     * <p>
     *     This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull KingdomRenameEvent event) {
        final FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                event.getName(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link KingdomJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull KingdomJoinEvent event) {
        final FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                Objects.requireNonNull(api.getFaction(event.getKingdom().getId().toString())),
                api.getFPlayer(event.getKingdomPlayer().getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

    /**
     * Listener for the {@link KingdomLeaveEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull KingdomLeaveEvent event) {
        final  FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                Objects.requireNonNull(api.getFaction(event.getKingdomPlayer().getKingdom().getId().toString())),
                api.getFPlayer(event.getKingdomPlayer().getPlayer()),
                FactionLeaveEvent.LeaveReason.fromString(event.getReason().name()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
