package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * IFactionCreateEvent is the Event which is called when a Player creates a new IFaction.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:29
 */
public class IFactionCreateEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Event event;
    private final String tag;
    private final Player sender;

    /**
     * Constructor to initialise an IFactionCreateEvent using the name of the new IFaction.
     * @param tag of the Faction.
     * @param sender who sent the create request.
     * @param other event object.
     */
    public IFactionCreateEvent(String tag, Player sender, Event other) {
        this.tag = tag;
        this.sender = sender;
        this.event = other;
    }


    /**
     * Constructor to initialise an IFactionCreateEvent using the name of the new IFaction.
     * @param tag of the Faction.
     * @param sender who sent the create request.
     * @param other event object.
     */
    public IFactionCreateEvent(String tag, IFactionPlayer sender, Event other) {
        this.tag = tag;
        this.sender = sender.getPlayer();
        this.event = other;
    }

    /**
     * Constructor to initialise an IFactionCreateEvent using the IFaction and IFactionPlayer objects.
     * @param fplayer who sent the create request.
     * @param faction which has been created.
     * @param other event object.
     */
    public IFactionCreateEvent(IFactionPlayer fplayer, IFaction faction, Event other) {
        this(fplayer.getPlayer(), faction, other);
    }

    /**
     * Constructor to initialise an IFactionCreateEvent using an OfflinePlayer and the IFaction.
     * @param player who sent the request.
     * @param faction which has been created.
     * @param other event object.
     */
    public IFactionCreateEvent(Player player, IFaction faction, Event other) {
        this.tag = faction.getName();
        this.sender = player;
        this.event = other;
    }

    public IFactionPlayer getFPlayer() {
        return FactionsBridge.getFactionsAPI().getFactionPlayer(sender);
    }

    public Player getPlayer() {
        return sender;
    }

    public String getTag() {
        return tag;
    }

    @NotNull
    public HandlerList getHandlers() {
        return IFactionCreateEvent.handlers;
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
