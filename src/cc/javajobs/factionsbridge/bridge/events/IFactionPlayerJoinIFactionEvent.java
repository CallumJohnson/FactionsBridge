package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * The IFactionPlayerJoinIFactionEvent is an event which is
 * called when an IFactionPlayer joins an IFaction.
 *
 * @author Callum Johnson
 * @since 04/03/2021 - 19:01
 */
public class IFactionPlayerJoinIFactionEvent extends IFactionPlayerEvent {

    /**
     * Constructor to initialise an IFactionPlayerJoinIFactionEvent.
     * @param faction which has been joined.
     * @param fplayer who joined.
     * @param other   event.
     */
    public IFactionPlayerJoinIFactionEvent(IFaction faction, IFactionPlayer fplayer, Event other) {
        super(faction, fplayer, other);
    }

}
