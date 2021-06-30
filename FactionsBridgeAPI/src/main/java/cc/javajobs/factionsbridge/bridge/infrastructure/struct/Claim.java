package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The Claim class stands for one Faction Claim, this class defines the API behaviour within the scope of FactionsBridge.
 * <p>
 *     This class will be implemented by the newest implementation of the Bridge to control what a Claim can do.
 * </p>
 *
 * @author Callum Johnson
 * @since 05/06/2021 - 09:11
 */
public interface Claim {

    /**
     * Method to obtain the Chunk related to the Claim.
     * <p>
     *     This method has been created to replicate methodology of the previous iteration of FactionsBridge.
     * </p>
     *
     * @return {@link Chunk} represented by the 'Claim'.
     */
    @Deprecated
    @NotNull
    default Chunk getBukkitChunk() {
        return getChunk();
    }

    /**
     * Method to obtain the Chunk related to the Claim.
     *
     * @return {@link Chunk} represented by the 'Claim'.
     */
    @NotNull
    Chunk getChunk();

    /**
     * Method to obtain the 'x' coordinate of the Claim.
     *
     * @return integer position on the 'x' axis.
     */
    int getX();

    /**
     * Method to obtain the 'z' coordinate of the Claim.
     *
     * @return integer position on the 'z' axis.
     */
    int getZ();

    /**
     * Method to obtain the World linked to the Chunk.
     *
     * @return {@link World} related to the {@link Chunk}.
     */
    @NotNull
    default World getWorld() {
        return getChunk().getWorld();
    }

    /**
     * Method to obtain the name of the World linked to the Chunk.
     *
     * @return String name of the {@link World}
     * @see #getWorld()
     */
    @NotNull
    default String getWorldName() {
        return getWorld().getName();
    }

    /**
     * Method to obtain the unique Id of the World linked to the Chunk.
     *
     * @return {@link UUID} Id of the {@link World}
     * @see #getWorld()
     */
    @NotNull
    default UUID getWorldUID() {
        return getWorld().getUID();
    }

    /**
     * Method to obtain the Faction related to the Claim.
     * <p>
     *     If there is no Faction, this method will return {@code null}.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     */
    @Nullable
    Faction getFaction();

    /**
     * Method to determine if the Claim has a Faction related to it.
     * <p>
     *     If the claim is owned by 'Wilderness' or the equivalent Faction, this method will return {@code false}.
     * </p>
     * @return {@code true} if a Faction owns this land (not Wilderness)
     * @see Faction#isWilderness()
     */
    boolean isClaimed();

    /**
     * Method to obtain the Provider name for Debugging/Console output purposes.
     *
     * @return String name of the Provider.
     */
    @NotNull
    default String getProvider() {
        return getClass().getSimpleName().replaceAll("[cC]laim", "");
    }

}
