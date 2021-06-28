package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionCreateEvent is the Event which is called when an FPlayer creates a new Faction.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:29
 */
public class FactionCreateEvent extends Event implements Cancellable {

    /**
     * Handler list, required by Bukkit.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Event which was called, triggering this one.
     */
    private final Event event;

    /**
     * FPlayer who asked for the Faction to be created.
     */
    private final FPlayer sender;

    /**
     * The faction which was created.
     */
    private final Faction faction;

    /**
     * Constructor to initialise an FactionCreateEvent using the Faction and FPlayer objects.
     * <p>
     *     Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     *
     * @param fplayer who sent the create request.
     * @param faction which has been created.
     * @param other event object.
     */
    public FactionCreateEvent(@NotNull Faction faction, @NotNull FPlayer fplayer, @Nullable Event other) {
        this.faction = faction;
        this.sender = fplayer;
        this.event = other;
    }

    /**
     * Method to obtain the FPlayer who sent the create request.
     *
     * @return FPlayer (creator).
     */
    public FPlayer getFPlayer() {
        return sender;
    }

    /**
     * Method to obtain the Player who sent the create request.
     *
     * @return {@link Player}.
     * @see FPlayer#getPlayer()
     */
    public Player getPlayer() {
        return sender.getPlayer();
    }

    /**
     * Method to obtain the OfflinePlayer who sent the create request.
     *
     * @return {@link OfflinePlayer}.
     * @see FPlayer#getOfflinePlayer()
     */
    public OfflinePlayer getOfflinePlayer() {
        return sender.getOfflinePlayer();
    }

    /**
     * Method to obtain the Factions' tag / name.
     *
     * @return String name of the Faction created.
     */
    public String getTag() {
        return faction.getName();
    }

    /**
     * Method to obtain the Factions' id.
     *
     * @return String id representation of the Faction.
     */
    public String getId() {
        return faction.getId();
    }

    /**
     * Method to obtain the Faction related to the create event.
     *
     * @return {@link Faction}
     */
    @NotNull
    public Faction getFaction() {
        return faction;
    }

    /**
     * Method to obtain the Handler list.
     *
     * @return {@link #handlers}.
     */
    @NotNull
    public HandlerList getHandlers() {
        return FactionCreateEvent.handlers;
    }

    /**
     * Static method to obtain the Handler list.
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
