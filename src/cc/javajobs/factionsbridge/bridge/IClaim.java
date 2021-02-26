package cc.javajobs.factionsbridge.bridge;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * IClaim stands for a Singular Claim for a Faction. Usually a 16x16 Chunk.
 * <p>
 *     This class bridges the gap between APIs and their own implementation
 *     of Claims/Chunks.
 * </p>
 * @author Callum Johnson
 * @version 1.0
 * @since 25/02/2021 - 19:28
 */
public interface IClaim {

    /**
     * Method to get the Chunk linked to the Claim.
     * @return {@link Chunk} from Bukkit.
     */
    Chunk getBukkitChunk();

    /**
     * Method to get the X of the Chunk.
     * @return x coordinate.
     */
    int getX();

    /**
     * Method to get the Z of the Chunk.
     * @return z coordinate.
     */
    int getZ();

    /**
     * Method to get the World of the Chunk.
     * @return {@link World} from the Bukkit API.
     */
    World getWorld();

    /**
     * Method to get the Faction linked to the Chunk.
     * @return IFaction linked to the IClaim.
     */
    IFaction getFaction();

    /**
     * Method to get the name of the World linked to the Chunk.
     * @return string name of the World.
     */
    String getWorldName();

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     * @return UUID (UniqueId).
     */
    UUID getWorldUID();

}
