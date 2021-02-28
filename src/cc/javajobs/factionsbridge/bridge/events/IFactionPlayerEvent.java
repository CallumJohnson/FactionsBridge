package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * IFactionPlayerEvent is the baseline of Events which occur to both a Faction and a FactionPlayer.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:38
 */
public class IFactionPlayerEvent extends IFactionEvent {

    private final IFactionPlayer fplayer;

    /**
     * IFactionPlayerEvent is an extension of an IFactionEvent.
     * <p>
     *     This type of Event also requires IFactionPlayer variables to be stored.
     * </p>
     * @param faction related to the event.
     * @param fplayer related to the event.
     * @param other event object.
     */
    public IFactionPlayerEvent(IFaction faction, IFactionPlayer fplayer, Event other) {
        super(faction, other);
        this.fplayer = fplayer;
    }

    public IFactionPlayer getFPlayer() {
        return fplayer;
    }

}
