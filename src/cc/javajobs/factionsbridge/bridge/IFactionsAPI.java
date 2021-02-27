package cc.javajobs.factionsbridge.bridge;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.List;

/**
 * IFactionsAPI is the forefront of the bridge.
 * <p>
 *     This class will inherently give access to all other FactionBridge bridges.
 * </p>
 * @author Callum Johnson
 * @version 1.0
 * @since 25/02/2021 - 18:48
 */
public interface IFactionsAPI {

    /**
     * Method to obtain all Factions.
     * @return IFactions in the form of a List.
     */
    List<IFaction> getAllFactions();

    /**
     * Method to obtain a Faction from Location.
     * @param location of the faction.
     * @return IFaction at that location
     */
    IFaction getFactionAt(Location location);

    /**
     * Method to retrieve an IFaction from Id.
     * @param id of the IFaction
     * @return IFaction implementation.
     */
    IFaction getFaction(String id);

    /**
     * Method to retrive an IFaction from Name.
     * @param name of the IFaction
     * @return IFaction implementation.
     */
    IFaction getFactionByName(String name);

    /**
     * Method to retrieve an IFaction from Player/OfflinePlayer.
     * @param player in the IFaction.
     * @return IFaction implementation.
     */
    IFaction getFaction(OfflinePlayer player);

    /**
     * Method to get an IFactionPlayer from Player/OfflinePlayer.
     * @param player related to the IFactionPlayer.
     * @return IFactionPlayer implementation.
     */
    IFactionPlayer getFactionPlayer(OfflinePlayer player);

    /**
     * Method to create a new Faction with the given name.
     * @param name of the new Faction.
     * @return IFaction implementation.
     * @throws IllegalStateException if the IFaction exists already.
     */
    IFaction createFaction(String name) throws IllegalStateException;

    /**
     * Method to delete a Faction.
     * @param faction to delete
     * @throws IllegalStateException if the Faction doesn't exist.
     */
    void deleteFaction(IFaction faction) throws IllegalStateException;

}
