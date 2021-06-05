package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.FactionsBridge;
import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * FactionDisbandEvent is called when an Faction is disbanded.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:33
 */
public class FactionDisbandEvent extends FactionEvent {

    private final DisbandReason reason;
    private final Player player;

    /**
     * Constructor to initialise an FactionDisbandEvent.
     *
     * @param fpl who disbanded the Faction.
     * @param faction which was disbanded.
     * @param reason for the disbandment.
     * @param other event object.
     */
    public FactionDisbandEvent(FPlayer fpl, Faction faction, DisbandReason reason, Event other) {
        this(fpl.getPlayer(), faction, reason, other);
    }

    /**
     * Constructor to initialise an FactionDisbandEvent
     *
     * @param player who disbanded the Faction.
     * @param faction which was disbanded.
     * @param reason for the disbandment.
     * @param other event object.
     */
    public FactionDisbandEvent(Player player, Faction faction, DisbandReason reason, Event other) {
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

    public FPlayer getFPlayer() {
        return FactionsBridge.getFactionsAPI().getFPlayer(player);
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
     * @since 28/02/2021 - 08:34
     */
    public enum DisbandReason {

        COMMAND("DISBAND_COMMAND"), PLUGIN, INACTIVITY, LEAVE, UNKNOWN;

        private final String other;

        DisbandReason() {
            this.other = null;
        }

        DisbandReason(String other_key) {
            this.other = other_key;
        }

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
                if (value.other != null) {
                    if (value.other.equalsIgnoreCase(key)) {
                        return value;
                    }
                }
            }
            return DisbandReason.UNKNOWN;
        }

    }


}
