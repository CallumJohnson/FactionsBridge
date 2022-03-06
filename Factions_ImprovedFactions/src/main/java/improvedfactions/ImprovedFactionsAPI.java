package improvedfactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import improvedfactions.events.ImprovedFactionsListener;
import io.github.toberocat.improvedfactions.factions.FactionUtils;
import io.github.toberocat.improvedfactions.utility.ChunkUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsX implementation of {@link FactionsAPI}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:43
 */
public class ImprovedFactionsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        return io.github.toberocat.improvedfactions.factions.Faction.getFACTIONS().stream()
                .map(ImprovedFactionsFaction::new)
                .collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public Faction getFactionAt(@NotNull Location location) {
        return new ImprovedFactionsFaction(ChunkUtils.GetFactionClaimedChunk(location.getChunk()));
    }

    /**
     * Method to obtain an IClaim from Chunk.
     *
     * @param chunk to convert
     * @return IClaim object.
     */
    @NotNull
    @Override
    public Claim getClaim(@NotNull Chunk chunk) {
        return new ImprovedFactionsClaim(chunk);
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        return new ImprovedFactionsFaction(FactionUtils.getFactionByRegistry(id));
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
        return getFaction(tag);
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new ImprovedFactionsFaction(FactionUtils.getFaction(player.getUniqueId()));
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
        return new ImprovedFactionsPlayer(player);
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
        throw new BridgeMethodUnsupportedException("ImprovedFactions doesn't support createFaction(name).");
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        ((io.github.toberocat.improvedfactions.factions.Faction) ((AbstractFaction<?>) faction).getFaction())
                .DeleteFaction();
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public boolean register() {
        Bukkit.getPluginManager().registerEvents(
                new ImprovedFactionsListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        return true;
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
        throw new BridgeMethodUnsupportedException("ImprovedFactions doesn't support getWarZone().");
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link Faction}
     */
    @NotNull
    @Override
    public Faction getSafeZone() {
        return new ImprovedFactionsFaction(FactionUtils.getFactionByRegistry("safezone"));
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
        throw new BridgeMethodUnsupportedException("ImprovedFactions doesn't support getWilderness().");
    }

}
