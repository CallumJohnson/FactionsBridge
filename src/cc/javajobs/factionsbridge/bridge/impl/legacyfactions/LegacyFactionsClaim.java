package cc.javajobs.factionsbridge.bridge.impl.legacyfactions;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import net.redstoneore.legacyfactions.FLocation;
import net.redstoneore.legacyfactions.entity.Board;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * @author Callum Johnson
 * @since 04/05/2021 - 09:47
 */
public class LegacyFactionsClaim implements IClaim {

    private final FLocation flocation;

    public LegacyFactionsClaim(FLocation fLocation) {
        this.flocation = fLocation;
    }

    public LegacyFactionsClaim(Locality locality) {
        this.flocation = new FLocation(locality.getLocation());
    }

    /**
     * Method to get the Chunk linked to the Claim.
     *
     * @return {@link Chunk} from Bukkit.
     */
    @Override
    public Chunk getBukkitChunk() {
        return flocation.getChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return (int) flocation.getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return (int) flocation.getZ();
    }

    /**
     * Method to get the World of the Chunk.
     *
     * @return {@link World} from the Bukkit API.
     */
    @Override
    public World getWorld() {
        return flocation.getWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new LegacyFactionsFaction(Board.get().getFactionAt(Locality.of(flocation.getChunk())));
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
        return flocation;
    }

}
