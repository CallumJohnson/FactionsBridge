package cc.javajobs.factionsbridge.bridge.impl.factionsx;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import net.prosavage.factionsx.persist.data.FLocation;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

/**
 * FactionsX implementation of IClaim.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 16:23
 */
public class FactionsXClaim extends AbstractClaim<FLocation> {

    /**
     * Constructor to create an FactionsXClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public FactionsXClaim(@NotNull FLocation claim) {
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
        return claim.getChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return (int) claim.getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return (int) claim.getZ();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public Faction getFaction() {
        return new FactionsXFaction(claim.getFaction());
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
