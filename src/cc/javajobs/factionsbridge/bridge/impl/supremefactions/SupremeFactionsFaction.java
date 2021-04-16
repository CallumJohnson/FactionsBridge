package cc.javajobs.factionsbridge.bridge.impl.supremefactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.zcore.supreme.Strike;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Callum Johnson
 * @since 27/02/2021 - 17:30
 */
public class SupremeFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to initialise an SupremeFactionsFaction.
     * <p>
     *     This builds upon the FactionsUUIDFaction class as this is what the API does.
     * </p>
     * @param faction to wrap.
     */
    public SupremeFactionsFaction(Faction faction) {
        super(faction);
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public IFactionPlayer getLeader() {
        return new SupremeFactionsPlayer((FPlayer) super.getLeader().asObject());
    }

    /**
     * Method to get all Claims related to the Faction.
     *
     * @return Claims in the form List of {@link IClaim}
     */
    @Override
    public List<IClaim> getAllClaims() {
        return f.getAllClaims().stream().map(SupremeFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return f.getFPlayers().stream().map(SupremeFactionsPlayer::new).collect(Collectors.toList());
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
            Method getFBalance = factionClass.getMethod("getFBalance");
            return (double) getFBalance.invoke(f);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getFBalance()");
        }
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
        throw new BridgeMethodUnsupportedException("SupremeFactions doesn't support getPoints().");
    }

    /**
     * Add strikes to a Faction.
     *
     * @param sender who desires to Strike the Faction.
     * @param reason for the Strike.
     */
    @Override
    public void addStrike(String sender, String reason) {
        try {
            Method add = f.getClass().getMethod("addStrike", Strike.class);
            Strike strike = new Strike(reason, sender, System.currentTimeMillis());
            add.invoke(f, strike);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "addStrike(Sender, String)");
        }
    }

    /**
     * Remove strike from a Faction.
     *
     * @param sender who desires to remove the Strike from the Faction.
     * @param reason of the original Strike.
     */
    @Override
    public void removeStrike(String sender, String reason) {
        try {
            Strike removeMe = null;
            Method get = f.getClass().getMethod("getStrikes");
            Object strikesObj = get.invoke(f);
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
                throw new BridgeMethodException(getClass(), "removeStrike(Sender, String){2}");
            }
            Method remove = f.getClass().getMethod("removeStrike", Strike.class);
            remove.invoke(f, removeMe);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "removeStrike(Sender, String){1}");
        }
    }

    /**
     * Method to obtain the Total Strikes a Faction has.
     *
     * @return integer amount of Strikes.
     */
    @Override
    public int getTotalStrikes() {
        try {
            Method get = f.getClass().getMethod("getStrikes");
            Object strikesObj = get.invoke(f);
            if (List.class.isAssignableFrom(strikesObj.getClass())) {
                return ((List<?>) strikesObj).size();
            }
            throw new BridgeMethodException(getClass(), "getTotalStrikes(){2}");
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getTotalStrikes(){1}");
        }
    }

    /**
     * Method to clear all Strikes.
     */
    @Override
    public void clearStrikes() {
        try {
            Method get = f.getClass().getMethod("getStrikes");
            Object strikesObj = get.invoke(f);
            if (List.class.isAssignableFrom(strikesObj.getClass())) {
                List<?> strikeData = (List<?>) strikesObj;
                Method remove = f.getClass().getMethod("removeStrike", Strike.class);
                for (Object strikeDatum : strikeData) {
                    if (strikeDatum instanceof Strike) {
                        Strike c = (Strike) strikeDatum;
                        remove.invoke(f, c);
                    }
                }
            } else {
                throw new BridgeMethodException(getClass(), "clearStrikes(){2}");
            }
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "clearStrikes(){1}");
        }
    }

}
