package cc.javajobs.factionsbridge.bridge.events.infrastructure;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * IFactionEvent is the baseline for all Faction-Related Events.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:24
 */
public class FactionEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Faction faction;
    private final Event event;

    /**
     * IFactionEvent is an event of the type relating to an IFaction.
     * <p>
     *     This is the base for many events Bridged with FactionsBridge.
     * </p>
     * @param faction related to the event.
     * @param other event object.
     */
    public FactionEvent(Faction faction, Event other) {
        this.faction = faction;
        this.event = other;
    }

    public Faction getFaction() {
        return faction;
    }

    @NotNull
    public HandlerList getHandlers() {
        return FactionEvent.handlers;
    }

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
