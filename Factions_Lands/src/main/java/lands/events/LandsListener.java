package lands.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.FactionClaimEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionCreateEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionDisbandEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionJoinEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionLeaveEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionRenameEvent;
import cc.javajobs.factionsbridge.bridge.events.FactionUnclaimEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import lands.LandsClaim;
import lands.LandsFactionsAPI;
import me.angeschossen.lands.api.events.LandCreateEvent;
import me.angeschossen.lands.api.events.LandDeleteEvent;
import me.angeschossen.lands.api.events.LandRenameEvent;
import me.angeschossen.lands.api.events.LandTrustPlayerEvent;
import me.angeschossen.lands.api.events.LandUntrustPlayerEvent;
import me.angeschossen.lands.api.events.land.claiming.selection.LandClaimSelectionEvent;
import me.angeschossen.lands.api.events.land.claiming.selection.LandUnclaimSelectionEvent;
import me.angeschossen.lands.api.events.player.PlayerNullableEvent;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * Listener to bridge Plugin-Events from Lands to the FactionsBridge.
 */
public class LandsListener implements Listener {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Listener for the {@link LandRenameEvent}.
     * <p>
     * This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull LandRenameEvent event) {
        final FactionRenameEvent renameEvent = new FactionRenameEvent(
            Objects.requireNonNull(api.getFaction(event.getLand().getULID().toString())),
            event.getNewName(),
            event
        );
        getPluginManager().callEvent(renameEvent);
    }

    /**
     * Listener for the {@link LandClaimSelectionEvent}.
     * <p>
     * This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull LandClaimSelectionEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer player = getFPlayerForEventOrOwner(event, faction);
        if (player == null) {
            FactionsBridge.get().error("Failed to find player reference for LandClaimSelectionEvent");
            return;
        }
        final Player mcPlayer = player.getPlayer();
        if (mcPlayer == null) {
            FactionsBridge.get().error("Failed to find MCPlayer reference for LandClaimSelectionEvent");
            return;
        }
        final LandWorld world = LandsFactionsAPI.integration.getWorld(mcPlayer.getWorld());
        if (world == null) {
            FactionsBridge.get().error("Failed to find LandWorld reference for LandClaimSelectionEvent");
            return;
        }
        final List<Claim> claims = event.getSelection().getChunks().stream().map(chunk ->
            new LandsClaim(chunk, world)
        ).collect(Collectors.toList());
        claims.forEach(claim -> {
            final FactionClaimEvent claimEvent = new FactionClaimEvent(
                claim,
                Objects.requireNonNull(faction),
                Objects.requireNonNull(player),
                event
            );
            getPluginManager().callEvent(claimEvent);
        });
    }

    /**
     * Listener for the {@link LandCreateEvent}.
     * <p>
     * This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onCreate(@NotNull LandCreateEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer player = getFPlayerForEventOrOwner(event, faction);
        final FactionCreateEvent createEvent = new FactionCreateEvent(
            Objects.requireNonNull(faction),
            Objects.requireNonNull(player),
            event
        );
        getPluginManager().callEvent(createEvent);
    }

    /**
     * Listener for the {@link LandDeleteEvent}.
     * <p>
     * This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onDisband(@NotNull LandDeleteEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer player = getFPlayerForEventOrOwner(event, faction);
        final FactionDisbandEvent disbandEvent = new FactionDisbandEvent(
            Objects.requireNonNull(player),
            Objects.requireNonNull(faction),
            FactionDisbandEvent.DisbandReason.COMMAND,
            event
        );
        getPluginManager().callEvent(disbandEvent);
    }

    /**
     * Listener for the {@link LandTrustPlayerEvent}.
     * <p>
     * This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull LandTrustPlayerEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer joiner = api.getFPlayer(event.getTargetUUID());
        final FactionJoinEvent joinEvent = new FactionJoinEvent(
            Objects.requireNonNull(faction),
            joiner,
            event
        );
        getPluginManager().callEvent(joinEvent);
    }

    /**
     * Listener for the {@link LandUntrustPlayerEvent}.
     * <p>
     * This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull LandUntrustPlayerEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer leaver = api.getFPlayer(event.getTargetUUID());
        final FactionLeaveEvent.LeaveReason reason;
        switch (event.getReason()) {
            case DEFAULT:
                reason = FactionLeaveEvent.LeaveReason.LEAVE;
                break;
            case BAN:
                reason = FactionLeaveEvent.LeaveReason.KICK;
                break;
            default:
                reason = FactionLeaveEvent.LeaveReason.UNKNOWN;
                break;
        }
        final FactionLeaveEvent leaveEvent = new FactionLeaveEvent(
            Objects.requireNonNull(faction),
            leaver,
            reason,
            event
        );
        getPluginManager().callEvent(leaveEvent);
    }

    /**
     * Listener for the {@link LandUnclaimSelectionEvent}.
     * <p>
     * This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull LandUnclaimSelectionEvent event) {
        final Faction faction = api.getFaction(event.getLand().getULID().toString());
        final FPlayer player = getFPlayerForEventOrOwner(event, faction);
        if (player == null) {
            FactionsBridge.get().error("Failed to find player reference for LandClaimSelectionEvent");
            return;
        }
        final Player mcPlayer = player.getPlayer();
        if (mcPlayer == null) {
            FactionsBridge.get().error("Failed to find MCPlayer reference for LandClaimSelectionEvent");
            return;
        }
        final LandWorld world = LandsFactionsAPI.integration.getWorld(mcPlayer.getWorld());
        if (world == null) {
            FactionsBridge.get().error("Failed to find LandWorld reference for LandClaimSelectionEvent");
            return;
        }
        final List<Claim> claims = event.getSelection().getChunks().stream().map(chunk ->
            new LandsClaim(chunk, world)
        ).collect(Collectors.toList());
        claims.forEach(claim -> {
            final FactionUnclaimEvent unclaimEvent = new FactionUnclaimEvent(
                claim,
                Objects.requireNonNull(faction),
                player,
                event
            );
            getPluginManager().callEvent(unclaimEvent);
        });
    }

    @Nullable
    private FPlayer getFPlayerForEventOrOwner(@NotNull PlayerNullableEvent event, @Nullable Faction faction) {
        if (faction == null) {
            FactionsBridge.get().error("Failed to find faction for NationTrustLandEvent!");
            return null;
        }
        final UUID playerId = event.getPlayerUUID();
        final FPlayer player;
        if (playerId != null) {
            player = api.getFPlayer(playerId);
        } else {
            player = faction.getLeader();
        }
        return player;
    }

}
