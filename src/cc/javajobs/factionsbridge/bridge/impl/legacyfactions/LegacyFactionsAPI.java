package cc.javajobs.factionsbridge.bridge.impl.legacyfactions;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.legacyfactions.events.LegacyFactionsListener;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.Faction;
import net.redstoneore.legacyfactions.entity.FactionColl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Callum Johnson
 * @since 04/05/2021 - 10:09
 */
public class LegacyFactionsAPI implements IFactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @Override
    public List<IFaction> getAllFactions() {
        return FactionColl.all().stream().map(LegacyFactionsFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public IFaction getFactionAt(Location location) {
        return getClaimAt(location).getFaction();
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
        return new LegacyFactionsClaim(new FLocation(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(String id) {
        return new LegacyFactionsFaction(FactionColl.get().getFactionById(id));
    }

    /**
     * Method to retrive an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFactionByName(String name) {
        return new LegacyFactionsFaction(FactionColl.get().getByTag(name));
    }

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     *
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(OfflinePlayer player) {
        return getFactionPlayer(player).getFaction();
    }

    /**
     * Method to get an IFactionPlayer from Player/OfflinePlayer.
     *
     * @param player related to the IFactionPlayer.
     * @return IFactionPlayer implementation.
     */
    @Override
    public IFactionPlayer getFactionPlayer(OfflinePlayer player) {
        return new LegacyFactionsPlayer(FPlayerColl.get(player));
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
        Faction faction = FactionColl.get().createFaction();
        faction.setTag(name);
        return new LegacyFactionsFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(IFaction faction) throws IllegalStateException {
        FactionColl.get().removeFaction(faction.getId());
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new LegacyFactionsListener(), FactionsBridge.get().getDevelopmentPlugin());
        FactionsBridge.get().registered = true;
    }

    /**
     * Method to obtain WarZone.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getWarZone() {
        return new LegacyFactionsFaction(FactionColl.get().getWarZone());
    }

    /**
     * Method to obtain SafeZone.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getSafeZone() {
        return new LegacyFactionsFaction(FactionColl.get().getSafeZone());
    }

    /**
     * Method to obtain the Wilderness.
     *
     * @return {@link IFaction}
     */
    @Override
    public IFaction getWilderness() {
        return new LegacyFactionsFaction(FactionColl.get().getWilderness());
    }

}
