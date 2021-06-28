package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionRenameEvent is the event which is called when an Faction is renamed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 18:01
 */
public class FactionRenameEvent extends FactionEvent {

    /**
     * New/changed name.
     */
    private final String name;

    /**
     * Constructor to initialise the FactionRenameEvent
     * <p>
     *     Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     *
     * @param faction related to the event.
     * @param name of the faction.
     * @param other   event object.
     */
    public FactionRenameEvent(@NotNull Faction faction, @NotNull String name, @Nullable Event other) {
        super(faction, other);
        this.name = name;
    }

    /**
     * Method to obtain the new/changed name.
     *
     * @return new/changed name.
     */
    @NotNull
    public String getName() {
        return name;
    }

}
