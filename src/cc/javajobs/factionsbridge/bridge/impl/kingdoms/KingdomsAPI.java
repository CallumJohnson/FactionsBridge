package cc.javajobs.factionsbridge.bridge.impl.kingdoms;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.impl.kingdoms.events.KingdomsListener;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.kingdoms.constants.kingdom.Kingdom;
import org.kingdoms.constants.land.Land;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.constants.player.KingdomPlayer;
import org.kingdoms.data.DataHandler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Kingdoms implementation of IFactionsAPI.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:17
 */
public class KingdomsAPI implements IFactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @Override
    public List<IFaction> getAllFactions() {
        return DataHandler.get().getKingdomManager().getKingdoms()
                .stream().map(KingdomsKingdom::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public IFaction getFactionAt(Location location) {
        return new KingdomsKingdom(Land.getLand(location).getKingdom());
    }

    /**
     * Method to obtain an IClaim from Location.
     *
     * @param location to get IClaim from.
     * @return IClaim object.
     */
    @Override
    public IClaim getClaimAt(Location location) {
        return getClaimAt(location.getChunk());
    }

    /**
     * Method to obtain an IClaim from Chunk.
     *
     * @param chunk to convert
     * @return IClaim object.
     */
    @Override
    public IClaim getClaimAt(Chunk chunk) {
        return new KingdomsClaim(Land.getLand(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(String id) {
        return new KingdomsKingdom(Kingdom.getKingdom(UUID.fromString(id)));
    }

    /**
     * Method to retrive an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFactionByName(String name) {
        return new KingdomsKingdom(Kingdom.getKingdom(name));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(OfflinePlayer player) {
        return new KingdomsKingdom(KingdomPlayer.getKingdomPlayer(player).getKingdom());
    }

    /**
     * Method to get an IFactionPlayer from Player/OfflinePlayer.
     *
     * @param player related to the IFactionPlayer.
     * @return IFactionPlayer implementation.
     */
    @Override
    public IFactionPlayer getFactionPlayer(OfflinePlayer player) {
        return new KingdomsPlayer(KingdomPlayer.getKingdomPlayer(player));
    }

    /**
     * Method to create a new Faction with the given name.
     *
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    @Override
    public IFaction createFaction(String name) throws IllegalStateException {
        throw new BridgeMethodUnsupportedException("Kingdoms doesn't support createFaction(name).");
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(IFaction faction) throws IllegalStateException {
        if (faction == null) {
            throw new IllegalStateException("IFaction cannot be null!");
        }
        ((Kingdom) faction.asObject()).disband();
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(new KingdomsListener(), FactionsBridge.get());
        FactionsBridge.get().registered = true;
    }

}
