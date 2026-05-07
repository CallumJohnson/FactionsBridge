package lands;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import me.angeschossen.lands.api.land.ChunkCoordinate;
import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Lands implementation of {@link Claim}.
 * Object Target: {@link ChunkCoordinate}.
 */
public class LandsClaim extends AbstractClaim<ChunkCoordinate> {

    private final @NotNull LandWorld world;

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public LandsClaim(@NotNull ChunkCoordinate claim, @NotNull LandWorld world) {
        super(claim);
        this.world = world;
    }

    /**
     * Method to obtain the Chunk related to the Claim.
     *
     * @return {@link Chunk} represented by the 'Claim'.
     */
    @NotNull
    @Override
    public Chunk getChunk() {
        return world.getWorld().getChunkAt(claim.getX(), claim.getZ());
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
        final Chunk chunk = getChunk();
        final Land land;
        if (chunk.isLoaded()) {
            land = world.getLandByChunk(chunk.getX(), chunk.getZ());
        } else {
            land = world.getLandByUnloadedChunk(chunk.getX(), chunk.getZ());
        }
        if (land == null || land.getNation() == null) {
            return null;
        }
        return new LandsFaction(land);
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
        return getFaction() == null;
    }

}
