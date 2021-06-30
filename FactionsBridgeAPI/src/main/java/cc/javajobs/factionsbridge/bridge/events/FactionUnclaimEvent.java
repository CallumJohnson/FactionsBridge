package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Claim;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionUnclaimEvent is the Event which is called when an Claim is Unclaimed.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:42
 */
public class FactionUnclaimEvent extends FPlayerEvent {

    /**
     * Claim related to the event.
     */
    private final Claim claim;

    /**
     * Constructor to initialise an FactionUnclaimEvent.
     * <p>
     *     Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     *
     * @param claim which was unclaimed.
     * @param faction who unclaimed it.
     * @param fplayer who requested the IClaim to be unclaimed.
     * @param other event object.
     */
    public FactionUnclaimEvent(@NotNull Claim claim, @NotNull Faction faction,
                               @NotNull FPlayer fplayer, @Nullable Event other) {
        super(faction, fplayer, other);
        this.claim = claim;
    }

    /**
     * Obtain the claim related to the event.
     *
     * @return {@link #claim}.
     */
    @NotNull
    public Claim getClaim() {
        return claim;
    }

}
