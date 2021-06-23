package cc.javajobs.factionsbridge.bridge.impl.factionsuuid;

import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Rel;
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

public class FactionsUUIDFaction extends AbstractFaction<Faction> {

    /**
     * Constructor to create a FactionsUUIDFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public FactionsUUIDFaction(@NotNull Faction faction) {
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
        return new FactionsUUIDFPlayer(faction.getFPlayerAdmin());
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List < Claim >} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getAllClaims().stream().map(FactionsUUIDClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List<FPlayer>} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getFPlayers().stream().map(FactionsUUIDFPlayer::new).collect(Collectors.toList());
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
        unsupported(getProvider(), "setPoints(int points)");
    }

    /**
     * Method to obtain the Bank Balance of the Faction.
     * <p>
     *     Credit goes to mbax for informing me of proper API usage.
     * </p>
     *
     * @return bank balance in the form of {@link Double}.
     */
    @Override
    public double getBank() {
        try {
            if (!Econ.hasAccount(faction)) return 0.0;
            return Econ.getBalance(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0.0;
            bridge.exception(ex, "Economy Potentially Disabled.");
            return (double) methodError(getClass(), "getBank()",
                    ex.getMessage() == null ? "Economy Potentially Disabled." : ex.getMessage());
        }
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
     * @param name of the Warp to create.
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
        final Object one = this.faction;
        final Object two = faction.getFaction();
        try {
            Class.forName("com.massivecraft.factions.zcore.persist.MemoryFaction");
            final MemoryFaction oneM = (MemoryFaction) one;
            final MemoryFaction twoM = (MemoryFaction) two;
            return Relationship.getRelationship(oneM.getRelationTo(twoM).name());
        } catch (ClassNotFoundException ignored) {}
        try {
            Class.forName("com.massivecraft.factions.data.MemoryFaction");
            final com.massivecraft.factions.data.MemoryFaction oneM = (com.massivecraft.factions.data.MemoryFaction) one;
            final com.massivecraft.factions.data.MemoryFaction twoM = (com.massivecraft.factions.data.MemoryFaction) two;
            return Relationship.getRelationship(oneM.getRelationTo(twoM).name());
        } catch (ClassNotFoundException ignored) {}
        if (bridge.catch_exceptions) return Relationship.NONE;
        else return (Relationship) methodError(getClass(), "getRelationshipTo(AbstractionFaction<?>)",
                "Can't find relationship.");
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    @Override
    public String getProvider() {
        return "FactionsUUID";
    }

}
