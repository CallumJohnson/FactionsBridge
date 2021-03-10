package cc.javajobs.factionsbridge.bridge.impl.atlasfactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.elapsed.objects.Warp;
import org.bukkit.Location;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AtlasFactions implementation of IFaction.
 *
 * @author Callum Johnson
 * @since 27/02/2021 - 09:07
 */
public class AtlasFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to initialise an AtlasFactionsFaction.
     * <p>
     *     This builds upon the FactionsUUIDFaction class as this is what the API does.
     * </p>
     * @param faction to wrap.
     */
    public AtlasFactionsFaction(Faction faction) {
        super(faction);
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public IFactionPlayer getLeader() {
        try {
            Class<?> factionClass = f.getClass();
            Method getLeader = factionClass.getMethod("getFPlayerLeader");
            FPlayer leader = (FPlayer) getLeader.invoke(f);
            return new AtlasFactionsPlayer(leader);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getLeader()");
        }
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link IClaim}
     */
    @Override
    public List<IClaim> getAllClaims() {
        return f.getAllClaims().stream().map(AtlasFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return f.getFPlayers().stream().map(AtlasFactionsPlayer::new).collect(Collectors.toList());
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
        return super.isServerFaction();
    }

    /**
     * Method to get the bank balance of the Faction.
     *
     * @return in the form of Double.
     */
    @Override
    public double getBank() {
        try {
            Class<?> factionClass = f.getClass();
            Method getBalance = factionClass.getMethod("getBalance");
            return (double) getBalance.invoke(f);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getBalance()");
        }
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
        try {
            Class<?> factionClass = f.getClass();
            Method getPoints = factionClass.getMethod("getPoints");
            return (int) getPoints.invoke(f);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getPoints()");
        }
    }

    /**
     * Method to get the Location of a Faction Warp by Name.
     *
     * @param name of the warp
     * @return {@link Location} of the warp.
     */
    @Override
    public Location getWarp(String name) {
        try {
            Class<?> factionClass = f.getClass();
            Method getWarp = factionClass.getMethod("getWarp", String.class);
            Object result = getWarp.invoke(f, name);
            if (result instanceof Warp) {
                return ((Warp) result).getLocation();
            }
            throw new BridgeMethodException(getClass(), "getWarp(String name){2}");
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getWarp(String name){1}");
        }
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
    @Override
    public HashMap<String, Location> getWarps() {
        HashMap<String, Location> data = new HashMap<>();
        try {
            Class<?> factionClass = f.getClass();
            Method getWarp = factionClass.getMethod("getWarps");
            Object warpMap = getWarp.invoke(f);
            if (warpMap instanceof HashMap) {
                // We know that if the Server is using Atlas, the Map is of Type <String,Warp>
                HashMap<String, Warp> warps = (HashMap<String, Warp>) warpMap;
                warps.forEach((k, v) -> data.put(k, v.getLocation()));
                return data;
            }
            throw new BridgeMethodException(getClass(), "getWarps(){2}");
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getWarps(){1}");
        }
    }

    /**
     * Method to create a warp for the Faction.
     *
     * @param name     of the warp.
     * @param location of the warp.
     */
    @Override
    public void createWarp(String name, Location location) {
        try {
            Warp warp = new Warp();
            warp.setLocation(location);
            warp.setName(name);
            Class<?> factionClass = f.getClass();
            Method setWarp = factionClass.getMethod("setWarp", String.class, Warp.class);
            setWarp.invoke(f, name, warp);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "createWarp(String name, Location location)");
        }
    }

    /**
     * Method to manually remove a Warp using its name.
     *
     * @param name of the warp to be deleted.
     */
    @Override
    public void deleteWarp(String name) {
        f.removeWarp(name);
    }

}
