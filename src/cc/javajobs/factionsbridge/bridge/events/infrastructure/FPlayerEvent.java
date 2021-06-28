package cc.javajobs.factionsbridge.bridge.events.infrastructure;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FPlayerEvent is the baseline of Events which occur to both a Faction and a FPlayer.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:38
 */
public class FPlayerEvent extends FactionEvent {

    /**
     * The FPlayer related to the event.
     */
    private final FPlayer fplayer;

    /**
     * FPlayerEvent is an extension of an IFactionEvent.
     * <p>
     *     This type of Event also requires IFactionPlayer variables to be stored.
     *     <br>Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     * @param faction related to the event.
     * @param fplayer related to the event.
     * @param other event object.
     */
    public FPlayerEvent(@NotNull Faction faction, @NotNull FPlayer fplayer, @Nullable Event other) {
        super(faction, other);
        this.fplayer = fplayer;
    }

    /**
     * Method to obtain the related FPlayer.
     *
     * @return {@link FPlayer}.
     */
    @NotNull
    public FPlayer getFPlayer() {
        return fplayer;
    }

}
