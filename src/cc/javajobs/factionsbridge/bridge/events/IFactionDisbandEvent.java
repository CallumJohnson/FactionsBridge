package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * IFactionDisbandEvent is called when an IFaction is disbanded.
 *
 * @author Callum Johnson
 * @version 1.0
 * @since 28/02/2021 - 08:33
 */
public class IFactionDisbandEvent extends IFactionEvent {

    private final DisbandReason reason;
    private final Player player;

    /**
     * Constructor to initialise an IFactionDisbandEvent.
     * @param fpl who disbanded the IFaction.
     * @param faction which was disbanded.
     * @param reason for the disbandment.
     * @param other event object.
     */
    public IFactionDisbandEvent(IFactionPlayer fpl, IFaction faction, DisbandReason reason, Event other) {
        this(fpl.getPlayer(), faction, reason, other);
    }

    /**
     * Constructor to initialise an IFactionDisbandEvent
     * @param player who disbanded the IFaction.
     * @param faction which was disbanded.
     * @param reason for the disbandment.
     * @param other event object.
     */
    public IFactionDisbandEvent(Player player, IFaction faction, DisbandReason reason, Event other) {
        super(faction, other);
        this.reason = reason;
        this.player = player;
    }

    /**
     * @see DisbandReason
     * @return reason for disbandment.
     */
    public DisbandReason getReason() {
        return reason;
    }

    public Player getPlayer() {
        return player;
    }

    public IFactionPlayer getFPlayer() {
        return FactionsBridge.getFactionsAPI().getFactionPlayer(player);
    }

    /**
     * This enumeration is a bridge for disbandment reasons.
     * <p>
     *     The current reasons are:
     *      - Command (Savage/Saber/Atlas).
     *      - Plugin (Savage/Saber/Atlas).
     *      - Inactivity (Savage/Saber/Atlas).
     *      - Leave (Savage/Saber/Atlas).
     * </p>
     * @author Callum Johnson
     * @version 1.0
     * @since 28/02/2021 - 08:34
     */
    public enum DisbandReason {

        COMMAND, PLUGIN, INACTIVITY, LEAVE, UNKNOWN;

        /**
         * Method to return a DisbandReason from a String.
         * <p>
         *     This method will return {@link DisbandReason#UNKNOWN} if
         *     the method cannot find a suitable match.
         * </p>
         * @param key to get a DisbandReason for.
         * @return a DisbandReason entry.
         */
        public static DisbandReason fromString(String key) {
            for (DisbandReason value : DisbandReason.values()) {
                if (value.name().equalsIgnoreCase(key)) {
                    return value;
                }
            }
            return DisbandReason.UNKNOWN;
        }

    }


}
