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
 * @version 1.0
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

}
