package cc.javajobs.factionsbridge.bridge.events.infrastructure;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionEvent is the baseline for all Faction-Related Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:24
 */
public class FactionEvent extends Event implements Cancellable {

    /**
     * Handler list, required by Bukkit.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Faction related to the Event.
     */
    private final Faction faction;

    /**
     * Event related to the Event.
     */
    private final Event event;

    /**
     * FactionEvent is an event of the type relating to an Faction.
     * <p>
     *     This is the base for many events Bridged with FactionsBridge.
     *     <br>Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     * @param faction related to the event.
     * @param other event object.
     */
    public FactionEvent(@NotNull Faction faction, @Nullable Event other) {
        this.faction = faction;
        this.event = other;
    }

    /**
     * Method to obtain the Faction related to the FactionEvent.
     *
     * @return {@link #faction}
     */
    @NotNull
    public Faction getFaction() {
        return faction;
    }

    /**
     * Method to obtain the Event which triggered this event.
     *
     * @return {@link #event}.
     */
    @Nullable
    public Event getEvent() {
        return event;
    }

    /**
     * Method to obtain the Handler list.
     *
     * @return {@link #handlers}.
     */
    @NotNull
    public HandlerList getHandlers() {
        return FactionEvent.handlers;
    }

    /**
     * Method to obtain the Handler list.
     *
     * @return {@link #handlers}.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Method to determine if the event is Cancelled.
     * <p>
     *     As this is a bridge, this bridges to the 'other' object and determines
     *     if that has been cancelled.
     * </p>
     * @return {@code true} cancelled, {@code false} not cancelled.
     */
    @Override
    public boolean isCancelled() {
        if (event == null) return false;
        if (!(event instanceof Cancellable)) return false;
        return ((Cancellable) event).isCancelled();
    }

    /**
     * Method to cancel the event.
     * <p>
     *     As this is a bridge, this bridges to the 'other' object and cancels
     *     that instead.
     * </p>
     * @param b status of the cancel call (true/false).
     */
    @Override
    public void setCancelled(boolean b) {
        if (event == null) return;
        if (!(event instanceof Cancellable)) return;
        ((Cancellable) event).setCancelled(b);
    }

}
