package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * IClaimUnclaimEvent is the Event which is called when an IClaim becomes Unclaimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:42
 */
public class IClaimUnclaimEvent extends IFactionPlayerEvent {

    private final IClaim claim;

    /**
     * Constructor to initialise an IClaimUnclaimEvent.
     * @param claim which was unclaimed.
     * @param faction who unclaimed it.
     * @param fplayer who requested the IClaim to be unclaimed.
     * @param other event object.
     */
    public IClaimUnclaimEvent(IClaim claim, IFaction faction, IFactionPlayer fplayer, Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    public IClaim getClaim() {
        return claim;
    }

}
