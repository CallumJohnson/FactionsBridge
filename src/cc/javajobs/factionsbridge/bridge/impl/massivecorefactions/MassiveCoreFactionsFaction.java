package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IRelationship;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MassiveCoreFactions implementation of IFaction.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 15:21
 */
public class MassiveCoreFactionsFaction implements IFaction {

    private final Faction f;

    public MassiveCoreFactionsFaction(Faction faction) {
        this.f = faction;
    }

    /**
     * Method to get the Id of the Faction.
     *
     * @return Id in the form of String.
     */
    @Override
    public String getId() {
        return f.getId();
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @Override
    public String getName() {
        return f.getName();
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public IFactionPlayer getLeader() {
        return new MassiveCoreFactionsPlayer(f.getLeader());
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
        return BoardColl.get().getChunks(f).stream().map(MassiveCoreFactionsClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return f.getMPlayers().stream().map(MassiveCoreFactionsPlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to get the relationship between two Factions.
     *
     * @param other faction to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFaction other) {
        Rel rel =  f.getRelationTo((Faction) other.asObject());
        switch (rel) {
            case ENEMY:
                return IRelationship.ENEMY;
            case NEUTRAL:
                return IRelationship.NONE;
            case TRUCE:
                return IRelationship.TRUCE;
            case ALLY:
                return IRelationship.ALLY;
            default:
                return IRelationship.MEMBER;
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
        return f;
    }

}
