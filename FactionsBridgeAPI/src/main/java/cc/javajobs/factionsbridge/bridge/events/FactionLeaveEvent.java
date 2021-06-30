package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FPlayerEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FactionLeaveEvent is called when an FPlayer leaves a Faction.
 *
 * @author Callum Johnson
 * @since 04/03/2021 - 19:21
 */
public class FactionLeaveEvent extends FPlayerEvent {

    /**
     * The reason this event was called.
     */
    private final LeaveReason reason;

    /**
     * Constructor to initialise an FactionLeaveEvent.
     * <p>
     *     Event parameter can be null, with thanks to FactionsBlue and their lack of event API.
     * </p>
     *
     * @param faction which was left.
     * @param fplayer which left.
     * @param other   event object.
     * @param reason which the player left.
     */
    public FactionLeaveEvent(@NotNull Faction faction, @NotNull FPlayer fplayer,
                             @NotNull LeaveReason reason, @Nullable Event other) {
        super(faction, fplayer, other);
        this.reason = reason;
    }

    /**
     * @see LeaveReason
     * @return reason for leaving.
     */
    @NotNull
    public LeaveReason getReason() {
        return reason;
    }

    /**
     * This enumeration is a bridge for leaving reasons.
     * @author Callum Johnson
     * @since 28/02/2021 - 08:34
     */
    public enum LeaveReason {

        /**
         * The player left the faction, usually with /f leave.
         */
        LEAVE("LEFT"),

        /**
         * The player was kicked from the Faction by an Administrator.
         */
        KICK("KICKED"),

        /**
         * Reason unspecified, it just happened!
         */
        UNKNOWN();

        /**
         * Variants of the reason's name.
         * @see #fromString(String)
         */
        private final String[] variants;

        /**
         * Constructor to initialise {@link #variants}.
         *
         * @param variants store.
         */
        LeaveReason(@NotNull String... variants) {
            this.variants = variants;
        }

        /**
         * Method to obtain the variants for a given LeaveReason.
         *
         * @return {@link #variants}.
         */
        @NotNull
        public String[] getVariants() {
            return variants;
        }

        /**
         * Method to return a LeaveReason from a String.
         * <p>
         *     This method will return {@link LeaveReason#UNKNOWN} if
         *     the method cannot find a suitable match.
         * </p>
         * @param key to get a LeaveReason for.
         * @return a LeaveReason entry.
         */
        public static LeaveReason fromString(String key) {
            for (LeaveReason value : LeaveReason.values()) {
                if (value.name().equalsIgnoreCase(key)) return value;
                for (String variant : value.getVariants()) if (variant.equalsIgnoreCase(key)) return value;
            }
            return LeaveReason.UNKNOWN;
        }

    }

}
