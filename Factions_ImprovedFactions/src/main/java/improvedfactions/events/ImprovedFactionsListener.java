package improvedfactions.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import io.github.toberocat.improvedfactions.event.chunk.ChunkClaimEvent;
import io.github.toberocat.improvedfactions.event.chunk.ChunkUnclaimEvent;
import io.github.toberocat.improvedfactions.event.faction.FactionDeleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * FactionsX implementation of the Bridges needed to handle all Custom Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 10:23
 */
public class ImprovedFactionsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link ChunkClaimEvent}.
     * <p>
     *     This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull ChunkClaimEvent event) {
        final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                api.getClaim(event.getChunk()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                api.getFPlayer(event.getFaction().getOwner()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link ChunkUnclaimEvent}.
     * <p>
     *     This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull ChunkUnclaimEvent event) {
        final FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                api.getClaim(event.getChunk()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                api.getFPlayer(event.getFaction().getOwner()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link io.github.toberocat.improvedfactions.event.faction.FactionCreateEvent}.
     * <p>
     *     This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull io.github.toberocat.improvedfactions.event.faction.FactionCreateEvent event) {
        final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);

    }

    /**
     * Listener for the {@link FactionDeleteEvent}.
     * <p>
     *     This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull FactionDeleteEvent event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getPlayer()),
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                FactionDisbandEvent.DisbandReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link io.github.toberocat.improvedfactions.event.faction.FactionJoinEvent}.
     * <p>
     *     This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull io.github.toberocat.improvedfactions.event.faction.FactionJoinEvent event) {
        final FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                api.getFPlayer(event.getPlayer()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link io.github.toberocat.improvedfactions.event.faction.FactionLeaveEvent}.
     * <p>
     *     This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull io.github.toberocat.improvedfactions.event.faction.FactionLeaveEvent event) {
        final FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                Objects.requireNonNull(api.getFaction(event.getFaction().getRegistryName())),
                api.getFPlayer(event.getPlayer()),
                FactionLeaveEvent.LeaveReason.UNKNOWN,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

}
