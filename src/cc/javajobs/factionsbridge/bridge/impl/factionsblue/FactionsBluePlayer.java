package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import cc.javajobs.factionsbridge.bridge.IRelationship;
import me.zysea.factions.api.FactionsApi;
import me.zysea.factions.faction.FPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * FactionsBlue Implementation of the IFactionPlayer.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 14:21
 */
public class FactionsBluePlayer implements IFactionPlayer {

    private final FPlayer fpl;

    public FactionsBluePlayer(FPlayer fpl) {
        this.fpl = fpl;
    }

    /**
     * Method to get the unique Id of the Faction Player.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getUniqueId() {
        return fpl.getId();
    }

    /**
     * Method to get the Name of the Faction Player.
     *
     * @return name of the Player.
     */
    @Override
    public String getName() {
        return fpl.getName();
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public IFaction getFaction() {
        if (!fpl.hasFaction()) {
            // -2 is Wilderness/Factionless
            return new FactionsBlueFaction(FactionsApi.getFaction(-2));
        }
        return new FactionsBlueFaction(fpl.getFaction());
    }

    /**
     * Method to get the Offline form of the Player.
     *
     * @return {@link OfflinePlayer}
     */
    @Override
    public OfflinePlayer getOfflinePlayer() {
        return fpl.getOfflinePlayer();
    }

    /**
     * Method to get the Online form of the Player.
     *
     * @return {@link Player}
     * @see IFactionPlayer#isOnline()
     */
    @Override
    public Player getPlayer() {
        return fpl.getOfflinePlayer().getPlayer();
    }

    /**
     * Uses Bukkit methodology to determine if the Player is online.
     *
     * @return {@code true} = yes, {@code false} = no.
     */
    @Override
    public boolean isOnline() {
        return fpl.getOfflinePlayer().isOnline();
    }

    /**
     * Method to get the relationship from a Player to a Faction.
     *
     * @param other faction to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFaction other) {
        return getFaction().getRelationTo(other);
    }

    /**
     * Method to get the relationship from a Player to another Player.
     *
     * @param other IFactionPlayer to test
     * @return {@link IRelationship}
     */
    @Override
    public IRelationship getRelationTo(IFactionPlayer other) {
        return getFaction().getRelationTo(other.getFaction());
    }

    /**
     * Method to return the IFactionPlayer as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return fpl;
    }

}
