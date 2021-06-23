package cc.javajobs.factionsbridge.bridge.infrastructure;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import org.jetbrains.annotations.NotNull;

/**
 * The AbstractClaim class stands for one implementation of a Claim.
 *
 * @param <C> to bridge using the FactionsBridge methodology.
 * @author Callum Johnson
 * @since 05/06/2021 - 09:25
 */
public abstract class AbstractClaim<C> implements Claim {

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

    @Override
    @NotNull
    public String toString() {
        return "AbstractClaim={claimObject:" + claim + "}";
    }

}
