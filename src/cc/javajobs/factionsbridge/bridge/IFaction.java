package cc.javajobs.factionsbridge.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IFaction stands for a Faction of varying implementations.
 * <p>
 *     An IFaction bridges all Faction variants into one for Developers.
 * </p>
 * @author Callum Johnson
 * @since 25/02/2021 - 18:52
 */
public interface IFaction {

    /**
     * Method to get the Id of the Faction.
     * @return Id in the form of String.
     */
    String getId();

    /**
     * Method to get the Name of the Faction.
     * @return name of the Faction.
     */
    String getName();

    /**
     * Method to get the IFactionPlayer Leader.
     * @return the person who created the Faction.
     */
    IFactionPlayer getLeader();

    /**
     * Method to get the name of the Leader.
     * @return name of the person who created the Faction.
     * @see IFaction#getLeader()
     */
    String getLeaderName();

    /**
     * Method to get all Claims related to the Faction.
     * @return Claims in the form List of {@link IClaim}
     */
    List<IClaim> getAllClaims();

    /**
     * Method to get all of the Members for the Faction.
     * @return List of IFactionPlayer
     */
    List<IFactionPlayer> getMembers();

    /**
     * Method to set the 'Home' of a Faction.
     * @param location to set as the new home.
     */
    void setHome(Location location);

    /**
     * Method to retrieve the 'Home' of the Faction.
     * @return {@link Bukkit}, {@link Location}.
     */
    Location getHome();

    /**
     * Method to get all of the Members for the Faction who are currently online.
     * @return list of IFactionPlayer.
     */
    default List<IFactionPlayer> getOnlineMembers() {
        return getMembers().stream().filter(IFactionPlayer::isOnline).collect(Collectors.toList());
    }

    /**
     * Method to get the relationship between two Factions.
     * @param other faction to test
     * @return {@link IRelationship}
     */
    IRelationship getRelationTo(IFaction other);

    /**
     * Method to get the relationship between an IFaction and an IFactionPlayer.
     * @param other IFactionPlayer to test
     * @return {@link IRelationship}
     */
    IRelationship getRelationTo(IFactionPlayer other);

    /**
     * Method to return the IFaction as an Object (API friendly)
     * @return object of API.
     */
    Object asObject();

    /**
     * Method to test if this Faction is a Server Faction
     * <p>
     *     Server Factions: Wilderness, SafeZone, WarZone.
     * </p>
     * @return {@code true} if yes, {@code false} if no.
     */
    boolean isServerFaction();

    /**
     * Method to determine if the IFaction is the WarZone.
     * @return {@code true} if it is.
     */
    boolean isWarZone();

    /**
     * Method to determine if the IFaction is a SafeZone.
     * @return {@code true} if it is.
     */
    boolean isSafeZone();

    /**
     * Method to determine if the IFaction is the Wilderness.
     * @return {@code true} if it is.
     */
    boolean isWilderness();

    /**
     * Method to determine if the Faction is in a Peaceful State.
     * @return {@code true} if yes, {@code false} if no.
     */
    boolean isPeaceful();

    /**
     * Method to get the bank balance of the Faction.
     * @return in the form of Double.
     */
    double getBank();

    /**
     * Method to get the points of a Faction.
     * @return in the form of Integer.
     */
    int getPoints();

    /**
     * Method to get the Location of a Faction Warp by Name.
     * @param name of the warp
     * @return {@link Location} of the warp.
     */
    Location getWarp(String name);

    /**
     * Method to retrieve all warps.
     * <p>
     *     This method returns a hashmap of String names and Locations.
     * </p>
     * @return hashmap of all warps.
     */
    HashMap<String, Location> getWarps();

    /**
     * Method to create a warp for the Faction.
     * @param name of the warp.
     * @param location of the warp.
     */
    void createWarp(String name, Location location);

    /**
     * Method to manually remove a Warp using its name.
     * @param name of the warp to be deleted.
     */
    void deleteWarp(String name);

    /**
     * Add strikes to a Faction.
     * @param sender who desires to Strike the Faction.
     * @param reason for the Strike.
     */
    void addStrike(String sender, String reason);

    /**
     * Remove strike from a Faction.
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    void removeStrike(String sender, String reason);

    /**
     * Method to obtain the Total Strikes a Faction has.
     * @return integer amount of Strikes.
     */
    int getTotalStrikes();

    /**
     * Method to clear all Strikes.
     */
    void clearStrikes();

    /**
     * Added a Default Method to display faction to console.
     * @return String (id, name, isserver, object, claimcount)
     */
    default String asString() {
        return "Faction{" +
                    "id=" + getId() +
                    ", name=" + getName() +
                    ", isServerFaction?=" + isServerFaction() +
                    ", obj=" + asObject() +
                    ", claimcount=" + getAllClaims().size() +
                    ", leader='" + getLeader().asString() + "'" +
                "}";
    }

}
