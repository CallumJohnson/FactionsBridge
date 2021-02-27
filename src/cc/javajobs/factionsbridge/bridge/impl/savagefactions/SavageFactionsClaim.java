package cc.javajobs.factionsbridge.bridge.impl.savagefactions;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDClaim;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

/**
 * SavageFactions implementation of IClaim.
 * 
 * @author Callum Johnson
 * @version 1.0
 * @since 27/02/2021 - 10:30
 */
public class SavageFactionsClaim extends FactionsUUIDClaim {

    /**
     * Constructor to initialise an SavageFactionsClaim.
     * <p>
     *     SavageFactions was built upon FactionsUUID, features in SavageFactions
     *     are therefore underpinned by FactionsUUID.
     * </p>
     * @param fLocation to use the claim within.
     */
    public SavageFactionsClaim(FLocation fLocation) {
        super(fLocation);
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new SavageFactionsFaction((Faction) super.getFaction().asObject());
    }
}
