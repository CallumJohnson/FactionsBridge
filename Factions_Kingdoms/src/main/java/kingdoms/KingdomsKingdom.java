package kingdoms;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.kingdom.Kingdom;
import org.kingdoms.constants.kingdom.model.KingdomRelation;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kingdoms implementation of {@link Faction}.
 * Object Target: {@link Kingdom}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:11
 */
public class KingdomsKingdom extends AbstractFaction<Kingdom> {

    /**
     * Constructor to create an KingdomsKingdom.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public KingdomsKingdom(@NotNull Kingdom faction) {
        super(faction);
    }

    /**
     * Method to get the Id of the Faction.
     *
     * @return Id in the form of String.
     */
    @NotNull
    @Override
    public String getId() {
        return faction.getId().toString();
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @NotNull
    @Override
    public String getName() {
        return faction.getName();
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public FPlayer getLeader() {
        return new KingdomsPlayer(faction.getKing());
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link Claim}
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getLands().stream().map(KingdomsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getKingdomPlayers().stream().map(KingdomsPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to set the 'Home' of a Faction.
     *
     * @param location to set as the new home.
     */
    @Override
    public void setHome(@NotNull Location location) {
        faction.setHome(location);
    }

    /**
     * Method to retrieve the 'Home' of the Faction.
     *
     * @return {@link Bukkit}, {@link Location}.
     */
    @Override
    public Location getHome() {
        return faction.getHome();
    }

    /**
     * Method to test if this Faction is a Server Faction
     * <p>
     * Server Factions: Wilderness, SafeZone, WarZone.
     * </p>
     *
     * @return {@code true} if yes, {@code false} if no.
     */
    @Override
    public boolean isServerFaction() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isServerFaction()");
    }

    /**
     * Method to determine if the IFaction is the WarZone.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isWarZone() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isWarZone()");
    }

    /**
     * Method to determine if the IFaction is a SafeZone.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isSafeZone() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isSafeZone()");
    }

    /**
     * Method to determine if the IFaction is the Wilderness.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isWilderness() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isWilderness()");
    }

    /**
     * Method to determine if the Faction is in a Peaceful State.
     *
     * @return {@code true} if yes, {@code false} if no.
     */
    @Override
    public boolean isPeaceful() {
        return faction.isPacifist();
    }

    /**
     * Method to obtain the power of the Faction.
     *
     * @return the power of the Faction.
     */
    @Override
    public double getPower() {
        return faction.getMaxLands();
    }

    /**
     * Method to set the power of a Faction.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "setPower(power)");
    }

    /**
     * Method to get the bank balance of the Faction.
     *
     * @return in the form of Double.
     */
    @Override
    public double getBank() {
        return faction.getBank();
    }

    /**
     * Method to set the balance of the Faction.
     *
     * @param balance to set.
     */
    @Override
    public void setBank(double balance) {
        faction.setBank(balance);
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
        if (bridge.catch_exceptions) return 0;
        return (int) unsupported(getProvider(), "getPoints()");
    }

    /**
     * Method to override the points of the Faction to the specified amount.
     *
     * @param points to set for the Faction.
     * @see #getPoints()
     */
    @Override
    public void setPoints(int points) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "setPoints(points)");
    }

    /**
     * Method to get the Location of a Faction Warp by Name.
     *
     * @param name of the warp
     * @return {@link Location} of the warp.
     */
    @Override
    public Location getWarp(@NotNull String name) {
        if (bridge.catch_exceptions) return null;
        return (Location) unsupported(getProvider(), "getWarp(name)");
    }

    /**
     * Method to retrieve all warps.
     * <p>
     * This method returns a hashmap of String names and Locations.
     * </p>
     *
     * @return hashmap of all warps.
     */
    @NotNull
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public HashMap<String, Location> getWarps() {
        if (bridge.catch_exceptions) return new HashMap<>();
        return (HashMap) unsupported(getProvider(), "setPoints(points)");
    }

    /**
     * Method to create a warp for the Faction.
     *
     * @param name     of the warp.
     * @param location of the warp.
     */
    @Override
    public void createWarp(@NotNull String name, @NotNull Location location) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "createWarp(name, location)");
    }

    /**
     * Method to manually remove a Warp using its name.
     *
     * @param name of the warp to be deleted.
     */
    @Override
    public void deleteWarp(@NotNull String name) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "deleteWarp(name)");
    }

    /**
     * Add strikes to a Faction.
     *
     * @param sender who desires to Strike the Faction.
     * @param reason for the Strike.
     */
    @Override
    public void addStrike(String sender, String reason) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "addStrike(Sender, String)");
    }

    /**
     * Remove strike from a Faction.
     *
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    @Override
    public void removeStrike(String sender, String reason) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "removeStrike(Sender, String)");
    }

    /**
     * Method to obtain the Total Strikes a Faction has.
     *
     * @return integer amount of Strikes.
     */
    @Override
    public int getTotalStrikes() {
        if (bridge.catch_exceptions) return 0;
        return (int) unsupported(getProvider(), "getTotalStrikes()");
    }

    /**
     * Method to clear all Strikes.
     */
    @Override
    public void clearStrikes() {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "clearStrikes()");
    }

    /**
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    @Override
    public Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        KingdomRelation relationWith = this.faction.getRelationWith((Kingdom) faction.getFaction());
        switch (relationWith) {
            case NEUTRAL:
                return Relationship.NONE;
            case TRUCE:
                return Relationship.TRUCE;
            case ALLY:
                return Relationship.ALLY;
            case ENEMY:
                return Relationship.ENEMY;
            default:
                return Relationship.MEMBER;
        }
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    @Override
    public String getProvider() {
        return "KingdomsX";
    }

}
