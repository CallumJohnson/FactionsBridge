package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Faction class stands for one Faction, this class defines the API behaviour within the scope of FactionsBridge.
 * <p>
 *      This class will be implemented by the newest implementation of the Bridge to control what a Faction can do.
 * </p>
 *
 * @author Callum Johnson
 * @since 04/06/2021 - 20:50
 */
public interface Faction {

    /**
     * Method to get the Id of the Faction.
     * <p>
     *      Almost all implementations use a String for Ids so I will enforce this to reduce variety.
     * </p>
     *
     * @return Id of the Faction of type String.
     */
    @NotNull
    String getId();

    /**
     * Method to obtain the Name or Tag of the Faction.
     *
     * @return Name or Tag of the Faction
     * @see #getTag()
     */
    @NotNull
    String getName();

    /**
     * Method to obtain the Name or Tag of the Faction.
     *
     * @return Name or Tag of the Faction
     * @see #getName()
     */
    @NotNull
    default String getTag() {
        return getName();
    }

    /**
     * Method to obtain the Leader of the Faction.
     * <p>
     *      Due to the nature of some of the implementations I will support, this can be {@code null}.
     * </p>
     *
     * @return {@link FPlayer} or {@code null}.
     */
    @Nullable
    FPlayer getLeader();

    /**
     * Method to obtain the Leader's name.
     * <p>
     *      This uses the method {@link #getLeader()} to obtain the Leader's name,
     *      which therefore means this can return {@code null.}
     * </p>
     *
     * @return Leader name or {@code null}.
     */
    @Nullable
    default String getLeaderName() {
        try {
            return Objects.requireNonNull(getLeader()).getName();
        } catch (Exception ex) {
            FactionsBridge.get().exception(ex, "Failed to discover Leader's name.");
            return null;
        }
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    List<Claim> getAllClaims();

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    List<FPlayer> getMembers();

    /**
     * Method to obtain all Online Members of the Faction.
     *
     * @return {@link List} of {@link FPlayer} who are online.
     * @see #getMembers()
     */
    @NotNull
    default List<FPlayer> getOnlineMembers() {
        return getMembers().stream().filter(FPlayer::isOnline).collect(Collectors.toList());
    }

    /**
     * Method to obtain all Offline Members of the Faction.
     *
     * @return {@link List} of {@link FPlayer} who are offline.
     * @see #getMembers()
     */
    @NotNull
    default List<FPlayer> getOfflineMembers() {
        return getMembers().stream().filter(fPlayer -> !fPlayer.isOnline()).collect(Collectors.toList());
    }

    /**
     * Method to set the home of the Faction.
     *
     * @param location to set as the home location.
     */
    void setHome(@NotNull Location location);

    /**
     * Method to obtain the home of the Faction.
     *
     * @return {@link Location} of the home for the Faction.
     */
    @Nullable
    Location getHome();

    /**
     * Method to determine if the Faction is any form of Server-Faction.
     * <p>
     *      A server faction is defined as a faction which is only for server operators to control the land-claiming and
     *      PVP'ing aspect of the game.
     *      <br>For example, FactionsUUID implements SafeZone, WarZone and Wilderness as Server-Factions.
     * </p>
     *
     * @return {@code true} if the Faction is a Server-Faction
     */
    boolean isServerFaction();

    /**
     * Method to determine if the Faction is Wilderness or not.
     *
     * @return {@code true} if the Faction is Wilderness (none).
     */
    boolean isWilderness();

    /**
     * Method to determine if the Faction is WarZone or not.
     *
     * @return {@code true} if the Faction is WarZone.
     */
    boolean isWarZone();

    /**
     * Method to determine if the Faction is SafeZone or not.
     *
     * @return {@code true} if the Faction is SafeZone (usually used for Spawnpoints).
     */
    boolean isSafeZone();

    /**
     * Method to determine if the Faction is in Peaceful mode.
     *
     * @return {@code true} if yes.
     */
    boolean isPeaceful();

    /**
     * Method to obtain the power of the Faction.
     */
    double getPower();

    /**
     * Method to set the power of a Faction.
     *
     * @param power to set.
     */
    void setPower(double power);

    /**
     * Method to get the points of the Faction.
     *
     * @return points of the Faction.
     */
    int getPoints();

    /**
     * Method to override the points of the Faction to the specified amount.
     *
     * @param points to set for the Faction.
     * @see #getPoints()
     */
    void setPoints(int points);

    /**
     * Method to obtain the Bank Balance of the Faction.
     *
     * @return bank balance in the form of {@link Double}.
     */
    double getBank();

    /**
     * Method to get a Warp set by the faction by its name.
     *
     * @param name of the Warp to get
     * @return {@link Location} related to that name, or {@code null}.
     */
    @Nullable
    Location getWarp(@NotNull String name);

    /**
     * Method to create a Warp manually.
     *
     * @param name of the Warp to create.
     * @param location of the warp.
     */
    void createWarp(@NotNull String name, @NotNull Location location);

    /**
     * Method to get all of the Warps from the Faction.
     * <p>
     *      The HashMap returned is of the form "String:Location".
     * </p>
     *
     * @return {@link HashMap} where each entry is a 'warp'.
     */
    @NotNull
    HashMap<String, Location> getWarps();

    /**
     * Method to delete a warp by name.
     *
     * @param name of the warp to delete.
     */
    void deleteWarp(@NotNull String name);

    /**
     * Method to clear the Strikes related to the Faction
     */
    void clearStrikes();

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    void addStrike(String sender, String reason);

    /**
     * Method to remove a Strike from the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    void removeStrike(String sender, String reason);

    /**
     * Method to obtain the total Strikes related to a Faction.
     *
     * @return total strikes.
     */
    int getTotalStrikes();

    /**
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction);

    /**
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    default Relationship getRelationshipTo(@NotNull Faction faction) {
        return getRelationshipTo(((AbstractFaction<?>) faction));
    }

    /**
     * Method to obtain the Relationship between this Faction and another Faction.
     * <p>
     *      This method has been made to reflect both the previous FactionsBridge iterations and Faction APIs.
     * </p>
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     * @see #getRelationshipTo(AbstractFaction)
     */
    @Deprecated
    @NotNull
    default Relationship getRelationTo(@NotNull AbstractFaction<?> faction) {
        return getRelationshipTo(faction);
    }

    /**
     * Method to obtain the Relationship between this Faction and an FPlayer.
     *
     * @param fPlayer to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     * @see #getRelationshipTo(AbstractFaction)
     */
    @NotNull
    default Relationship getRelationshipTo(@NotNull FPlayer fPlayer) {
        if (fPlayer.getFaction() == null || !fPlayer.hasFaction()) return Relationship.NONE;
        return getRelationshipTo((AbstractFaction<?>) fPlayer.getFaction());
    }

    /**
     * Method to obtain the Relationship between this Faction and an FPlayer.
     * <p>
     *      This method has been made to reflect both the previous FactionsBridge iterations and Faction APIs.
     * </p>
     *
     * @param fPlayer to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     * @see #getRelationshipTo(FPlayer)
     */
    @NotNull
    @Deprecated
    default Relationship getRelationTo(@NotNull FPlayer fPlayer) {
        return getRelationshipTo(fPlayer);
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    String getProvider();

}
