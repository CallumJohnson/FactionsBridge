package towny;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.ErrorParticipator;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.InvalidNameException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.palmergames.bukkit.towny.TownyUniverse.getInstance;

/**
 * @author Callum Johnson
 * @since 01/07/2021 - 11:08
 */
public class TownyAPI implements FactionsAPI, ErrorParticipator {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @Override
    public @NotNull List<Faction> getFactions() {
        return getInstance().getTowns().stream().map(TownyFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Claim by location.
     *
     * @param location of the claim.
     * @return Claim implementation.
     */
    @Override
    public @NotNull Claim getClaim(@NotNull Location location) {
        final com.palmergames.bukkit.towny.TownyAPI townyAPI = com.palmergames.bukkit.towny.TownyAPI.getInstance();
        TownBlock townyBlock = townyAPI.getTownBlock(location);
        if (townyBlock == null) {
            if (FactionsBridge.get().catch_exceptions) {
                FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
            }
            if (townyAPI.isWilderness(location)) {
                townyBlock = new TownBlock(location.getChunk().getX(), location.getChunk().getZ(),
                        townyAPI.getTownyWorld(Objects.requireNonNull(location.getWorld()).getName()));
            } else return (Claim) methodError(getClass(), "getClaim(Location)", "Failed to find Town from Location");
        }
        return new TownyClaim(townyBlock);
    }

    /**
     * Method to obtain a Claim by chunk.
     *
     * @param chunk of the claim.
     * @return Claim implementation.
     */
    @Override
    public @NotNull Claim getClaim(@NotNull Chunk chunk) {
        return getClaim(chunk.getBlock(0, 0, 0).getLocation());
    }

    /**
     * Method to retrieve an Faction by Id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Override
    public @Nullable Faction getFaction(@NotNull String id) {
        try {
            return new TownyFaction(Objects.requireNonNull(getInstance().getTown(UUID.fromString(id))));
        } catch (NullPointerException ex) {
            if (FactionsBridge.get().catch_exceptions) {
                FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
            }
            return (Faction) methodError(getClass(), "getFaction(id)", "Failed to resolve Town from Id.");
        }
    }

    /**
     * Method to retrieve an Faction from Tag.
     *
     * @param tag of the Faction
     * @return Faction implementation.
     */
    @Override
    public @Nullable Faction getFactionByTag(@NotNull String tag) {
        try {
            return new TownyFaction(Objects.requireNonNull(getInstance().getTown(tag)));
        } catch (NullPointerException ex) {
            if (FactionsBridge.get().catch_exceptions) {
                FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
            }
            return (Faction) methodError(getClass(), "getFaction(tag)", "Failed to resolve Town from name.");
        }
    }

    /**
     * Method to obtain the FPlayer by a Player.
     * <p>
     * Due to the SpigotAPI, OfflinePlayer == Player through implementation, so you can pass both here.
     * </p>
     *
     * @param player to get the FPlayer equivalent for.
     * @return FPlayer implementation.
     */
    @Override
    public @NotNull FPlayer getFPlayer(@NotNull OfflinePlayer player) {
        try {
            return new TownyFPlayer(Objects.requireNonNull(getInstance().getResident(player.getUniqueId())));
        } catch (NullPointerException ex) {
            if (FactionsBridge.get().catch_exceptions) {
                FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
            }
            return (FPlayer) methodError(getClass(), "getFPlayer(offlinePlayer)",
                    "Failed to resolve FPlayer from OfflinePlayer/UUID");
        }
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @Override
    public @NotNull Faction getWarZone() {
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        return (Faction) unsupported(getProvider(), "getWarZone()");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @Override
    public @NotNull Faction getSafeZone() {
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        return (Faction) unsupported(getProvider(), "getSafeZone()");
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @Override
    public @NotNull Faction getWilderness() {
        if (FactionsBridge.get().catch_exceptions)
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        return (Faction) unsupported(getProvider(), "getWilderness()");
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @Override
    public @NotNull Faction createFaction(@NotNull String name) throws IllegalStateException {
        try {
            getInstance().newTown(name);
        } catch (AlreadyRegisteredException e) {
            throw new IllegalStateException("Faction already exists.");
        } catch (InvalidNameException e) {
            return (Faction) methodError(getClass(), "createFaction(name)", "Invalid name.");
        }
        try {
            return Objects.requireNonNull(getFactionByTag(name));
        } catch (Exception exception) {
            return (Faction) methodError(getClass(), "createFaction(name)", "Registration failed.");
        }
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        AbstractFaction<?> fac = (AbstractFaction<?>) faction;
        Town f = (Town) fac.getFaction();
        try {
            getInstance().unregisterTown(f);
        } catch (NotRegisteredException e) {
            throw new IllegalStateException("Failed to delete Town, its missing.");
        }
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     *
     * @return {@code true} if the provider successfully registered.
     */
    @Override
    public boolean register() {

        return true;
    }

}
