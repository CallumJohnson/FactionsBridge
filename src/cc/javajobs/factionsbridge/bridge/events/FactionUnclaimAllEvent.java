package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * FactionUnclaimAllEvent is the Event which is called when a FPlayer unclaims all of the Faction claims.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:41
 */
public class FactionUnclaimAllEvent extends FPlayerEvent {

    /**
     * Constructor to initialise an IClaimUnclaimAllEvent.
     * @param faction who now has no claims.
     * @param fplayer who requested for this change.
     * @param other event object.
     */
    public FactionUnclaimAllEvent(Faction faction, FPlayer fplayer, Event other) {
        super(faction, fplayer, other);
    }

}
