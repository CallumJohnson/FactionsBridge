package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import org.jetbrains.annotations.NotNull;

/**
 * The Role class stands for an individual Role provided by the FactionsProvider.
 * <p>
 *     Due to the nature of projects like FactionsX and FactionsBlue, custom roles are possible, this will return,
 *     {@link #CUSTOM} if there is a custom or unknown role attached to the player.
 *     <br>If the custom role is found then I would recommend specifically supporting the factions implementation.
 * </p>
 * @author Callum Johnson
 * @since 28/06/2021 - 14:07
 */
public enum Role {

    /**
     * The leader of the Faction.
     */
    LEADER("ADMIN", "OWNER", "KING"),

    /**
     * The co-leader or admin of the Faction.
     */
    CO_LEADER("GENERAL", "ADMINISTRATOR", "COOWNER", "CO-OWNER"),

    /**
     * The officer or moderator of the Faction.
     */
    OFFICER("MODERATOR", "LIEUTENANT", "MOD", "KNIGHT"),

    /**
     * The normal member of the Faction.
     */
    NORMAL("MEMBER", "PLAYER"),

    /**
     * The recruit or newbies of the Faction.
     */
    RECRUIT,

    /**
     * The custom role indicating that you should support the factions project specifically.
     */
    CUSTOM,

    /**
     * The role a player is when they are not in a Faction.
     */
    FACTIONLESS;

    /**
     * The default role if none is found is Custom.
     * @see Role
     */
    public static final Role DEFAULT_ROLE = CUSTOM;

    /**
     * Alt/Alternative names provided by different Factions implementations.
     */
    private final String[] alt;

    /**
     * Constructor to allocate alternative names to each Role.
     * @param alternatives to store.
     * @see #alt
     */
    Role(String... alternatives) {
        this.alt = alternatives;
    }

    /**
     * Method to obtain a Role from a String.
     *
     * @param name to get the role equivalent for.
     * @return {@link Role} related to the String or default {@link #DEFAULT_ROLE}.
     */
    public static Role getRole(@NotNull String name) {
        for (Role value : Role.values()) {
            if (value.name().equalsIgnoreCase(name)) return value;
            for (String alternativeName : value.alt) if (alternativeName.equalsIgnoreCase(name)) return value;
        }
        return DEFAULT_ROLE;
    }

    /**
     * Method to obtain the Owner role for all implementations.
     *
     * @return {@link #LEADER}.
     */
    public static Role getOwner() {
        return Role.LEADER;
    }

}
