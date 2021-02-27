package cc.javajobs.factionsbridge.bridge.impl.atlasfactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

/**
 * AtlasFactions implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 09:06
 */
public class AtlasFactionsPlayer extends FactionsUUIDPlayer {

    /**
     * Constructor to initialise an AtlasFactionsPlayer.
     * <p>
     *     This builds upon the FactionsUUIDPlayer class as this is what the API does.
     * </p>
     * @param fpl to wrap.
     */
    public AtlasFactionsPlayer(FPlayer fpl) {
        super(fpl);
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public IFaction getFaction() {
        return new AtlasFactionsFaction((Faction) super.getFaction().asObject());
    }

}
