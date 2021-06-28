package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * The FactionClaimEvent is an event which is called when a Claim is claimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:40
 */
public class FactionClaimEvent extends FPlayerEvent {

    /**
     * The claim related to the event.
     */
    private final Claim claim;

    /**
     * Constructor to initialise an FactionClaimEvent.
     *
     * @param claim which was claimed.
     * @param faction who claimed it.
     * @param fplayer who requested the claiming.
     * @param other event object.
     */
    public FactionClaimEvent(@NotNull Claim claim, @NotNull Faction faction,
                             @NotNull FPlayer fplayer, @NotNull Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    /**
     * Method to obtain the claim related to the event.
     *
     * @return {@link Claim}.
     */
    @NotNull
    public Claim getClaim() {
        return claim;
    }

}
