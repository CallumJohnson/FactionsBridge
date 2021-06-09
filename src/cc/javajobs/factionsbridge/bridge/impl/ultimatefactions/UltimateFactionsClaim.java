package cc.javajobs.factionsbridge.bridge.impl.ultimatefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import de.miinoo.factions.FactionsSystem;
import de.miinoo.factions.model.FactionChunk;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UltimateFactionsClaim extends AbstractClaim<FactionChunk> {

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public UltimateFactionsClaim(@NotNull FactionChunk claim) {
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
        return claim.getBukkitChunk();
    }

    /**
     * Method to obtain the 'x' coordinate of the Claim.
     *
     * @return integer position on the 'x' axis.
     */
    @Override
    public int getX() {
        return claim.getX();
    }

    /**
     * Method to obtain the 'z' coordinate of the Claim.
     *
     * @return integer position on the 'z' axis.
     */
    @Override
    public int getZ() {
        return claim.getZ();
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
        return new UltimateFactionsFaction(FactionsSystem.getFactions().getFaction(claim));
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
        return FactionsSystem.getFactions().isClaimedChunk(claim.getBukkitChunk());
    }

}
