package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * IClaimUnclaimAllEvent is the Event which is called when a FactionPlayer unclaims all of the Faction IClaims.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 08:41
 */
public class IClaimUnclaimAllEvent extends IFactionPlayerEvent {

    /**
     * Constructor to initialise an IClaimUnclaimAllEvent.
     * @param faction who now has no claims.
     * @param fplayer who requested for this change.
     * @param other event object.
     */
    public IClaimUnclaimAllEvent(IFaction faction, IFactionPlayer fplayer, Event other) {
        super(faction, fplayer, other);
    }

}
