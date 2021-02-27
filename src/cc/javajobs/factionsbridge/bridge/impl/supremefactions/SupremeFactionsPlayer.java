package cc.javajobs.factionsbridge.bridge.impl.supremefactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDPlayer;
import com.massivecraft.factions.FPlayer;

/**
 * SupremeFactions implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 17:29
 */
public class SupremeFactionsPlayer extends FactionsUUIDPlayer {

    /**
     * Constructor to initialise an SupremeFactionsPlayer..
     * <p>
     *     This builds upon the FactionsUUIDPlayer class as this is what the API does.
     * </p>
     * @param fpl to wrap.
     */
    public SupremeFactionsPlayer(FPlayer fpl) {
        super(fpl);
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public IFaction getFaction() {
        return super.getFaction(); // FIXME: 27/02/2021 
    }
    
}
