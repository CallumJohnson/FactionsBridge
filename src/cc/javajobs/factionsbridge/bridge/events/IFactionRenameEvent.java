package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IFaction;
import org.bukkit.event.Event;

/**
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 18:01
 */
public class IFactionRenameEvent extends IFactionEvent {

    private final String name;

    /**
     * Constructor to initialise the IFactionRenameEvent
     *
     * @param faction related to the event.
     * @param name of the faction.
     * @param other   event object.
     */
    public IFactionRenameEvent(IFaction faction, String name, Event other) {
        super(faction, other);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
