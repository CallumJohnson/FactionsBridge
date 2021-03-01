package cc.javajobs.factionsbridge.bridge.impl.saberfactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.impl.atlasfactions.AtlasFactionsPlayer;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFaction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SaberFactions implementation of IFaction.
 *
 * @author Callum Johnson
 * @since 27/02/2021 - 10:48
 */
public class SaberFactionsFaction extends FactionsUUIDFaction {

    /**
     * Constructor to initialise SaberFactionsFaction.
     * <p>
     *     As SaberFactions was built upon FactionsUUID,
     *     this class extends FactionUUID equivalents.
     * </p>
     * @param faction to wrap.
     */
    public SaberFactionsFaction(Faction faction) {
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
        return f.getAllClaims().stream().map(SaberFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return f.getFPlayers().stream().map(SaberFactionsPlayer::new).collect(Collectors.toList());
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

}