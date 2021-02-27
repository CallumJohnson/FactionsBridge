package cc.javajobs.factionsbridge.bridge.impl.factionsuuid;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * FactionsUUID implementation of IClaim.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 26/02/2021 - 14:44
 */
public class FactionsUUIDClaim implements IClaim {

    protected final FLocation floc;

    public FactionsUUIDClaim(FLocation fLocation) {
        this.floc = fLocation;
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
        return floc.getWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new FactionsUUIDFaction(Board.getInstance().getFactionAt(floc));
    }

    /**
     * Method to get the name of the World linked to the Chunk.
     *
     * @return string name of the World.
     */
    @Override
    public String getWorldName() {
        return floc.getWorldName();
    }

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getWorldUID() {
        return floc.getWorld().getUID();
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
