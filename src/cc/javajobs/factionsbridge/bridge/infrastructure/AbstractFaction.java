package cc.javajobs.factionsbridge.bridge.infrastructure;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.jetbrains.annotations.NotNull;

/**
 * The AbstractFaction class stands for one implementation of a Faction.
 *
 * @param <F> to bridge using the FactionsBridge methodology.
 * @author Callum Johnson
 * @since 04/06/2021 - 21:17
 */
public abstract class AbstractFaction<F> implements Faction, ErrorParticipator {

    /**
     * Faction object bridged using FactionsBridge.
     */
    protected final F faction;

    /**
     * Bridge object for quick-use within a 'Faction' implementation.
     */
    protected final FactionsBridge bridge;

    /**
     * Constructor to create an AbstractFaction.
     * <p>
     *     This class will be used to create each implementation of a 'Faction'.
     * </p>
     *
     * @param faction object which will be bridged using the FactionsBridge.
     */
    public AbstractFaction(@NotNull F faction) {
        this.faction = faction;
        this.bridge = FactionsBridge.get();
    }

    /**
     * Method to obtain the linked Faction object.
     *
     * @return {@link F} object.
     */
    @NotNull
    public F getFaction() {
        return faction;
    }

    /**
     * Method to obtain the String representation of the {@link AbstractFaction}.
     *
     * @return String representation of the {@link AbstractFaction}.
     */
    @Override
    @NotNull
    public String toString() {
        return "AbstractFaction={factionObject:" + faction + "}";
    }

}
