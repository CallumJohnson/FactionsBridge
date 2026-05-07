package lands;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import me.angeschossen.lands.api.applicationframework.util.ULID;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.memberholder.MemberHolder;
import me.angeschossen.lands.api.nation.Nation;
import me.angeschossen.lands.api.relations.Relation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lands implementation of {@link Faction}.
 * Object Target: {@link Land}.
 */
public class LandsFaction extends AbstractFaction<Land> {

    /**
     * Constructor to create a LandsFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public LandsFaction(@NotNull Land faction) {
        super(faction);
        new LandsFactionsAPI();
    }

    /**
     * Method to get the Id of the Faction.
     * <p>
     * Almost all implementations use a String for Ids so I will enforce this to reduce variety.
     * </p>
     *
     * @return Id of the Faction of type String.
     */
    @NotNull
    @Override
    public String getId() {
        return faction.getULID().toString();
    }

    /**
     * Method to obtain the Name or Tag of the Faction.
     *
     * @return Name or Tag of the Faction
     * @see #getTag()
     */
    @NotNull
    @Override
    public String getName() {
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
    @Nullable
    @Override
    public FPlayer getLeader() {
        final UUID factionOwnerUID = faction.getOwnerUID();
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(factionOwnerUID);
        return new LandsFPlayer(offlinePlayer);
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getContainers().stream()
            .flatMap(container -> container.getChunks().stream()
                .map(chunk -> new LandsClaim(chunk, container.getWorld())))
            .collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getTrustedPlayers().stream().map(Bukkit::getOfflinePlayer).map(LandsFPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to set the home of the Faction.
     *
     * @param location to set as the home location.
     */
    @Override
    public void setHome(@NotNull Location location) {
        faction.setSpawn(location);
    }

    /**
     * Method to obtain the home of the Faction.
     *
     * @return {@link Location} of the home for the Faction.
     */
    @Nullable
    @Override
    public Location getHome() {
        return faction.getSpawn();
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
        return false;
    }

    /**
     * Method to determine if the Faction is Wilderness or not.
     *
     * @return {@code true} if the Faction is Wilderness (none).
     */
    @Override
    public boolean isWilderness() {
        return false;
    }

    /**
     * Method to determine if the Faction is WarZone or not.
     *
     * @return {@code true} if the Faction is WarZone.
     */
    @Override
    public boolean isWarZone() {
        return false;
    }

    /**
     * Method to determine if the Faction is SafeZone or not.
     *
     * @return {@code true} if the Faction is SafeZone (usually used for Spawnpoints).
     */
    @Override
    public boolean isSafeZone() {
        return false;
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
        if (bridge.catch_exceptions) return 0.0;
        return (double) unsupported(getProvider(), "setPower(power)");
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
        return faction.getBalance();
    }

    /**
     * Method to set the balance of the Faction.
     *
     * @param balance to set.
     */
    @Override
    public void setBank(double balance) {
        faction.setBalance(balance);
    }

    /**
     * Method to get a Warp set by the faction by its name.
     *
     * @param name of the Warp to get
     * @return {@link Location} related to that name, or {@code null}.
     */
    @Nullable
    @Override
    public Location getWarp(@NotNull String name) {
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
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, Location> getWarps() {
        if (bridge.catch_exceptions) return new HashMap<>();
        return (HashMap<String, Location>) unsupported(getProvider(), "getWarps()");
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
        unsupported(getProvider(), "addStrike(String sender, String reason)");
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
        unsupported(getProvider(), "removeStrike(String sender, String reason)");
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
    @NotNull
    @Override
    public Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        final Optional<Relationship> relation = getRelationViaAPI(faction);
        if (relation.isPresent()) {
            return relation.get();
        }
        boolean isEnemy = this.faction.getEnemies().stream().anyMatch(member -> member.getULID() == ULID.fromString(faction.getId()));
        if (isEnemy) {
            return Relationship.ENEMY;
        }
        boolean isAlly = this.faction.getAllies().stream().anyMatch(member -> member.getULID() == ULID.fromString(faction.getId()));
        if (isAlly) {
            return Relationship.ALLY;
        }
        if (this.faction.getULID() == ((Nation) faction.getFaction()).getULID()) {
            return Relationship.MEMBER;
        }
        return Relationship.NONE;
    }

    private Optional<Relationship> getRelationViaAPI(@NotNull AbstractFaction<?> faction) {
        final ULID otherFactionULID = ((Nation) faction.getFaction()).getULID();
        final MemberHolder member = LandsFactionsAPI.integration.getMemberholderByULID(otherFactionULID);
        if (member != null) {
            final Relation relation = this.faction.getRelation(member);
            switch (relation) {
                case ALLY:
                    return Optional.of(Relationship.ALLY);
                case ENEMY:
                    return Optional.of(Relationship.ENEMY);
                case NEUTRAL:
                    if (this.faction.getULID() == otherFactionULID) {
                        return Optional.of(Relationship.MEMBER);
                    }
                    return Optional.of(Relationship.NONE);
                default:
                    return Optional.empty();
            }
        } else {
            return Optional.empty();
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
        return "Lands";
    }

}
