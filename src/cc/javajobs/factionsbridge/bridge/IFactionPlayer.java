package cc.javajobs.factionsbridge.bridge;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * IFactionPlayer stands for a single FactionPlayer/FPlayer
 * <p>
 *     This class bridges the gap between APIs and developers plugins
 *     who desire to use FPlayers.
 * </p>
 * @author Callum Johnson
 * @since 25/02/2021 - 18:52
 */
public interface IFactionPlayer {

    /**
     * Method to get the unique Id of the Faction Player.
     * @return UUID (UniqueId).
     */
    UUID getUniqueId();

    /**
     * Method to get the Name of the Faction Player.
     * @return name of the Player.
     */
    String getName();

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     * @return faction of the player.
     */
    IFaction getFaction();

    /**
     * Method to determine if the Player is in a Faction & if the Faction isn't a System Faction.
     * <p>
     *     Some Factions implementations, if a Player isn't in a Faction, the Player is assumed
     *     to be "factionless" which is defaulted to Wilderness.
     * </p>
     * @return {@code true} if the player is in a faction other than Wilderness/WarZone/SafeZone.
     */
    boolean hasFaction();

    /**
     * Method to get the Offline form of the Player.
     * @return {@link OfflinePlayer}
     */
    OfflinePlayer getOfflinePlayer();

    /**
     * Method to get the Online form of the Player.
     * @return {@link Player}
     * @see IFactionPlayer#isOnline()
     */
    Player getPlayer();

    /**
     * Uses Bukkit methodology to determine if the Player is online.
     * @return {@code true} = yes, {@code false} = no.
     */
    boolean isOnline();

    /**
     * Method to get the relationship from a Player to a Faction.
     * @param other faction to test
     * @return {@link IRelationship}
     */
    IRelationship getRelationTo(IFaction other);

    /**
     * Method to get the relationship from a Player to another Player.
     * @param other IFactionPlayer to test
     * @return {@link IRelationship}
     */
    IRelationship getRelationTo(IFactionPlayer other);

    /**
     * Method to return the IFactionPlayer as an Object (API friendly)
     * @return object of API.
     */
    Object asObject();

    /**
     * Method to return IFactionPlayer as a String.
     * @return string details about faction-player.
     */
    default String asString() {
        return "FactionsPlayer{" +
                    "name=" + getName() + "," +
                    "uuid=" + getUniqueId() + "," +
                    "isOnline=" + isOnline() + "," +
                    "factionName=" + (getFaction() == null ? "N/A" : getFaction().getName()) +
                "}";
    }

}
