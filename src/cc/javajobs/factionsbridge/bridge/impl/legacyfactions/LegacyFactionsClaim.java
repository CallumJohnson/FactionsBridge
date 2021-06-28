package cc.javajobs.factionsbridge.bridge.impl.legacyfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

/**
 * LegacyFactions implementation of {@link Claim}.
 * Object Target: {@link FLocation}.
 *
 * @author Callum Johnson
 * @since 04/05/2021 - 09:47
 */
public class LegacyFactionsClaim extends AbstractClaim<FLocation> {

    /**
     * Constructor to create an LegacyFactionsClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public LegacyFactionsClaim(@NotNull FLocation claim) {
        super(claim);
    }

    /**
     * Constructor to create an LegacyFactionsClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param locality to be converted to an FLocation.
     */
    public LegacyFactionsClaim(Locality locality) {
        this(new FLocation(locality.getLocation()));
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
        return new LegacyFactionsFaction(Board.get().getFactionAt(Locality.of(claim.getChunk())));
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
