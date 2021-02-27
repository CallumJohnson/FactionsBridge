package cc.javajobs.factionsbridge.bridge;

import java.util.List;

/**
 * IFaction stands for a Faction of varying implementations.
 * <p>
 *     An IFaction bridges all Faction variants into one for Developers.
 * </p>
 * @author Callum Johnson
 * @version 1.0
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

    double getBank();

    int getPoints();

}
