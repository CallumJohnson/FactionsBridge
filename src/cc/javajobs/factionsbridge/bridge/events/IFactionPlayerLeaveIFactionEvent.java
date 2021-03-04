package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.IFaction;
import cc.javajobs.factionsbridge.bridge.IFactionPlayer;
import org.bukkit.event.Event;

/**
 * IFactionPlayerLeaveIFactionEvent is called when an IFactionPlayer
 * leaves an IFaction.
 *
 * @author Callum Johnson
 * @since 04/03/2021 - 19:21
 */
public class IFactionPlayerLeaveIFactionEvent extends IFactionPlayerEvent {

    private final LeaveReason reason;

    /**
     * Constructor to initialise an IFactionPlayerLeaveIFactionEvent.
     * @param faction which was left.
     * @param fplayer which left.
     * @param other   event object.
     */
    public IFactionPlayerLeaveIFactionEvent(IFaction faction, IFactionPlayer fplayer, LeaveReason reason, Event other) {
        super(faction, fplayer, other);
        this.reason = reason;
    }

    /**
     * @see IFactionPlayerLeaveIFactionEvent.LeaveReason
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
         *     This method will return {@link IFactionPlayerLeaveIFactionEvent.LeaveReason#UNKNOWN} if
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
