package cc.javajobs.factionsbridge.bridge.impl.savagefactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.impl.atlasfactions.AtlasFactionsPlayer;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.zcore.persist.MemoryFaction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SavageFactions implementation of IFaction.
 *
 * @author Callum Johnson
 * @since 27/02/2021 - 10:34
 */
public class SavageFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to initialise an SavageFactionsFaction.
     * <p>
     *     This builds upon the FactionsUUIDFaction class as this is what the API does.
     * </p>
     * @param faction to wrap.
     */
    public SavageFactionsFaction(Faction faction) {
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
        return f.getAllClaims().stream().map(SavageFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return f.getFPlayers().stream().map(SavageFactionsPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to get the points of a Faction.
     *
     * @return in the form of Integer.
     */
    @Override
    public int getPoints() {
        try {
            Class<?> factionClass = ((MemoryFaction) f).getClass();
            Method getPoints = factionClass.getMethod("getPoints");
            return (int) getPoints.invoke(f);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BridgeMethodException(getClass(), "getPoints()");
        }
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
            Method set = f.getClass().getMethod("setStrikes", Integer.TYPE, Boolean.TYPE);
            set.invoke(f, getTotalStrikes()+1, false);
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
            Method set = f.getClass().getMethod("setStrikes", Integer.TYPE, Boolean.TYPE);
            int current = getTotalStrikes();
            set.invoke(f, current <= 0 ? 0 : current-1, false);
        } catch (Exception ex) {
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
        try {
            Method get = f.getClass().getMethod("getStrikes");
            return (int) get.invoke(f);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getStrikes()");
        }
    }

    /**
     * Method to clear all Strikes.
     */
    @Override
    public void clearStrikes() {
        try {
            Method set = f.getClass().getMethod("setStrikes", Integer.TYPE, Boolean.TYPE);
            set.invoke(f, 0, false);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "clearStrikes()");
        }
    }

}
