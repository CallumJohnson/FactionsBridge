package cc.javajobs.factionsbridge.bridge.impl.supremefactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDClaim;
import com.massivecraft.factions.FLocation;

/**
 * SupremeFactions implementation of IClaim.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 10:56
 */
public class SupremeFactionsClaim extends FactionsUUIDClaim {

    /**
     * Constructor to initialise a SupremeFactionsClaim.
     * <p>
     *     SupremeFactions was built upon FactionsUUID, features in
     *     SupremeFactions are therefore underpinned by FactionsUUID.
     * </p>
     * @param fLocation to wrap.
     */
    public SupremeFactionsClaim(FLocation fLocation) {
        super(fLocation);
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return super.getFaction(); // FIXME: 27/02/2021
    }

}
