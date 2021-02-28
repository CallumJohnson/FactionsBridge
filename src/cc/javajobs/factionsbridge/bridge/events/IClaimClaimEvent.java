package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IClaim;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * The IClaimClaimEvent is an event which is called when an IClaim is claimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:40
 */
public class IClaimClaimEvent extends IFactionPlayerEvent {

    private final IClaim claim;

    /**
     * Constructor to initialise an IClaimClaimEvent.
     * @param claim which was claimed.
     * @param faction who claimed it.
     * @param fplayer who requested the claiming.
     * @param other event object.
     */
    public IClaimClaimEvent(IClaim claim, IFaction faction, IFactionPlayer fplayer, Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    public IClaim getClaim() {
        return claim;
    }

}
