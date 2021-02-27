package cc.javajobs.factionsbridge.bridge.impl.atlasfactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AtlasFactions implementation of IFaction.
 *
 * @author Callum Johnson
 * @version 1.0
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
            Method getLeader = factionClass.getDeclaredMethod("getFPlayerLeader");
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
            Method getBalance = factionClass.getDeclaredMethod("getBalance");
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
            Method getPoints = factionClass.getDeclaredMethod("getPoints");
            return (int) getPoints.invoke(f);
        } catch (Exception ex) {
            throw new BridgeMethodException(getClass(), "getPoints()");
        }
    }

}
