package savagefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Role;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.util.LazyLocation;
import com.massivecraft.factions.zcore.persist.MemoryFaction;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship.getRelationship;

/**
 * SavageFactions implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction}.
 * Object Target: {@link Faction}.
 */
public class SavageFactionsFaction extends AbstractFaction<Faction> {

    /**
     * Constructor to create a FactionsUUIDFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public SavageFactionsFaction(@NotNull Faction faction) {
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
    @NotNull
    @Override
    public String getId() {
        return faction.getId();
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
        return faction.getTag();
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
        try {
            return new SavageFactionsFPlayer(faction.getFPlayerAdmin());
        } catch (Exception ex) {
            for (final FPlayer member : getMembers()) {
                if (member.getRole().equals(Role.getOwner())) {
                    return member;
                }
            }
            if (bridge.catch_exceptions) return null;
            return (FPlayer) methodError(getClass(), "getLeader()", "Failed to find Leader for Faction.");
        }
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getAllClaims().stream().map(SavageFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getFPlayers().stream().map(SavageFactionsFPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to set the home of the Faction.
     *
     * @param location to set as the home location.
     */
    @Override
    public void setHome(@NotNull Location location) {
        faction.setHome(location);
    }

    /**
     * Method to obtain the home of the Faction.
     *
     * @return {@link Location} of the home for the Faction.
     */
    @Nullable
    @Override
    public Location getHome() {
        return faction.getHome();
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
        return (isSafeZone() || isWarZone() || isWilderness());
    }

    /**
     * Method to determine if the Faction is Wilderness or not.
     *
     * @return {@code true} if the Faction is Wilderness (none).
     */
    @Override
    public boolean isWilderness() {
        return faction.isWilderness();
    }

    /**
     * Method to determine if the Faction is WarZone or not.
     *
     * @return {@code true} if the Faction is WarZone.
     */
    @Override
    public boolean isWarZone() {
        return faction.isWarZone();
    }

    /**
     * Method to determine if the Faction is SafeZone or not.
     *
     * @return {@code true} if the Faction is SafeZone (usually used for Spawnpoints).
     */
    @Override
    public boolean isSafeZone() {
        return faction.isSafeZone();
    }

    /**
     * Method to determine if the Faction is in Peaceful mode.
     *
     * @return {@code true} if yes.
     */
    @Override
    public boolean isPeaceful() {
        return faction.isPeaceful();
    }

    /**
     * Method to obtain the power of the Faction.
     *
     * @return the power of the Faction.
     */
    @Override
    public double getPower() {
        return faction.getPower();
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
        return faction.getPoints();
    }

    /**
     * Method to override the points of the Faction to the specified amount.
     *
     * @param points to set for the Faction.
     * @see #getPoints()
     */
    @Override
    public void setPoints(int points) {
        faction.givePoints(points-getPoints());
    }

    /**
     * Method to obtain the Bank Balance of the Faction.
     * <p>
     * Credit goes to mbax for informing me of proper API usage.
     * </p>
     *
     * @return bank balance in the form of {@link Double}.
     */
    @Override
    public double getBank() {
        if (!Econ.shouldBeUsed()) {
            if (bridge.catch_exceptions) return 0.0D;
            return (double) unsupported(getProvider(), "Economy hasn't been initialised.");
        }
        if (!Econ.hasAccount(faction.getAccountId())) return 0.0;
        return Econ.getBalance(faction.getAccountId());
    }

    /**
     * Method to set the balance of the Faction.
     *
     * @param balance to set.
     */
    @Override
    public void setBank(double balance) {
        if (!Econ.shouldBeUsed()) {
            if (bridge.catch_exceptions) return;
            else methodError(getClass(), "setBank(balance)", "Economy hasn't been initialised.");
            return;
        }
        Econ.setBalance(faction.getAccountId(), balance);
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
        LazyLocation location = faction.getWarp(name);
        return location == null ? null : location.getLocation();
    }

    /**
     * Method to create a Warp manually.
     *
     * @param name     of the Warp to create.
     * @param location of the warp.
     */
    @Override
    public void createWarp(@NotNull String name, @NotNull Location location) {
        faction.setWarp(name, new LazyLocation(location));
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
    public HashMap<String, Location> getWarps() {
        HashMap<String, Location> map = new HashMap<>();
        for (String s : faction.getWarps().keySet()) {
            Location loc = getWarp(s);
            if (loc == null) continue;
            map.put(s, loc);
        }
        return map;
    }

    /**
     * Method to delete a warp by name.
     *
     * @param name of the warp to delete.
     */
    @Override
    public void deleteWarp(@NotNull String name) {
        faction.removeWarp(name);
    }

    /**
     * Method to clear the Strikes related to the Faction
     */
    @Override
    public void clearStrikes() {
        faction.setStrikes(0, true);
    }

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void addStrike(String sender, String reason) {
        faction.setStrikes(faction.getStrikes()+1, true);
    }

    /**
     * Method to remove a Strike from the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void removeStrike(String sender, String reason) {
        faction.setStrikes(faction.getStrikes()-1, true);
    }

    /**
     * Method to obtain the total Strikes related to a Faction.
     *
     * @return total strikes.
     */
    @Override
    public int getTotalStrikes() {
        return faction.getStrikes();
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
        return getRelationship(this.faction.getRelationTo((RelationParticipator) faction.getFaction()).name());
    }

}
