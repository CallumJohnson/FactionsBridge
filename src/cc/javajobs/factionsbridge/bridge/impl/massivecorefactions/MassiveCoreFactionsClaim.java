package cc.javajobs.factionsbridge.bridge.impl.massivecorefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

/**
 * MassiveCoreFactions implementation of IClaim.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 15:11
 */
public class MassiveCoreFactionsClaim extends AbstractClaim<PS> {

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public MassiveCoreFactionsClaim(@NotNull PS claim) {
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
        return claim.asBukkitChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return claim.getChunkX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return claim.getChunkZ();
    }


    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public Faction getFaction() {
        return new MassiveCoreFactionsFaction(BoardColl.get().getFactionAt(claim));
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
        return BoardColl.get().getFactionAt(claim) != null;
    }

}
