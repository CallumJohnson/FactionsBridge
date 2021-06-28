package cc.javajobs.factionsbridge.bridge.impl.saberfactions;

import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The SaberFactions implementation is an extension of FactionsUUID.
 * Object Target: {@link FLocation}.
 */
public class SaberFactionsClaim extends FactionsUUIDClaim {

    /**
     * Constructor to create a SaberFactionsClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public SaberFactionsClaim(@NotNull FLocation claim) {
        super(claim);
    }

    /**
     * Method to obtain the Faction related to the Claim.
     * <p>
     * If there is no Faction, this method will return {@code null}.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     */
    @Nullable
    @Override
    public Faction getFaction() {
        return new SaberFactionsFaction(Board.getInstance().getFactionAt(claim));
    }

}
