package cc.javajobs.factionsbridge.bridge.impl.saberfactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDPlayer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

/**
 * SaberFactions implementation of IFactionPlayer.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 10:46
 */
public class SaberFactionsPlayer extends FactionsUUIDPlayer {

    /**
     * Constructor to initialise SaberFactionsPlayer.
     * <p>
     *     As SaberFactions was built upon FactionsUUID,
     *     this class extends FactionUUID equivalents.
     * </p>
     * @param fpl to wrap.
     */
    public SaberFactionsPlayer(FPlayer fpl) {
        super(fpl);
    }

    /**
     * Method to get the Faction linked to the IFactionPlayer.
     *
     * @return faction of the player.
     */
    @Override
    public IFaction getFaction() {
        return new SaberFactionsFaction((Faction) super.getFaction().asObject());
    }

}
