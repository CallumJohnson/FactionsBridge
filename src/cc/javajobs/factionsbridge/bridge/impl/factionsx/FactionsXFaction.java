package cc.javajobs.factionsbridge.bridge.impl.factionsx;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IRelationship;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import net.prosavage.factionsx.core.Faction;
import net.prosavage.factionsx.manager.GridManager;
import net.prosavage.factionsx.persist.data.wrappers.DataLocation;
import net.prosavage.factionsx.util.Relation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * FactionsX implementation of IFaction.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:38
 */
public class FactionsXFaction implements IFaction {

    private final Faction faction;

    public FactionsXFaction(Faction faction) {
        this.faction = faction;
    }

    /**
     * Method to get the Id of the Faction.
     *
     * @return Id in the form of String.
     */
    @Override
    public String getId() {
        return String.valueOf(faction.getId());
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @Override
    public String getName() {
        return faction.getTag();
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public IFactionPlayer getLeader() {
        return new FactionsXPlayer(faction.getLeader());
    }

    /**
     * Method to get the name of the Leader.
     *
     * @return name of the person who created the Faction.
     * @see IFaction#getLeader()
     */
    @Override
    public String getLeaderName() {
        return getLeader().getName();
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link IClaim}
     */
    @Override
    public List<IClaim> getAllClaims() {
        return GridManager.INSTANCE.getAllClaims(faction)
                .stream().map(FactionsXClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return faction.getMembers().stream().map(FactionsXPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to set the 'Home' of a Faction.
     *
     * @param location to set as the new home.
     */
    @Override
    public void setHome(Location location) {
        if (location == null || location.getWorld() == null) return;
        faction.setHome(new DataLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        ));
    }

    /**
     * Method to retrieve the 'Home' of the Faction.
     *
     * @return {@link Bukkit}, {@link Location}.
     */
    @Override
    public Location getHome() {
        return faction.getHome().getLocation();
    }

    /**
     * Method to get the relationship between two Factions.
     *
     * @param other faction to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFaction other) {
        if (getId().equals(other.getId())) return IRelationship.MEMBER;
        Relation relationTo = faction.getRelationTo((Faction) other.asObject());
        switch (relationTo) {
            case ALLY:
                return IRelationship.ALLY;
            case ENEMY:
                return IRelationship.ENEMY;
            case TRUCE:
                return IRelationship.TRUCE;
            default:
                return IRelationship.NONE;
        }
    }

    /**
     * Method to get the relationship between an IFaction and an IFactionPlayer.
     *
     * @param other IFactionPlayer to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFactionPlayer other) {
        return getRelationTo(other.getFaction());
    }

    /**
     * Method to return the IFaction as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return faction;
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
        return faction.isSystemFaction();
    }

    /**
     * Method to determine if the Faction is in a Peaceful State.
     *
     * @return {@code true} if yes, {@code false} if no.
     */
    @Override
    public boolean isPeaceful() {
        throw new BridgeMethodUnsupportedException("FactionsX doesn't support isPeaceful().");
    }

    /**
     * Method to get the bank balance of the Faction.
     *
     * @return in the form of Double.
     */
    @Override
    public double getBank() {
        return faction.getBank().getAmount();
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
        throw new BridgeMethodUnsupportedException("FactionsX doesn't support getPoints().");
    }

    /**
     * Method to get the Location of a Faction Warp by Name.
     *
     * @param name of the warp
     * @return {@link Location} of the warp.
     */
    @Override
    public Location getWarp(String name) {
        return faction.getWarp(name).getDataLocation().getLocation();
    }

    /**
     * Method to retrieve all warps.
     * <p>
     * This method returns a hashmap of String names and Locations.
     * </p>
     *
     * @return hashmap of all warps.
     */
    @Override
    public HashMap<String, Location> getWarps() {
        return faction.getWarps().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // k
                        stringWarpEntry -> stringWarpEntry.getValue().getDataLocation().getLocation(), // v
                        (a, b) -> b, HashMap::new) //assign
                );
    }

    /**
     * Method to create a warp for the Faction.
     *
     * @param name     of the warp.
     * @param location of the warp.
     */
    @Override
    public void createWarp(String name, Location location) {
        if (location == null || location.getWorld() == null) {
            throw new BridgeMethodException(getClass(), "createWarp(String name, null)");
        }
        faction.setWarp(name, null, new DataLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        ));
    }

    /**
     * Method to manually remove a Warp using its name.
     *
     * @param name of the warp to be deleted.
     */
    @Override
    public void deleteWarp(String name) {
        faction.removeWarp(name);
    }

    /**
     * Add strikes to a Faction.
     *
     * @param sender who desires to Strike the Faction.
     * @param reason for the Strike.
     */
    @Override
    public void addStrike(String sender, String reason) {
        // This is the only one that requires this :/
        faction.addStrike(Bukkit.getConsoleSender(), reason);
    }

    /**
     * Remove strike from a Faction.
     *
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    @Override
    public void removeStrike(String sender, String reason) {
        int removeMe = IntStream.range(0, faction.getStrikes().size())
                .filter(i -> faction.getStrikes().get(i).equalsIgnoreCase(reason))
                .findFirst().orElse(-1);
        if (removeMe == -1) {
            throw new BridgeMethodException(getClass(), "removeStrike(Sender, String)");
        }
    }

    /**
     * Method to obtain the Total Strikes a Faction has.
     *
     * @return integer amount of Strikes.
     */
    @Override
    public int getTotalStrikes() {
        return faction.getStrikes().size();
    }

    /**
     * Method to clear all Strikes.
     */
    @Override
    public void clearStrikes() {
        // This is the only one that requires this :/
        faction.clearStrikes(Bukkit.getConsoleSender());
    }

}
