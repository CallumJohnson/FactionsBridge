package cc.javajobs.factionsbridge.bridge.impl.supremefactions;

import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.zcore.supreme.Strike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The SupremeFactions implementation is an extension of FactionsUUID.
 * Object Target: {@link Faction}.
 */
public class SupremeFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to create a SupremeFactionsFaction.
     * <p>
     * This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public SupremeFactionsFaction(@NotNull Faction faction) {
        super(faction);
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
        return new SupremeFactionsFPlayer(faction.getFPlayerAdmin());
    }

    /**
     * Method to get all of the Claims linked to the Faction.
     *
     * @return {@link List} of {@link Claim} related to the Faction.
     */
    @NotNull
    @Override
    public List<Claim> getAllClaims() {
        return faction.getAllClaims().stream().map(SupremeFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members of a Faction.
     *
     * @return {@link List} of {@link FPlayer} related to the Faction.
     */
    @NotNull
    @Override
    public List<FPlayer> getMembers() {
        return faction.getFPlayers().stream().map(SupremeFactionsFPlayer::new).collect(Collectors.toList());
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
            Method getFBalance = factionClass.getMethod("getFBalance");
            return (double) getFBalance.invoke(faction);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return 0.0;
            return (double) methodError(getClass(), "getFBalance()", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to clear the Strikes related to the Faction
     */
    @Override
    public void clearStrikes() {
        try {
            Method get = faction.getClass().getMethod("getStrikes");
            Object strikesObj = get.invoke(faction);
            if (List.class.isAssignableFrom(strikesObj.getClass())) {
                List<?> strikeData = (List<?>) strikesObj;
                Method remove = faction.getClass().getMethod("removeStrike", Strike.class);
                for (Object strikeDatum : strikeData) {
                    if (strikeDatum instanceof Strike) {
                        Strike strike = (Strike) strikeDatum;
                        remove.invoke(faction, strike);
                    }
                }
            } else {
                if (bridge.catch_exceptions) return;
                methodError(getClass(), "clearStrikes(){2}", "Strikes not in List.");
            }
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "clearStrikes(){1}", ex.getClass().getSimpleName());
        }
    }

    /**
     * Method to add a Strike to the Faction.
     *
     * @param sender who added the Strike
     * @param reason for the Strike
     */
    @Override
    public void addStrike(String sender, String reason) {
        try {
            Method add = faction.getClass().getMethod("addStrike", Strike.class);
            Strike strike = new Strike(reason, sender, System.currentTimeMillis());
            add.invoke(faction, strike);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "addStrike(Sender, String)", ex.getClass().getSimpleName());
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
            Strike removeMe = null;
            Method get = faction.getClass().getMethod("getStrikes");
            Object strikesObj = get.invoke(faction);
            if (List.class.isAssignableFrom(strikesObj.getClass())) {
                List<?> strikeData = (List<?>) strikesObj;
                removeMe = strikeData.stream()
                        .filter(strikeObj -> strikeObj instanceof Strike)
                        .map(strikeObj -> (Strike) strikeObj)
                        .filter(c -> c.getStriker().equalsIgnoreCase(sender))
                        .filter(c -> c.getReason().equalsIgnoreCase(reason))
                        .findFirst().orElse(null);
            }
            if (removeMe == null) {
                if (bridge.catch_exceptions) return;
                methodError(getClass(), "removeStrike(Sender, String){2}", "RemoveMe == null");
            }
            Method remove = faction.getClass().getMethod("removeStrike", Strike.class);
            remove.invoke(faction, removeMe);
        } catch (Exception ex) {
            if (bridge.catch_exceptions) return;
            methodError(getClass(), "removeStrike(Sender, String){1}", ex.getClass().getSimpleName());
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
            Object strikesObj = get.invoke(faction);
            if (List.class.isAssignableFrom(strikesObj.getClass())) {
                return ((List<?>) strikesObj).size();
            }
        } catch (Exception ignored) {}
        if (bridge.catch_exceptions) return 0;
        else return (int) methodError(getClass(), "getTotalStrikes()", "Strikes not in list.");
    }

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    @Override
    public String getProvider() {
        return "SupremeFactions";
    }

}
