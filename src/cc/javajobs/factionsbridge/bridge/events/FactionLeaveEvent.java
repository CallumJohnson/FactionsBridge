package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;

/**
 * FactionLeaveEvent is called when an FPlayer leaves a Faction.
 *
 * @author Callum Johnson
 * @since 04/03/2021 - 19:21
 */
public class FactionLeaveEvent extends FPlayerEvent {

    private final LeaveReason reason;

    /**
     * Constructor to initialise an FactionLeaveEvent.
     *
     * @param faction which was left.
     * @param fplayer which left.
     * @param other   event object.
     */
    public FactionLeaveEvent(Faction faction, FPlayer fplayer, LeaveReason reason, Event other) {
        super(faction, fplayer, other);
        this.reason = reason;
    }

    /**
     * @see FactionLeaveEvent.LeaveReason
     * @return reason for leaving.
     */
    public LeaveReason getReason() {
        return reason;
    }

    /**
     * This enumeration is a bridge for leaving reasons.
     * @author Callum Johnson
     * @since 28/02/2021 - 08:34
     */
    public enum LeaveReason {

        LEAVE("LEFT"),
        KICK("KICKED"),
        UNKNOWN();

        private final String[] variants;

        LeaveReason(String... variants) {
            this.variants = variants;
        }

        public String[] getVariants() {
            return variants;
        }

        /**
         * Method to return a LeaveReason from a String.
         * <p>
         *     This method will return {@link FactionLeaveEvent.LeaveReason#UNKNOWN} if
         *     the method cannot find a suitable match.
         * </p>
         * @param key to get a LeaveReason for.
         * @return a LeaveReason entry.
         */
        public static LeaveReason fromString(String key) {
            for (LeaveReason value : LeaveReason.values()) {
                if (value.name().equalsIgnoreCase(key)) {
                    return value;
                } else {
                    for (String variant : value.getVariants()) {
                        if (variant.equalsIgnoreCase(key)) {
                            return value;
                        }
                    }
                }
            }
            return LeaveReason.UNKNOWN;
        }

    }

}
