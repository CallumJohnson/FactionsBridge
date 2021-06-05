package cc.javajobs.factionsbridge.bridge.impl.kingdoms;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.impl.kingdoms.events.KingdomsListener;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FactionsAPI;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.constants.kingdom.Kingdom;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.data.DataHandler;
import org.kingdoms.data.managers.KingdomManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Kingdoms implementation of IFactionsAPI.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:17
 */
public class KingdomsAPI implements FactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return Factions in the form of a List.
     */
    @NotNull
    @Override
    public List<Faction> getFactions() {
        DataHandler dataHandler;
        KingdomManager manager;
        try {
            dataHandler = DataHandler.get();
        } catch (Exception exception) {
            FactionsBridge.get().exception(exception, "Kingdoms DataHandler is null.");
            return new ArrayList<>();
        }
        try {
            manager = dataHandler.getKingdomManager();
        } catch (Exception exception) {
            FactionsBridge.get().exception(exception, "Kingdoms KingdomManager is null.");
            return new ArrayList<>();
        }
        try {
            return manager.getKingdoms().stream().map(KingdomsKingdom::new).collect(Collectors.toList());
        } catch (Exception ex) {
            FactionsBridge.get().exception(ex, "Cannot convert Kingdoms to FactionsBridge Factions.");
            return new ArrayList<>();
        }
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
        return new KingdomsClaim(Land.getLand(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull String id) {
        return new KingdomsKingdom(Kingdom.getKingdom(UUID.fromString(id)));
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
        return new KingdomsKingdom(Kingdom.getKingdom(tag));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public Faction getFaction(@NotNull OfflinePlayer player) {
        return new KingdomsKingdom(KingdomPlayer.getKingdomPlayer(player).getKingdom());
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
        return new KingdomsPlayer(KingdomPlayer.getKingdomPlayer(player));
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
        throw new BridgeMethodUnsupportedException("Kingdoms doesn't support createFaction(name).");
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(@NotNull Faction faction) throws IllegalStateException {
        ((Kingdom) (((AbstractFaction<?>) faction).getFaction())).disband();
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new KingdomsListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        FactionsBridge.get().registered = true;
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
        throw new BridgeMethodUnsupportedException("Kingdoms doesn't support getWarZone().");
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
        throw new BridgeMethodUnsupportedException("Kingdoms doesn't support getSafeZone().");
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
        throw new BridgeMethodUnsupportedException("Kingdoms doesn't support getWilderness().");
    }

}
