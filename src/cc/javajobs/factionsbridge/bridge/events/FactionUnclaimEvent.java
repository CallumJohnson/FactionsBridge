package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * FactionUnclaimEvent is the Event which is called when an Claim is Unclaimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:42
 */
public class FactionUnclaimEvent extends FPlayerEvent {

    private final Claim claim;

    /**
     * Constructor to initialise an FactionUnclaimEvent.
     *
     * @param claim which was unclaimed.
     * @param faction who unclaimed it.
     * @param fplayer who requested the IClaim to be unclaimed.
     * @param other event object.
     */
    public FactionUnclaimEvent(Claim claim, Faction faction, FPlayer fplayer, Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    public Claim getClaim() {
        return claim;
    }

}
