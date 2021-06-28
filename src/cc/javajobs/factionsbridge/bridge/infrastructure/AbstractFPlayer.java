package cc.javajobs.factionsbridge.bridge.infrastructure;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * The AbstractFPlayer class stands for one implementation of an FPlayer.
 *
 * @param <FP> to bridge using the FactionsBridge methodology.
 * @author Callum Johnson
 * @since 04/06/2021 - 21:49
 */
public abstract class AbstractFPlayer<FP> implements FPlayer, ErrorParticipator {

    /**
     * FPlayer object bridged using FactionsBridge.
     */
    protected final FP fPlayer;

    /**
     * Bridge object for quick-use within an 'FPlayer' implementation.
     */
    protected final FactionsBridge bridge;

    /**
     * Constructor to create an AbstractFPlayer.
     * <p>
     *     This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public AbstractFPlayer(@NotNull FP fPlayer) {
        this.fPlayer = fPlayer;
        this.bridge = FactionsBridge.get();
    }

    /**
     * Method to obtain the linked FPlayer object.
     *
     * @return {@link FP} object.
     */
    @NotNull
    public FP getFPlayer() {
        return fPlayer;
    }

    /**
     * Method to obtain the String representation of the {@link AbstractFPlayer}.
     *
     * @return String representation of the {@link AbstractFPlayer}.
     */
    @Override
    @NotNull
    public String toString() {
        return "AbstractFPlayer={fplayerObject:" + fPlayer + "}";
    }

}
