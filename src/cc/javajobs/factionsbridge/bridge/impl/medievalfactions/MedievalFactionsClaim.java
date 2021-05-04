package cc.javajobs.factionsbridge.bridge.impl.medievalfactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import dansplugins.factionsystem.data.PersistentData;
import dansplugins.factionsystem.objects.ClaimedChunk;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * @author Callum Johnson
 * @since 03/05/2021 - 09:23
 */
public class MedievalFactionsClaim implements IClaim {

    private final ClaimedChunk claimedChunk;

    public MedievalFactionsClaim(ClaimedChunk claimedChunk) {
        this.claimedChunk = claimedChunk;
    }

    /**
     * Method to get the Chunk linked to the Claim.
     *
     * @return {@link Chunk} from Bukkit.
     */
    @Override
    public Chunk getBukkitChunk() {
        return claimedChunk.getChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return getBukkitChunk().getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return getBukkitChunk().getZ();
    }

    /**
     * Method to get the World of the Chunk.
     *
     * @return {@link World} from the Bukkit API.
     */
    @Override
    public World getWorld() {
        return getBukkitChunk().getWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new MedievalFactionsFaction(PersistentData.getInstance().getFaction(claimedChunk.getHolder()));
    }

    /**
     * Method to get the name of the World linked to the Chunk.
     *
     * @return string name of the World.
     */
    @Override
    public String getWorldName() {
        return getWorld().getName();
    }

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getWorldUID() {
        return getWorld().getUID();
    }

    /**
     * Method to return the IClaim as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return claimedChunk;
    }

}
