package cc.javajobs.factionsbridge.bridge.impl.factionsuuid;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IFactionsAPI;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.events.FactionsUUIDListener;
import com.massivecraft.factions.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsUUID implementation of IFactionAPI.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 15:01
 */
public class FactionsUUIDAPI implements IFactionsAPI {

    /**
     * Method to obtain all Factions.
     *
     * @return IFactions in the form of a List.
     */
    @Override
    public List<IFaction> getAllFactions() {
        return Factions.getInstance().getAllFactions()
                .stream().map(FactionsUUIDFaction::new).collect(Collectors.toList());
    }

    /**
     * Method to obtain a Faction from Location.
     *
     * @param location of the faction.
     * @return IFaction at that location
     */
    @Override
    public IFaction getFactionAt(Location location) {
        return new FactionsUUIDFaction(Board.getInstance().getFactionAt(new FLocation(location)));
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
        return new FactionsUUIDClaim(new FLocation(chunk));
    }

    /**
     * Method to retrieve an IFaction from Id.
     *
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFaction(String id) {
        return new FactionsUUIDFaction(Factions.getInstance().getFactionById(id));
    }

    /**
     * Method to retrive an IFaction from Name.
     *
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    @Override
    public IFaction getFactionByName(String name) {
        return new FactionsUUIDFaction(Factions.getInstance().getByTag(name));
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
        return new FactionsUUIDPlayer(FPlayers.getInstance().getByOfflinePlayer(player));
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
        IFaction fac = getFactionByName(name);
        if (fac != null && !fac.isServerFaction()) {
            throw new IllegalStateException("Faction already exists.");
        }
        Faction faction = Factions.getInstance().createFaction();
        faction.setTag(name);
        return new FactionsUUIDFaction(faction);
    }

    /**
     * Method to delete a Faction.
     *
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    @Override
    public void deleteFaction(IFaction faction) throws IllegalStateException {
        Factions.getInstance().removeFaction(faction.getId());
    }

    /**
     * Method to register events and handle event pass-through for the Bridge.
     */
    @Override
    public void register() {
        if (FactionsBridge.getFactionsAPI().hasRegistered()) return;
        Bukkit.getPluginManager().registerEvents(
                new FactionsUUIDListener(),
                FactionsBridge.get().getDevelopmentPlugin()
        );
        FactionsBridge.get().registered = true;
    }

}
