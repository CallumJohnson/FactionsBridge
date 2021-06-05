package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import cc.javajobs.factionsbridge.FactionsBridge;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    List<Faction> getFactions();

    /**
     * Method to obtain all Factions.
     * <p>
     *     This method uses {@link #getFactions()} to obtain the Factions.
     * </p>
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Deprecated
    default List<Faction> getAllFactions() {
        return getFactions();
    }

    /**
     * Method to obtain a Faction from Chunk.
     *
     * @param chunk of the faction.
     * @return Faction at that location
     */
    @Nullable
    default Faction getFactionAt(@NotNull Chunk chunk) {
        return getClaim(chunk).getFaction();
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return Faction at that location
     */
    @Nullable
    default Faction getFactionAt(@NotNull Location location) {
        return getFactionAt(location.getChunk());
    }

    /**
     * Method to obtain a Claim by chunk.
     *
     * @param chunk of the claim.
     * @return Claim implementation.
     */
    @NotNull
    Claim getClaim(@NotNull Chunk chunk);

    /**
     * Method to obtain a Claim by location.
     *
     * @param location of the claim.
     * @return Claim implementation.
     */
    @NotNull
    default Claim getClaim(@NotNull Location location) {
        return getClaim(location.getChunk());
    }

    /**
     * Method to retrieve an Faction by Id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Nullable
    Faction getFaction(@NotNull String id);

    /**
     * Method to retrieve an Faction by Id.
     * <p>
     *     I've added this method as some implementations use this, so its nice for people to comfortable with APIs.
     * </p>
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    default Faction getFactionById(@NotNull String id) {
        return getFaction(id);
    }

    /**
     * Method to retrieve an Faction from Tag.
     *
     * @param tag of the Faction
     * @return Faction implementation.
     */
    @Nullable
    Faction getFactionByTag(@NotNull String tag);

    /**
     * Method to retrieve an Faction from Name.
     *
     * @param name of the Faction
     * @return Faction implementation.
     */
    @Nullable
    default Faction getFactionByName(@NotNull String name) {
        return getFactionByTag(name);
    }

    /**
     * Method to obtain the Faction by Player.
     * <p>
     *     This method uses {@link #getFPlayer(OfflinePlayer)} and {@link FPlayer#getFaction()}.
     *     <br>This method can return null, as not every player has a Faction.
     * </p>
     * @param player to get the relative Faction from.
     * @return Faction implementation.
     * @see FPlayer
     * @see #getFPlayer(OfflinePlayer)
     */
    @Nullable
    default Faction getFaction(@NotNull OfflinePlayer player) {
        return getFPlayer(player).getFaction();
    }

    /**
     * Method to obtain the FPlayer by a Player.
     * <p>
     *     Due to the SpigotAPI, OfflinePlayer == Player through implementation, so you can pass both here.
     * </p>
     *
     * @param player to get the FPlayer equivalent for.
     * @return FPlayer implementation.
     */
    @NotNull
    FPlayer getFPlayer(@NotNull OfflinePlayer player);

    /**
     * Method to obtain the FPlayer by an UUID.
     *
     * @param uuid to get the FPlayer equivalent for.
     * @return FPlayer implementation.
     */
    @NotNull
    default FPlayer getFPlayer(@NotNull UUID uuid) {
        return getFPlayer(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    Faction getWarZone();

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    Faction getSafeZone();

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    Faction getWilderness();

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @NotNull
    Faction createFaction(@NotNull String name) throws IllegalStateException;

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    void deleteFaction(@NotNull Faction faction) throws IllegalStateException;

    /**
     * Method to determine if a Faction exists, useful for checking before creating/deleting Factions.
     *
     * @param name to test.
     * @return {@code true} if the Faction does exist.
     */
    default boolean doesFactionExist(@NotNull String name) {
        return getFactionByName(name) != null;
    }

    /**
     * Method to determine if the {@link FactionsAPI#register()} method has been called.
     * @return {@code true} yes, {@code false} no
     */
    default boolean hasRegistered() {
        return FactionsBridge.get().registered;
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    void register();

}
