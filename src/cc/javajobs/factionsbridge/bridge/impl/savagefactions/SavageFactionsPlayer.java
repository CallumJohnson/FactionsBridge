package cc.javajobs.factionsbridge.bridge.impl.savagefactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

/**
 * SavageFactions implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @since 27/02/2021 - 10:32
 */
public class SavageFactionsPlayer extends FactionsUUIDPlayer {

    /**
     * Constructor to initialise an SavageFactionsPlayer.
     * <p>
     *     This builds upon the FactionsUUIDPlayer class as this is what the API does.
     * </p>
     * @param fpl to wrap.
     */
    public SavageFactionsPlayer(FPlayer fpl) {
        super(fpl);
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public IFaction getFaction() {
        return new SavageFactionsFaction((Faction) super.getFaction().asObject());
    }
}
