package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The FactionJoinEvent is an event which is called when an FPlayer joins an Faction.
 *
 * @author Callum Johnson
 * @since 04/03/2021 - 19:01
 */
public class FactionJoinEvent extends FPlayerEvent {

    /**
     * Constructor to initialise an FactionJoinEvent.
     * <p>
     *     Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     *
     * @param faction which has been joined.
     * @param fplayer who joined.
     * @param other   event.
     */
    public FactionJoinEvent(@NotNull Faction faction, @NotNull FPlayer fplayer, @Nullable Event other) {
        super(faction, fplayer, other);
    }

}
