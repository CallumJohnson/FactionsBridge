package towny;

import cc.javajobs.factionsbridge.bridge.infrastructure.AbstractClaim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Towny implementation of {@link Claim}.
 * Object Target: {@link Resident}.
 *
 * @author Callum Johnson
 * @since 01/07/2021 - 11:03
 */
public class TownyClaim extends AbstractClaim<TownBlock> {

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     * This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public TownyClaim(@NotNull TownBlock claim) {
        super(claim);
    }

    /**
     * Method to obtain the Chunk related to the Claim.
     *
     * @return {@link Chunk} represented by the 'Claim'.
     */
    @Override
    public @NotNull Chunk getChunk() {
        return getLocation().getChunk();
    }

    /**
     * Method to obtain the TownyBlock as Location.
     *
     * @return {@link Location}.
     */
    private @NotNull Location getLocation() {
        final WorldCoord worldCoord = claim.getWorldCoord();
        final int y = worldCoord.getBukkitWorld().getHighestBlockYAt(worldCoord.getX(), worldCoord.getZ());
        return new Location(worldCoord.getBukkitWorld(), worldCoord.getX() + 0.5, y, worldCoord.getZ() + 0.5);
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
        return getClaim().getZ();
    }

    /**
     * Method to obtain the Faction related to the Claim.
     * <p>
     * If there is no Faction, this method will return {@code null}.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     */
    @Override
    public @Nullable Faction getFaction() {
        try {
            return new TownyFaction(claim.getTown());
        } catch (TownyException e) {
            if (bridge.catch_exceptions) return null;
            else return (Faction) methodError(getClass(), "getFaction()", "TownyException encountered");
        }
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
        return !claim.isForSale() && getFaction() != null;
    }

}
