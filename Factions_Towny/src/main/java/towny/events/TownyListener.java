package towny.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.*;
import cc.javajobs.factionsbridge.bridge.infrastructure.ErrorParticipator;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * Listener to bridge Plugin-Events from Towny to the FactionsBridge.
 *
 * @author Callum Johnson
 * @since 01/07/2021 - 11:35
 */
public class TownyListener implements Listener, ErrorParticipator {

    /**
     * Instance of the {@link FactionsAPI} created by FactionsBridge.
     */
    private final FactionsAPI api = FactionsBridge.getFactionsAPI();

    /**
     * Method to obtain the TownyBlock as Location.
     *
     * @return {@link Location}.
     */
    private @NotNull Location getLocation(@NotNull TownBlock block) {
        final WorldCoord worldCoord = block.getWorldCoord();
        final int y = worldCoord.getBukkitWorld().getHighestBlockYAt(worldCoord.getX(), worldCoord.getZ());
        return new Location(worldCoord.getBukkitWorld(), worldCoord.getX() + 0.5, y, worldCoord.getZ() + 0.5);
    }

    /**
     * Listener for the {@link NewTownEvent}.
     * <p>
     * This listener calls the {@link FactionCreateEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onTownCreate(@NotNull NewTownEvent event) {
        final FactionCreateEvent bridgeEvent = new FactionCreateEvent(
                Objects.requireNonNull(api.getFaction(event.getTown().getUUID().toString())),
                api.getFPlayer(event.getTown().getMayor().getUUID()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link DeleteTownEvent}.
     * <p>
     * This listener calls the {@link FactionDisbandEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onTownDisband(@NotNull DeleteTownEvent event) {
        final FactionDisbandEvent bridgeEvent = new FactionDisbandEvent(
                api.getFPlayer(event.getMayorUUID()),
                Objects.requireNonNull(api.getFaction(event.getTownUUID().toString())),
                FactionDisbandEvent.DisbandReason.COMMAND,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link TownClaimEvent}.
     * <p>
     * This listener calls the {@link FactionClaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onClaim(@NotNull TownClaimEvent event) {
        try {
            final FactionClaimEvent bridgeEvent = new FactionClaimEvent(
                    api.getClaim(getLocation(event.getTownBlock())),
                    Objects.requireNonNull(api.getFaction(event.getTownBlock().getTown().getUUID().toString())),
                    api.getFPlayer(event.getResident().getUUID()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
        } catch (TownyException ex) {
            methodError(getClass(), "onClaim(TownClaimEvent)", "Failed to get Town.");
        }
    }

    /**
     * Listener for the {@link TownUnclaimEvent}.
     * <p>
     * This listener calls the {@link FactionUnclaimEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onUnclaim(@NotNull TownUnclaimEvent event) {
        try {
            final FactionUnclaimEvent bridgeEvent = new FactionUnclaimEvent(
                    api.getClaim(getLocation(event.getWorldCoord().getTownBlock())),
                    Objects.requireNonNull(api.getFaction(Objects.requireNonNull(event.getTown()).getUUID().toString())),
                    api.getFPlayer(event.getTown().getMayor().getUUID()),
                    event
            );
            getPluginManager().callEvent(bridgeEvent);
        } catch (TownyException e) {
            methodError(getClass(), "onUnclaim(TownyUnclaimEvent)", "Failed to get TownBlock");
        }
    }

    /**
     * Listener for the {@link TownAddResidentEvent}.
     * <p>
     * This listener calls the {@link FactionJoinEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onJoin(@NotNull TownAddResidentEvent event) {
        final FactionJoinEvent bridgeEvent = new FactionJoinEvent(
                Objects.requireNonNull(api.getFaction(event.getTown().getUUID().toString())),
                api.getFPlayer(event.getResident().getUUID()),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link TownRemoveResidentEvent}.
     * <p>
     * This listener calls the {@link FactionLeaveEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onLeave(@NotNull TownRemoveResidentEvent event) {
        final FactionLeaveEvent bridgeEvent = new FactionLeaveEvent(
                Objects.requireNonNull(api.getFaction(event.getTown().getUUID().toString())),
                api.getFPlayer(event.getResident().getUUID()),
                FactionLeaveEvent.LeaveReason.LEAVE,
                event
        );
        getPluginManager().callEvent(bridgeEvent);
    }

    /**
     * Listener for the {@link TownPreRenameEvent}.
     * <p>
     * This listener calls the {@link FactionRenameEvent}.
     * </p>
     *
     * @param event to monitor.
     */
    @EventHandler
    public void onRename(@NotNull TownPreRenameEvent event) {
        final FactionRenameEvent bridgeEvent = new FactionRenameEvent(
                Objects.requireNonNull(api.getFaction(event.getTown().getUUID().toString())),
                event.getNewName(),
                event
        );
        getPluginManager().callEvent(bridgeEvent);
        event.setCancelled(bridgeEvent.isCancelled());
    }

}
