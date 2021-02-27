package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IRelationship;
import me.zysea.factions.FPlugin;
import me.zysea.factions.faction.Faction;
import me.zysea.factions.faction.role.Role;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FactionsBlue Implementation of the IFaction.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 14:05
 */
public class FactionsBlueFaction implements IFaction {

    private final Faction faction;

    public FactionsBlueFaction(Faction faction) {
        this.faction = faction;
    }

    /**
     * Method to get the Id of the Faction.
     *
     * @return Id in the form of String.
     */
    @Override
    public String getId() {
        return faction.getId().toString();
    }

    /**
     * Method to get the Name of the Faction.
     *
     * @return name of the Faction.
     */
    @Override
    public String getName() {
        return faction.getName();
    }

    /**
     * Method to get the IFactionPlayer Leader.
     *
     * @return the person who created the Faction.
     */
    @Override
    public IFactionPlayer getLeader() {
        for (OfflinePlayer allMember : faction.getMembers().getAllMembers()) {
            Role role = faction.getRoles().getMemberRole(allMember.getUniqueId());
            if (role.getId() == 4) {
                return new FactionsBluePlayer(FPlugin.getInstance().getFPlayers().getFPlayer(allMember));
            }
        }
        return null;
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
        return faction.getAllClaims().stream().map(FactionsBlueClaim::new).collect(Collectors.toList());
    }

    /**
     * Method to get all of the Members for the Faction.
     *
     * @return List of IFactionPlayer
     */
    @Override
    public List<IFactionPlayer> getMembers() {
        return faction.getAllMembers()
                .stream().map(allMember -> FPlugin.getInstance().getFPlayers().getFPlayer(allMember))
                .map(FactionsBluePlayer::new).collect(Collectors.toList());
    }

    /**
     * Method to get the relationship between two Factions.
     *
     * @param other faction to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFaction other) {
        if (faction.isAlliedTo((Faction) other.asObject())) {
            return IRelationship.ALLY;
        } else if (getId().equals(other.getId())) {
            return IRelationship.MEMBER;
        } else {
            return IRelationship.ENEMY;
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
        if (other.getFaction() == null) return IRelationship.ENEMY;
        return getRelationTo(other.getFaction());
    }

    /**
     * Method to return the IFaction as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return faction;
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
        return getId().equals("-2") || getId().equals("-1") || getId().equals("0");
    }

}
