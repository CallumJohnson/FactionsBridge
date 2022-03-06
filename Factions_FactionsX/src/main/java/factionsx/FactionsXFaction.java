package factionsx;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import net.prosavage.factionsx.core.Faction;
import net.prosavage.factionsx.manager.GridManager;
import net.prosavage.factionsx.persist.data.wrappers.DataLocation;
import net.prosavage.factionsx.util.Relation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FactionsX implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction}.
 * Object Target: {@link Faction}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:38
 */
public class FactionsXFaction extends AbstractFaction<Faction> {

    /**
     * Constructor to create an FactionsXFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public FactionsXFaction(@NotNull Faction faction) {
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
        return String.valueOf(faction.getId());
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @NotNull
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
    public FPlayer getLeader() {
        return new FactionsXPlayer(faction.getLeader());
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link Claim}
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return GridManager.INSTANCE.getAllClaims(faction)
                .stream().map(FactionsXClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getMembers().stream().map(FactionsXPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to set the 'Home' of a Faction.
     *
     * @param location to set as the new home.
     */
    @Override
    public void setHome(@NotNull Location location) {
        if (location.getWorld() == null) return;
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
     * Method to determine if the IFaction is the WarZone.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isWarZone() {
        return faction.isWarzone();
    }

    /**
     * Method to determine if the IFaction is a SafeZone.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isSafeZone() {
        return faction.isSafezone();
    }

    /**
     * Method to determine if the IFaction is the Wilderness.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isWilderness() {
        return faction.isWilderness();
    }

    /**
     * Method to determine if the Faction is in a Peaceful State.
     *
     * @return {@code true} if yes, {@code false} if no.
     */
    @Override
    public boolean isPeaceful() {
        return (boolean) unsupported(getProvider(), "isPeaceful()");
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
     * Method to get the bank balance of the Faction.
     *
     * @return in the form of Double.
     */
    @Override
    public double getBank() {
        return faction.getBank().getAmount();
    }

    /**
     * Method to set the balance of the Faction.
     *
     * @param balance to set.
     */
    @Override
    public void setBank(double balance) {
        faction.getBank().setAmount(balance);
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
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
    @NotNull
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
    public void createWarp(@NotNull String name, @NotNull Location location) {
        if (location.getWorld() == null) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "createWarp(name, location)", "Location's world == null.");
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
    public void deleteWarp(@NotNull String name) {
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
     * Method to clear all Strikes.
     */
    @Override
    public void clearStrikes() {
        // This is the only one that requires this :/
        faction.clearStrikes(Bukkit.getConsoleSender());
    }

    /**
     * Remove strike from a Faction.
     *
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    @Override
    public void removeStrike(String sender, String reason) {
        int removeMe = -1;
        int bound = faction.getStrikes().size();
        for (int i = 0; i < bound; i++) {
            if (faction.getStrikes().get(i).equalsIgnoreCase(reason)) {
                removeMe = i;
                break;
            }
        }
        if (removeMe == -1) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "removeStrike(Sender, String)", "Strike not found.");
        }
        faction.removeStrike(Bukkit.getConsoleSender(), removeMe);
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
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    @Override
    public Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        if (getId().equals(faction.getId())) return Relationship.MEMBER;
        Relation relationTo = this.faction.getRelationTo((Faction) faction.getFaction());
        switch (relationTo) {
            case ALLY:
                return Relationship.ALLY;
            case ENEMY:
                return Relationship.ENEMY;
            case TRUCE:
                return Relationship.TRUCE;
            default:
                return Relationship.NONE;
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
        return "FactionsX";
    }

}
