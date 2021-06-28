package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The FPlayer class stands for one FPlayer, this class defines the API behaviour within the scope of FactionsBridge.
 * <p>
 *     This class will be implemented by the newest implementation of the Bridge to control what an FPlayer can do.
 * </p>
 *
 * @author Callum Johnson
 * @since 04/06/2021 - 21:43
 */
public interface FPlayer {

    /**
     * Method to get the UUID of the FPlayer.
     *
     * @return {@link UUID} originated from Minecraft/Mojang.
     */
    @NotNull
    UUID getUniqueId();

    /**
     * Method to get the Name of the FPlayer.
     *
     * @return name of the player.
     */
    @NotNull
    String getName();

    /**
     * Method to get the {@link Faction} relative to the FPlayer.
     * <p>
     *     This method can return null, please use {@link #hasFaction()} to ensure the safest practice.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     * @see #hasFaction()
     */
    @Nullable
    Faction getFaction();

    /**
     * Method to determine if the FPlayer has a Faction
     *
     * @return {@code true} if they do.
     * @see #getFaction()
     */
    boolean hasFaction();

    /**
     * Method to get the OfflinePlayer related to the FPlayer.
     *
     * @return {@link OfflinePlayer} from the Bukkit API.
     */
    @NotNull
    OfflinePlayer getOfflinePlayer();

    /**
     * Method to get the Player related to the FPlayer.
     * <p>
     *     Please use {@link #isOnline()} before relying on this method as it can return null if the player isn't online.
     * </p>
     *
     * @return {@link Player} or {@code null}.
     * @see #isOnline()
     */
    @Nullable
    Player getPlayer();

    /**
     * Method to determine if the player is online or not.
     *
     * @return {@code true} if they are connected to the server.
     */
    boolean isOnline();

    /**
     * Method to get the power of the FPlayer.
     *
     * @return power value.
     */
    double getPower();

    /**
     * Method to set the power of the FPlayer.
     *
     * @param power to set.
     */
    void setPower(double power);

    /**
     * Method to obtain the title of the FPlayer.
     *
     * @return title or tag of the FPlayer.
     */
    @Nullable
    String getTitle();

    /**
     * Method to set the title of the FPlayer.
     *
     * @param title to set.
     */
    void setTitle(@NotNull String title);

    /**
     * Method to get the Role of the FPlayer.
     *
     * @return {@link Role}
     */
    @NotNull
    Role getRole();

    /**
     * Method to obtain the Relationship between this FPlayer and a Faction.
     *
     * @param faction to get relationship relative to this FPlayer.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    default Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        if (!hasFaction() || getFaction() == null) return Relationship.NONE;
        return getFaction().getRelationshipTo(faction);
    }

    /**
     * Method to obtain the Relationship between this FPlayer and a Faction.
     * <p>
     *      This method has been made to reflect both the previous FactionsBridge iterations and Faction APIs.
     * </p>
     *
     * @param faction to get relationship relative to this FPlayer.
     * @return {@link Relationship} enumeration.
     */
    @Deprecated
    @NotNull
    default Relationship getRelationTo(@NotNull AbstractFaction<?> faction) {
        return getRelationshipTo(faction);
    }

    /**
     * Method to obtain the Relationship between this FPlayer and another FPlayer.
     *
     * @param fPlayer to get relationship relative to this FPlayer.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    default Relationship getRelationshipTo(@NotNull FPlayer fPlayer) {
        if (fPlayer.getFaction() == null || !fPlayer.hasFaction()) return Relationship.NONE;
        return getRelationshipTo((AbstractFaction<?>) fPlayer.getFaction());
    }

    /**
     * Method to obtain the Relationship between this FPlayer and another FPlayer.
     * <p>
     *      This method has been made to reflect both the previous FactionsBridge iterations and Faction APIs.
     * </p>
     *
     * @param fPlayer to get relationship relative to this FPlayer.
     * @return {@link Relationship} enumeration.
     */
    @Deprecated
    @NotNull
    default Relationship getRelationTo(@NotNull FPlayer fPlayer) {
        return getRelationshipTo(fPlayer);
    }

}
