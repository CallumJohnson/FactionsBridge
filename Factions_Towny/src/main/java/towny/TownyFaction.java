package towny;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.*;
import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Towny implementation of {@link Faction}.
 * Object Target: {@link Resident}.
 *
 * @author Callum Johnson
 * @since 01/07/2021 - 10:26
 */
public class TownyFaction extends AbstractFaction<Town> {

    /**
     * Constructor to create an AbstractFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public TownyFaction(@NotNull Town faction) {
        super(faction);
    }

    /**
     * Method to get the Id of the Faction.
     * <p>
     * Almost all implementations use a String for Ids so I will enforce this to reduce variety.
     * </p>
     *
     * @return Id of the Faction of type String.
     */
    @Override
    public @NotNull String getId() {
        return faction.getUUID().toString();
    }

    /**
     * Method to obtain the Name or Tag of the Faction.
     *
     * @return Name or Tag of the Faction
     * @see #getTag()
     */
    @Override
    public @NotNull String getName() {
        return faction.getName();
    }

    /**
     * Method to obtain the Leader of the Faction.
     * <p>
     * Due to the nature of some of the implementations I will support, this can be {@code null}.
     * </p>
     *
     * @return {@link FPlayer} or {@code null}.
     */
    @Override
    public @Nullable FPlayer getLeader() {
        return new TownyFPlayer(faction.getMayor());
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @Override
    public @NotNull List<Claim> getAllClaims() {
        return faction.getTownBlocks().stream().map(TownyClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @Override
    public @NotNull List<FPlayer> getMembers() {
        final List<FPlayer> collect = faction.getResidents().stream().map(TownyFPlayer::new).collect(Collectors.toList());
        boolean addOwner = collect.stream().noneMatch(fPlayer -> fPlayer.getRole().equals(Role.getOwner()));
        if (addOwner) collect.add(getLeader());
        return collect;
    }

    /**
     * Method to set the home of the Faction.
     *
     * @param location to set as the home location.
     */
    @Override
    public void setHome(@NotNull Location location) {
        try {
            faction.setSpawn(location);
        } catch (TownyException e) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "setHome(location)", "TownyException Detected");
        }
    }

    /**
     * Method to obtain the home of the Faction.
     *
     * @return {@link Location} of the home for the Faction.
     */
    @Override
    public @Nullable Location getHome() {
        if (!faction.hasSpawn()) return null;
        try {
            return faction.getSpawn();
        } catch (TownyException e) {
            if (bridge.catch_exceptions) return null;
            return (Location) methodError(getClass(), "getHome()", "Failed to get Home location");
        }
    }

    /**
     * Method to determine if the Faction is any form of Server-Faction.
     * <p>
     * A server faction is defined as a faction which is only for server operators to control the land-claiming and
     * PVP'ing aspect of the game.
     * <br>For example, FactionsUUID implements SafeZone, WarZone and Wilderness as Server-Factions.
     * </p>
     *
     * @return {@code true} if the Faction is a Server-Faction
     */
    @Override
    public boolean isServerFaction() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isServerFaction()");
    }

