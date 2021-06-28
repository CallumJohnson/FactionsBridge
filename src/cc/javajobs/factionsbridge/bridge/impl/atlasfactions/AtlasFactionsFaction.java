package cc.javajobs.factionsbridge.bridge.impl.atlasfactions;

import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.elapsed.objects.StrikeInfo;
import com.massivecraft.factions.elapsed.objects.Warp;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AtlasFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to create an AtlasFactionsFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public AtlasFactionsFaction(@NotNull Faction faction) {
        super(faction);
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getAllClaims().stream().map(AtlasFactionsClaim::new).collect(Collectors.toList());
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
            Class<?> factionClass = faction.getClass();
            Method getLeader = factionClass.getMethod("getFPlayerLeader");
            com.massivecraft.factions.FPlayer leader = (com.massivecraft.factions.FPlayer) getLeader.invoke(faction);
            if (leader == null) {
                return new AtlasFactionsFPlayer(faction.getFPlayerAdmin());
            }
            return new AtlasFactionsFPlayer(leader);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return null;
            return (FPlayer) methodError(getClass(), "getLeader()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getFPlayers().stream().map(AtlasFactionsFPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to get the points of the Faction.
     *
     * @return points of the Faction.
     */
    @Override
    public int getPoints() {
        try {
            Class<?> factionClass = faction.getClass();
            Method getPoints = factionClass.getMethod("getPoints");
            return (int) getPoints.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0;
            return (int) methodError(getClass(), "getPoints()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to override the points of the Faction to the specified amount.
     *
     * @param points to set for the Faction.
     * @see #getPoints()
     */
    @Override
    public void setPoints(int points) {
        try {
            int difference = points - getPoints();
            Class<?> factionClass = faction.getClass();
            Method addPoints = factionClass.getMethod("addPoints", Integer.TYPE);
            addPoints.invoke(faction, difference);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "setPoints(int points)", ex.getClass().getSimpleName());
        }
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
        try {
            Class<?> factionClass = faction.getClass();
            Method getBalance = factionClass.getMethod("getBalance");
            return (double) getBalance.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0.0;
            return (double) methodError(getClass(), "getBalance()", ex.getClass().getSimpleName());
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
        try {
            Class<?> factionClass = faction.getClass();
            Method getWarp = factionClass.getMethod("getWarp", String.class);
            Object result = getWarp.invoke(faction, name);
            if (result instanceof Warp) return ((Warp) result).getLocation();
            if (bridge.catch_exceptions) return null;
            return (Location) methodError(getClass(), "getWarp(String name) {2}", "Failed");
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return null;
            return (Location) methodError(getClass(), "getWarp(String name) {1}", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to create a Warp manually.
     *
     * @param name     of the Warp to create.
     * @param location of the warp.
     */
    @Override
    public void createWarp(@NotNull String name, @NotNull Location location) {
        try {
            Warp warp = new Warp();
            warp.setLocation(location);
            warp.setName(name);
            Class<?> factionClass = faction.getClass();
            Method setWarp = factionClass.getMethod("setWarp", String.class, Warp.class);
            setWarp.invoke(faction, name, warp);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "createWarp(String name, Location location)", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to get all of the Warps from the Faction.
     * <p>
     * The HashMap returned is of the form "String:Location".
     * </p>
     *
     * @return {@link HashMap} where each entry is a 'warp'.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @NotNull
    @Override
    public HashMap<String, Location> getWarps() {
        HashMap<String, Location> data = new HashMap<>();
        try {
            Class<?> factionClass = faction.getClass();
            Method getWarp = factionClass.getMethod("getWarps");
            Object warpMap = getWarp.invoke(factionClass);
            if (warpMap instanceof HashMap) {
                // We know that if the Server is using Atlas, the Map is <String,Warp>
                HashMap<String, Warp> warps = (HashMap<String, Warp>) warpMap;
                warps.forEach((k, v) -> data.put(k, v.getLocation()));
                return data;
            }
            if (bridge.catch_exceptions) return new HashMap<>();
            return (HashMap) methodError(getClass(), "getWarps(){2}", "Not WarpMap");
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return new HashMap<>();
            return (HashMap) methodError(getClass(), "getWarps(){1}", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to clear the Strikes related to the Faction
     */
    @Override
    public void clearStrikes() {
        try {
            faction.getClass().getMethod("clearStrikes").invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "clearStrikes()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to remove a Strike from the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void removeStrike(String sender, String reason) {
        try {
            StrikeInfo removeMe = null;
            Method get = faction.getClass().getMethod("getStrikes");
            Object strikeObj = get.invoke(faction);
            if (Set.class.isAssignableFrom(strikeObj.getClass())) {
                Set<?> strikesData = (Set<?>) strikeObj;
                removeMe = strikesData.stream()
                        .filter(strikesDatum -> strikesDatum instanceof StrikeInfo)
                        .map(strikesDatum -> (StrikeInfo) strikesDatum)
                        .filter(info -> info.getOfficer().equalsIgnoreCase(sender))
                        .filter(info -> info.getReason().equalsIgnoreCase(reason))
                        .findFirst().orElse(null);
            }
            if (removeMe == null) {
                if (bridge.catch_exceptions) return;
                methodError(getClass(), "removeStrike(Sender, String){2}", "RemoveMe == Null.");
            }

            Method remove = faction.getClass().getMethod("removeStrike", StrikeInfo.class);
            remove.invoke(faction, removeMe);

        } catch (Exception e) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "removeStrike(Sender, String){1}", e.getClass().getSimpleName());
        }
    }

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @SuppressWarnings({"JavaReflectionMemberAccess", "deprecation"}) // FactionsUUID takes precedence over Atlas,
    @Override                                       // so the member cannot be found.
    public void addStrike(String sender, String reason) {
        try {
            StrikeInfo info = new StrikeInfo();
            Field officer = info.getClass().getField("officer");
            Field faction = info.getClass().getField("faction");
            Field freason = info.getClass().getField("reason");
            Field time = info.getClass().getField("time");

            if (!officer.isAccessible()) officer.setAccessible(true);
            if (!faction.isAccessible()) faction.setAccessible(true);
            if (!freason.isAccessible()) freason.setAccessible(true);
            if (!time.isAccessible()) time.setAccessible(true);

            officer.set(info, sender);
            faction.set(info, getName());
            freason.set(info, reason);
            time.set(info, System.currentTimeMillis());

            Method add = faction.getClass().getMethod("addStrike", StrikeInfo.class);
            add.invoke(faction, info);

        } catch (Exception e) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "addStrike(Sender, String)", e.getClass().getSimpleName());
        }
    }

    /**
     * Method to obtain the total Strikes related to a Faction.
     *
     * @return total strikes.
     */
    @Override
    public int getTotalStrikes() {
        try {
            Method get = faction.getClass().getMethod("getStrikes");
            Object strikeObj = get.invoke(faction);
            if (Set.class.isAssignableFrom(strikeObj.getClass())) return ((Set<?>) strikeObj).size();
            if (bridge.catch_exceptions) return 0;
            return (int) methodError(getClass(), "getTotalStrikes(){2}", "Not StrikeSet");
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0;
            return (int) methodError(getClass(), "getTotalStrikes(){1}", ex.getClass().getSimpleName());
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
        return "AtlasFactions";
    }

}
