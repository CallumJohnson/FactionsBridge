package cc.javajobs.factionsbridge.bridge.impl.kingdoms;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.kingdoms.constants.land.Land;

import java.util.UUID;

/**
 * Kingdoms implementation of IClaim.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 17:02
 */
public class KingdomsClaim implements IClaim {

    private final Land land;

    public KingdomsClaim(Land land) {
        this.land = land;
    }

    /**
     * Method to get the Chunk linked to the Claim.
     *
     * @return {@link Chunk} from Bukkit.
     */
    @Override
    public Chunk getBukkitChunk() {
        return land.getLocation().toChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return land.getLocation().getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return land.getLocation().getZ();
    }

    /**
     * Method to get the World of the Chunk.
     *
     * @return {@link World} from the Bukkit API.
     */
    @Override
    public World getWorld() {
        return land.getLocation().getBukkitWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new KingdomsKingdom(land.getKingdom());
    }

    /**
     * Method to get the name of the World linked to the Chunk.
     *
     * @return string name of the World.
     */
    @Override
    public String getWorldName() {
        return land.getLocation().getWorld();
    }

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getWorldUID() {
        return land.getLocation().getBukkitWorld().getUID();
    }

    /**
     * Method to return the IClaim as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return land;
    }

}
