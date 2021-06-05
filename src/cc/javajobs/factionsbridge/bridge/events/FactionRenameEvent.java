package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * FactionRenameEvent is the event which is called when an Faction is renamed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 18:01
 */
public class FactionRenameEvent extends FactionEvent {

    private final String name;

    /**
     * Constructor to initialise the FactionRenameEvent
     *
     * @param faction related to the event.
     * @param name of the faction.
     * @param other   event object.
     */
    public FactionRenameEvent(Faction faction, String name, Event other) {
        super(faction, other);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
