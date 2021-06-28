package cc.javajobs.factionsbridge.bridge.events;

import cc.javajobs.factionsbridge.bridge.events.infrastructure.FactionEvent;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.FPlayer;
import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * FactionDisbandEvent is called when an Faction is disbanded.
 *
 * @author Callum Johnson
 * @since 28/02/2021 - 08:33
 */
public class FactionDisbandEvent extends FactionEvent {

    /**
     * Reason for the disbandment.
     */
    private final DisbandReason reason;

    /**
     * Player who disbanded the Faction.
     */
    private final FPlayer player;

    /**
     * Constructor to initialise an FactionDisbandEvent.
     *
     * @param fpl who disbanded the Faction.
     * @param faction which was disbanded.
     * @param reason for the disbandment.
     * @param other event object.
     */
    public FactionDisbandEvent(@NotNull FPlayer fpl, @NotNull Faction faction,
                               @NotNull DisbandReason reason, @NotNull Event other) {
        super(faction, other);
        this.reason = reason;
        this.player = fpl;
    }

    /**
     * @see #reason
     * @see DisbandReason
     * @return reason for disbandment.
     */
    @NotNull
    public DisbandReason getReason() {
        return reason;
    }

    /**
     * Method to obtain the {@link FPlayer}.
     *
     * @return {@link FPlayer} who disbanded the faction.
     */
    @NotNull
    public FPlayer getFPlayer() {
        return player;
    }

    /**
     * This enumeration is a bridge for disbandment reasons.
     *
     * @author Callum Johnson
     * @since 28/02/2021 - 08:34
     */
    public enum DisbandReason {

        /**
         * When the faction is disbanded via /f disband.
         */
        COMMAND("DISBAND_COMMAND"),

        /**
         * When the Faction is disbanded by an external plugin.
         */
        PLUGIN,

        /**
         * When the Faction is disbanded due to inactivity.
         */
        INACTIVITY,

        /**
         * When the Faction is disbanded as the Owner left with no players remaining.
         */
        LEAVE,

        /**
         * When the Faction is disbanded.
         */
        UNKNOWN;

        /**
         * Other name for the reason.
         */
        private final String other;

        /**
         * Constructor to initialise a DisbandReason without alternative name.
         */
        DisbandReason() {
            this.other = null;
        }

        /**
         * Constructor to initialise a DisbandReason with an alternative name.
         *
         * @param other_key or other_name.
         */
        DisbandReason(@NotNull String other_key) {
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
        @NotNull
        public static DisbandReason fromString(String key) {
            for (DisbandReason value : DisbandReason.values()) {
                if (value.name().equalsIgnoreCase(key)) return value;
                if (value.other != null && value.other.equalsIgnoreCase(key)) return value;
            }
            return DisbandReason.UNKNOWN;
        }

    }


}
