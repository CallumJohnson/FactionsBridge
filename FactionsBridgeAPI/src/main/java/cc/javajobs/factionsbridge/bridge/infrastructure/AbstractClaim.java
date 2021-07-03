package cc.javajobs.factionsbridge.bridge.infrastructure;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * The AbstractClaim class stands for one implementation of a Claim.
 *
 * @param <C> to bridge using the FactionsBridge methodology.
 * @author Callum Johnson
 * @since 05/06/2021 - 09:25
 */
public abstract class AbstractClaim<C> implements Claim, ErrorParticipator {

    /**
     * Claim object bridged using FactionsBridge.
     */
    protected final C claim;

    /**
     * Bridge object for quick-use within a 'Claim' implementation.
     */
    protected final FactionsBridge bridge;

    /**
     * Constructor to create an AbstractClaim.
     * <p>
     *     This class will be used to create each implementation of a 'Claim'.
     * </p>
     *
     * @param claim object which will be bridged using the FactionsBridge.
     */
    public AbstractClaim(@NotNull C claim) {
        this.claim = claim;
        this.bridge = FactionsBridge.get();
    }

    /**
     * Method to obtain the linked Claim object.
     *
     * @return {@link C} object.
     */
    @NotNull
    public C getClaim() {
        return claim;
    }

    /**
     * Method to obtain the String representation of the {@link AbstractClaim}.
     *
     * @return String representation of the {@link AbstractClaim}.
     */
    @Override
    @NotNull
    public String toString() {
        return "AbstractClaim={claimObject:" + claim + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(claim, getWorldName(), getX(), getZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractClaim)) return false;
        final AbstractClaim<?> claim = (AbstractClaim<?>) obj;
        if (getX() == claim.getX() && getZ() == claim.getX() && getWorldUID().equals(claim.getWorldUID())) return true;
        else return hashCode() == claim.hashCode();
    }

}