    /**
     * Method to determine if the Faction is Wilderness or not.
     *
     * @return {@code true} if the Faction is Wilderness (none).
     */
    @Override
    public boolean isWilderness() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isWilderness()");
    }

    /**
     * Method to determine if the Faction is WarZone or not.
     *
     * @return {@code true} if the Faction is WarZone.
     */
    @Override
    public boolean isWarZone() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isWarZone()");
    }

    /**
     * Method to determine if the Faction is SafeZone or not.
     *
     * @return {@code true} if the Faction is SafeZone (usually used for Spawnpoints).
     */
    @Override
    public boolean isSafeZone() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isSafeZone()");
    }

    /**
     * Method to determine if the Faction is in Peaceful mode.
     *
     * @return {@code true} if yes.
     */
    @Override
    public boolean isPeaceful() {
        if (bridge.catch_exceptions) return false;
        return (boolean) unsupported(getProvider(), "isPeaceful()");
    }

    /**
     * Method to obtain the power of the Faction.
     *
     * @return the power of the Faction.
     */
    @Override
    public double getPower() {
        return faction.getTotalBlocks();
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
     * Method to get the points of the Faction.
     *
     * @return points of the Faction.
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
     * Method to obtain the Bank Balance of the Faction.
     *
     * @return bank balance in the form of {@link Double}.
     */
    @Override
    public double getBank() {
        try {
            return faction.getAccount().getHoldingBalance();
        } catch (EconomyException e) {
            if (bridge.catch_exceptions) return 0.0D;
            return (double) methodError(getClass(), "getBank()", "EconomyException encountered.");
        }
    }

    /**
     * Method to get a Warp set by the faction by its name.
     *
     * @param name of the Warp to get
     * @return {@link Location} related to that name, or {@code null}.
     */
    @Override
    public @Nullable Location getWarp(@NotNull String name) {
        if (bridge.catch_exceptions) return null;
        return (Location) unsupported(getProvider(), "getWarp(name)");
    }

    /**
     * Method to create a Warp manually.
     *
     * @param name     of the Warp to create.
     * @param location of the warp.
     */
    @Override
    public void createWarp(@NotNull String name, @NotNull Location location) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "createWarp(name, location)");
    }

    /**
     * Method to get all of the Warps from the Faction.
     * <p>
     * The HashMap returned is of the form "String:Location".
     * </p>
     *
     * @return {@link HashMap} where each entry is a 'warp'.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @NotNull HashMap<String, Location> getWarps() {
        if (bridge.catch_exceptions) return new HashMap<>();
        return (HashMap) unsupported(getProvider(), "getWarps()");
    }

    /**
     * Method to delete a warp by name.
     *
     * @param name of the warp to delete.
     */
    @Override
    public void deleteWarp(@NotNull String name) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "deleteWarp(name)");
    }

    /**
     * Method to clear the Strikes related to the Faction
     */
    @Override
    public void clearStrikes() {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "clearStrikes()");
    }

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void addStrike(String sender, String reason) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "addStrike(sender, reason)");
    }

    /**
     * Method to remove a Strike from the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void removeStrike(String sender, String reason) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "removeStrike(sender, reason)");
    }

    /**
     * Method to obtain the total Strikes related to a Faction.
     *
     * @return total strikes.
     */
    @Override
    public int getTotalStrikes() {
        if (bridge.catch_exceptions) return 0;
        return (int) unsupported(getProvider(), "getTotalStrikes()");
    }

    /**
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @Override
    public @NotNull Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        if (!this.faction.hasNation() || !((Town) faction.getFaction()).hasNation()) {
            return Relationship.DEFAULT_RELATIONSHIP;
        }
        try {
            final Nation thisNation = this.faction.getNation();
            final Nation otherNation = ((Town) faction.getFaction()).getNation();
            if (thisNation.equals(otherNation)) return Relationship.MEMBER;
            if (getId().equals(faction.getId())) return Relationship.MEMBER;
            final Nation enemy = thisNation.getEnemies().stream()
                    .filter(nation -> nation.equals(otherNation))
                    .findFirst().orElse(null);
            if (enemy != null) return Relationship.ENEMY;
            final Nation ally = thisNation.getAllies().stream()
                    .filter(nation -> nation.equals(otherNation))
                    .findFirst().orElse(null);
            if (ally != null) return Relationship.ALLY;
            return Relationship.DEFAULT_RELATIONSHIP;
        } catch (TownyException exception) {
            if (bridge.catch_exceptions) return Relationship.DEFAULT_RELATIONSHIP;
            return (Relationship) methodError(getClass(), "getRelationshipTo(faction)", "Failed to retrieve Nations.");
        }
    }

}
