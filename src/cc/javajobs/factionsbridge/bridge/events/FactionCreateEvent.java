package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * FactionCreateEvent is the Event which is called when an FPlayer creates a new Faction.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:29
 */
public class FactionCreateEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Event event;
    private final FPlayer sender;
    private final Faction faction;

    /**
     * Constructor to initialise an FactionCreateEvent using the Faction and FPlayer objects.
     *
     * @param fplayer who sent the create request.
     * @param faction which has been created.
     * @param other event object.
     */
    public FactionCreateEvent(Faction faction, FPlayer fplayer, Event other) {
        this.faction = faction;
        this.sender = fplayer;
        this.event = other;
    }

    public FPlayer getFPlayer() {
        return sender;
    }

    public Player getPlayer() {
        return sender.getPlayer();
    }

    public OfflinePlayer getOfflinePlayer() {
        return sender.getOfflinePlayer();
    }

    public String getTag() {
        return faction.getName();
    }

    public String getId() {
        return faction.getId();
    }

    public Faction getFaction() {
        return faction;
    }

    @NotNull
    public HandlerList getHandlers() {
        return FactionCreateEvent.handlers;
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
