package cc.javajobs.factionsbridge.bridge.events.infrastructure;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * FPlayerEvent is the baseline of Events which occur to both a Faction and a FPlayer.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:38
 */
public class FPlayerEvent extends FactionEvent {

    private final FPlayer fplayer;

    /**
     * IFactionPlayerEvent is an extension of an IFactionEvent.
     * <p>
     *     This type of Event also requires IFactionPlayer variables to be stored.
     * </p>
     * @param faction related to the event.
     * @param fplayer related to the event.
     * @param other event object.
     */
    public FPlayerEvent(Faction faction, FPlayer fplayer, Event other) {
        super(faction, other);
        this.fplayer = fplayer;
    }

    public FPlayer getFPlayer() {
        return fplayer;
    }

}
