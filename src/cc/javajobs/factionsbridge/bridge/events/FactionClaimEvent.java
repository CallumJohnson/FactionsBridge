package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * The FactionClaimEvent is an event which is called when a Claim is claimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:40
 */
public class FactionClaimEvent extends FPlayerEvent {

    private final Claim claim;

    /**
     * Constructor to initialise an FactionClaimEvent.
     *
     * @param claim which was claimed.
     * @param faction who claimed it.
     * @param fplayer who requested the claiming.
     * @param other event object.
     */
    public FactionClaimEvent(Claim claim, Faction faction, FPlayer fplayer, Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    public Claim getClaim() {
        return claim;
    }

}
