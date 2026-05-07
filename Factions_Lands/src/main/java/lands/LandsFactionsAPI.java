package lands;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import lands.events.LandsListener;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.applicationframework.util.ULID;
import me.angeschossen.lands.api.blockworks.ChunkCoordinate;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lands implementation of the {@link FactionsAPI}.
 */
public class LandsFactionsAPI implements FactionsAPI {

    public static LandsIntegration integration;

    public LandsFactionsAPI() {
        if (integration == null) {
            integration = LandsIntegration.of(FactionsBridge.get().getDevelopmentPlugin());
        }
    }

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return integration.getLands().parallelStream().map(LandsFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Claim by chunk.
     *
     * @param chunk of the claim.
     * @return Claim implementation.
     */
    @NotNull
    @Override
    public Claim getClaim(@NotNull Chunk chunk) {
        final LandWorld world = integration.getWorld(chunk.getWorld());
        if (world == null) {
            FactionsBridge.get().error("Failed to get LandsWorld for chunk.");
            throw new IllegalStateException("No LandsWorld for chunk found!");
        }
        final ChunkCoordinate coordinate = new ChunkCoordinate(chunk.getX(), chunk.getZ());
        return new LandsClaim(coordinate, world);
    }

    /**
     * Method to retrieve an Faction by Id.
     *
     * @param id of the Faction
     * @return Faction implementation.
     */
    @Nullable
    @Override
    public Faction getFaction(@NotNull String id) {
        final Land land = integration.getLandByULID(ULID.fromString(id));
        if (land == null) {
            return null;
        }
        return new LandsFaction(land);
    }

    /**
     * Method to retrieve an Faction from Tag.
     *
     * @param tag of the Faction
     * @return Faction implementation.
     */
    @Nullable
    @Override
    public Faction getFactionByTag(@NotNull String tag) {
        final Land land = integration.getLandByName(tag);
        if (land == null) {
            return null;
        }
        return new LandsFaction(land);
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
    @NotNull
    @Override
    public FPlayer getFPlayer(@NotNull OfflinePlayer player) {
        return new LandsFPlayer(player);
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWarZone() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Lands doesn't support getWarZone()");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Lands doesn't support getSafeZone()");
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getWilderness() {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Lands doesn't support getWilderness()");
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @NotNull
    @Override
    public Faction createFaction(@NotNull String name) throws IllegalStateException {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Lands doesn't support createFaction(name)");
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        if (FactionsBridge.get().catch_exceptions) {
            FactionsBridge.get().error("Cannot bypass exception as this is an API method!");
        }
        throw new BridgeMethodUnsupportedException("Lands doesn't support deleteFaction(faction)");
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public boolean register() {
        Bukkit.getPluginManager().registerEvents(
            new LandsListener(),
            FactionsBridge.get().getDevelopmentPlugin()
        );
        return true;
    }

}