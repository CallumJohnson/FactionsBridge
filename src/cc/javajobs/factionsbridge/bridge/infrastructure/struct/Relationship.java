package cc.javajobs.factionsbridge.bridge.infrastructure.struct;

import org.jetbrains.annotations.NotNull;

/**
 * The Relationship class stands for individualised relationships between relationship participating objects.
 * <p>
 *     A Faction has a relationship to both another Faction and any FPlayer.
 * </p>
 *
 * @author Callum Johnson
 * @since 04/06/2021 - 21:52
 */
public enum Relationship {

    ENEMY, TRUCE, ALLY, MEMBER, NONE;

    /**
     * A static definition of the default relationship.
     */
    public static final Relationship DEFAULT_RELATIONSHIP = NONE;

    /**
     * Accessible method to convert a string into a {@link Relationship}.
     * <p>
     *     This method defaults to {@link #NONE}.
     * </p>
     *
     * @param text to convert
     * @return {@link Relationship} even if there is no match.
     */
    @NotNull
    public static Relationship getRelationship(@NotNull String text) {
        for (Relationship value : Relationship.values()) {
            if (value.name().equalsIgnoreCase(text) || text.toLowerCase().contains(value.name().toLowerCase())) {
                return value;
            }
        }
        return DEFAULT_RELATIONSHIP;
    }

}
