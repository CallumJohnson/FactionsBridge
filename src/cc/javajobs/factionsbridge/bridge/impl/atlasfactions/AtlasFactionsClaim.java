package cc.javajobs.factionsbridge.bridge.impl.atlasfactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDClaim;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

/**
 * AtlasFactions implementation of IClaim.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 08:35
 */
public class AtlasFactionsClaim extends FactionsUUIDClaim {

    /**
     * Constructor to initialise an AtlasFactionsClaim.
     * <p>
     *     AtlasFactions was built upon FactionsUUID, features in AtlasFactions
     *     are therefore underpinned by FactionsUUID.
     * </p>
     * @param fLocation to use the claim within.
     */
    public AtlasFactionsClaim(FLocation fLocation) {
        super(fLocation);
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new AtlasFactionsFaction((Faction) super.getFaction().asObject());
    }

}
