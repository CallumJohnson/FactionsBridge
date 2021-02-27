package cc.javajobs.factionsbridge.bridge.impl.saberfactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDClaim;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

/**
 * SaberFactions implementation of IClaim.
 * 
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 10:45
 */
public class SaberFactionsClaim extends FactionsUUIDClaim {

    /**
     * Constructor to initialise a SaberFactionsClaim.
     * <p>
     *     As SaberFactions was built upon FactionsUUID,
     *     this class extends FactionUUID equivalents.
     * </p>
     * @param fLocation to wrap.
     */
    public SaberFactionsClaim(FLocation fLocation) {
        super(fLocation);
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new SaberFactionsFaction((Faction) super.getFaction().asObject());
    }

}
