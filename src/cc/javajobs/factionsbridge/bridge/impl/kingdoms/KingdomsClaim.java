package cc.javajobs.factionsbridge.bridge.impl.kingdoms;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.constants.land.Land;

/**
 * Kingdoms implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim}.
 * Object Target: {@link Land}.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:02
 */
public class KingdomsClaim extends AbstractClaim<Land> {

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public KingdomsClaim(@NotNull Land claim) {
        super(claim);
    }

    /**
     * Method to obtain the Chunk related to the Claim.
     *
     * @return {@link Chunk} represented by the 'Claim'.
     */
    @NotNull
    @Override
    public Chunk getChunk() {
        return claim.getLocation().toChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return claim.getLocation().getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return claim.getLocation().getZ();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public Faction getFaction() {
        return new KingdomsKingdom(claim.getKingdom());
    }

    /**
     * Method to determine if the Claim has a Faction related to it.
     * <p>
     * If the claim is owned by 'Wilderness' or the equivalent Faction, this method will return {@code false}.
     * </p>
     *
     * @return {@code true} if a Faction owns this land (not Wilderness)
     * @see Faction#isWilderness()
     */
    @Override
    public boolean isClaimed() {
        return getFaction() != null;
    }

}
