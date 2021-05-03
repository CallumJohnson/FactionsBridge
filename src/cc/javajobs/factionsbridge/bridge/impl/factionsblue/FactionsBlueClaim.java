package cc.javajobs.factionsbridge.bridge.impl.factionsblue;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import me.zysea.factions.FPlugin;
import me.zysea.factions.objects.Claim;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.UUID;

/**
 * FactionsBlue Implementation of the IClaim.
 *
 * @author Callum Johnson
 * @since 26/02/2021 - 14:01
 */
public class FactionsBlueClaim implements IClaim {

    private final Claim claim;

    public FactionsBlueClaim(Claim claim) {
        this.claim = claim;
    }

    /**
     * Method to get the Chunk linked to the Claim.
     *
     * @return {@link Chunk} from Bukkit.
     */
    @Override
    public Chunk getBukkitChunk() {
        return claim.asChunk();
    }

    /**
     * Method to get the X of the Chunk.
     *
     * @return x coordinate.
     */
    @Override
    public int getX() {
        return claim.getX();
    }

    /**
     * Method to get the Z of the Chunk.
     *
     * @return z coordinate.
     */
    @Override
    public int getZ() {
        return claim.getZ();
    }

    /**
     * Method to get the World of the Chunk.
     *
     * @return {@link World} from the Bukkit API.
     */
    @Override
    public World getWorld() {
        return claim.getWorld();
    }

    /**
     * Method to get the Faction linked to the Chunk.
     *
     * @return IFaction linked to the IClaim.
     */
    @Override
    public IFaction getFaction() {
        return new FactionsBlueFaction(FPlugin.getInstance().getClaims().getOwner(claim));
    }

    /**
     * Method to get the name of the World linked to the Chunk.
     *
     * @return string name of the World.
     */
    @Override
    public String getWorldName() {
        return claim.getWorld().getName();
    }

    /**
     * Method to get the unique Id of the World linked to the Chunk.
     *
     * @return UUID (UniqueId).
     */
    @Override
    public UUID getWorldUID() {
        return claim.getWorld().getUID();
    }

    /**
     * Method to return the IClaim as an Object (API friendly)
     *
     * @return object of API.
     */
    @Override
    public Object asObject() {
        return claim;
    }

}
