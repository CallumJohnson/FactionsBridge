package cc.javajobs.factionsbridge.bridge.impl.factionsx;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import net.prosavage.factionsx.persist.data.FLocation;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * FactionsX implementation of IClaim.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 16:23
 */
public class FactionsXClaim implements IClaim {

    private final FLocation floc;

    public FactionsXClaim(FLocation floc) {
        this.floc = floc;
    }

    /**
     * Method to get the Chunk linked to the Claim.
     *
     * @return {@link Chunk} from Bukkit.
     */
    @Override
    public Chunk getBukkitChunk() {
        return floc.getChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return (int) floc.getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return (int) floc.getZ();
    }

    /**
     * Method to get the World of the Chunk.
     *
     * @return {@link World} from the Bukkit API.
     */
    @Override
    public World getWorld() {
        return floc.getChunk().getWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new FactionsXFaction(floc.getFaction());
    }

    /**
     * Method to get the name of the World linked to the Chunk.
     *
     * @return string name of the World.
     */
    @Override
    public String getWorldName() {
        return floc.getWorld();
    }

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getWorldUID() {
        return floc.getChunk().getWorld().getUID();
    }

    /**
     * Method to return the IClaim as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return floc;
    }

}
