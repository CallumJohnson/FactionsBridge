package factionsuuid;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionsUUID implementation of {@link cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim}.
 * Object Target: {@link FLocation}.
 */
public class FactionsUUIDClaim extends AbstractClaim<FLocation> {

    /**
     * Constructor to create a FactionsUUIDClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public FactionsUUIDClaim(@NotNull FLocation claim) {
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
     * Method to obtain the 'x' coordinate of the Claim.
     *
     * @return integer position on the 'x' axis.
     */
    @Override
    public int getX() {
        return (int) claim.getX();
    }

    /**
     * Method to obtain the 'z' coordinate of the Claim.
     *
     * @return integer position on the 'z' axis.
     */
    @Override
    public int getZ() {
        return (int) claim.getZ();
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
        return new FactionsUUIDFaction(Board.getInstance().getFactionAt(claim));
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
        Faction claimedBy = getFaction();
        return claimedBy != null && !claimedBy.isWilderness();
    }

}
