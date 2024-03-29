package improvedfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Relationship;
import extension.homeextension.Main;
import io.github.toberocat.improvedfactions.ImprovedFactionsMain;
import io.github.toberocat.improvedfactions.factions.Faction;
import io.github.toberocat.improvedfactions.factions.FactionMember;
import io.github.toberocat.improvedfactions.factions.economy.Bank;
import io.github.toberocat.improvedfactions.factions.relation.RelationManager;
import io.github.toberocat.improvedfactions.utility.ChunkUtils;
import io.github.toberocat.improvedfactions.utility.Vector2;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FactionsX implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction}.
 * Object Target: {@link Faction}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:38
 */
public class ImprovedFactionsFaction extends AbstractFaction<Faction> {

    /**
     * Constructor to create an FactionsXFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public ImprovedFactionsFaction(@NotNull Faction faction) {
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
        return faction.getRegistryName();
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @NotNull
    @Override
    public String getName() {
        return faction.getDisplayName();
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public FPlayer getLeader() {
        return new ImprovedFactionsPlayer(Bukkit.getOfflinePlayer(faction.getOwner()));
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link Claim}
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        final List<Claim> claims = new ArrayList<>();
        final List<Vector2> claimedChunks = ChunkUtils.claimedChunks;
        for (Vector2 claimedChunk : claimedChunks) {
            for (World world : Bukkit.getWorlds()) {
                final Chunk chunk = world.getChunkAt((int) claimedChunk.getX(), (int) claimedChunk.getY());
                final Faction faction = ChunkUtils.GetFactionClaimedChunk(chunk);
                if (faction == this.faction) {
                    claims.add(new ImprovedFactionsClaim(chunk));
                }
            }
        }
        return claims;
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return Arrays.stream(faction.getMembers())
                .map(FactionMember::getUuid)
                .map(Bukkit::getOfflinePlayer)
                .map(ImprovedFactionsPlayer::new)
                .collect(Collectors.toList());
    }

    /**
     * Method to set the 'Home' of a Faction.
     *
     * @param location to set as the new home.
     */
    @Override
    public void setHome(@NotNull Location location) {
        if (location.getWorld() == null) return;
        if (ImprovedFactionsMain.extensions.containsKey("HomeExtension"))
            Main.homes.put(faction.getRegistryName(), location);
        else {
            if (bridge.catch_exceptions) return;
            unsupported(getProvider(), "setHome(location)");
        }
    }

    /**
     * Method to retrieve the 'Home' of the Faction.
     *
     * @return {@link Bukkit}, {@link Location}.
     */
    @Override
    public Location getHome() {
        if (ImprovedFactionsMain.extensions.containsKey("HomeExtension")) {
            return Main.homes.getOrDefault(faction.getRegistryName(), null);
        } else {
            if (bridge.catch_exceptions) return null;
            return (Location) unsupported(getProvider(), "setHome(location)");
        }
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
        return isSafeZone();
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
        return getId().equals("safezone");
    }

    /**
     * Method to determine if the IFaction is the Wilderness.
     *
     * @return {@code true} if it is.
     */
    @Override
    public boolean isWilderness() {
        return false; // This isn't an error, anything null means Wilderness.
    }

    /**
     * Method to determine if the Faction is in a Peaceful State.
     *
     * @return {@code true} if yes, {@code false} if no.
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
        return faction.getPowerManager().getPower();
    }

    /**
     * Method to set the power of a Faction.
     *
     * @param power to set.
     */
    @Override
    public void setPower(double power) {
        faction.getPowerManager().setPower((int) power);
    }

    /**
     * Method to get the bank balance of the Faction.
     *
     * @return in the form of Double.
     */
    @Override
    public double getBank() {
        final Bank bank = faction.getBank();
        if (bank == null) {
            if (bridge.catch_exceptions) return 0.0d;
            else return (double) methodError(getClass(), "getBank()", "VaultAPI isn't installed.");
        } else {
            try {
                final Class<? extends Bank> clazz = bank.getClass();
                final Method balance = clazz.getMethod("balance");
                final Object economicResponse = balance.invoke(bank);
                if (economicResponse == null) {
                    if (bridge.catch_exceptions) return 0.0d;
                    else return (double) methodError(getClass(), "getBank()", "EconomyResponse is null.");
                }
                final Class<?> responseClass = economicResponse.getClass();
                final Field balanceField = responseClass.getField("balance");
                return balanceField.getDouble(economicResponse);
            } catch (ReflectiveOperationException ex) {
                return (double) methodError(getClass(), "getBank()", ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        }
    }

    /**
     * Method to set the balance of the Faction.
     *
     * @param balance to set.
     */
    @Override
    public void setBank(double balance) {
        final Bank bank = faction.getBank();
        if (bank == null) {
            if (!bridge.catch_exceptions) {
                methodError(getClass(), "setBank(balance)", "VaultAPI isn't installed.");
            }
        } else {
            try {
                final Class<? extends Bank> clazz = bank.getClass();
                final double bal = getBank();
                final Method withdraw = clazz.getMethod("withdraw", int.class);
                Object invoke = withdraw.invoke(bank, ((int) bal));
                final Class<?> economicResponse = invoke.getClass();
                final Method transactionSuccess = economicResponse.getMethod("transactionSuccess");
                boolean result = (boolean) transactionSuccess.invoke(invoke);
                if (!result) methodError(getClass(), "setBank(balance)", "Cannot set balance.");
                else {
                    final Method deposit = clazz.getMethod("deposit", int.class);
                    invoke = deposit.invoke(bal, ((int) balance));
                    result = (boolean) transactionSuccess.invoke(invoke);
                    if (!result) {
                        methodError(getClass(), "setBank(balance)", "Failed to set balance.");
                    }
                }
            } catch (ReflectiveOperationException ex) {
               methodError(getClass(), "setBank(balance)", ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        }
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
    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public HashMap<String, Location> getWarps() {
        if (bridge.catch_exceptions) return new HashMap<>();
        return (HashMap<String, Location>) unsupported(getProvider(), "getWarps()");
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
        unsupported(getProvider(), "addStrike(sender, reason)");
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
     * Remove strike from a Faction.
     *
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    @Override
    public void removeStrike(String sender, String reason) {
        if (bridge.catch_exceptions) return;
        unsupported(getProvider(), "removeStrike(sender, reason)");
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
     * Method to obtain the Relationship between this Faction and another Faction.
     *
     * @param faction to get the relative relationship to this Faction.
     * @return {@link Relationship} enumeration.
     */
    @NotNull
    @Override
    public Relationship getRelationshipTo(@NotNull AbstractFaction<?> faction) {
        if (getId().equals(faction.getId())) return Relationship.MEMBER;
        final RelationManager relationManager = this.faction.getRelationManager();
        for (String ally : relationManager.getAllies()) {
            if (ally.equals(faction.getName())) {
                return Relationship.ALLY;
            }
        }
        for (String enemy : relationManager.getEnemies()) {
            if (enemy.equals(faction.getName())) {
                return Relationship.ENEMY;
            }
        }
        return Relationship.DEFAULT_RELATIONSHIP;
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    @Override
    public String getProvider() {
        return "ImprovedFactions";
    }

}
