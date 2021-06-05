package cc.javajobs.factionsbridge.bridge.impl.medievalfactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import dansplugins.factionsystem.ChunkManager;
import dansplugins.factionsystem.data.PersistentData;
import dansplugins.factionsystem.objects.ClaimedChunk;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

/**
 * @author Callum Johnson
 * @since 03/05/2021 - 09:23
 */
public class MedievalFactionsClaim extends AbstractClaim<ClaimedChunk> {

    /**
     * Constructor to create an MedievalFactionsClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public MedievalFactionsClaim(@NotNull ClaimedChunk claim) {
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
        return getChunk().getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return getChunk().getZ();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public Faction getFaction() {
        return new MedievalFactionsFaction(PersistentData.getInstance().getFaction(claim.getHolder()));
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
        return getFaction() != null &&
                ChunkManager.getInstance().isClaimed(
                        getChunk(),
                        PersistentData.getInstance().getClaimedChunks()
                );
    }

}
