package cc.javajobs.factionsbridge.bridge;

/**
 * IRelationship stands for a Relationship between two IFactions.
 * <p>
 *     A relationship can be Enemy (Usually Red), Truce (Usually Purple),
 *     Ally (Usually Pink/Dark Green) and Member (Usually Green).
 * </p>
 * @author Callum Johnson
 * @since 26/02/2021 - 11:54
 */
public enum IRelationship {

    ENEMY, TRUCE, ALLY, MEMBER, NONE;

    public static IRelationship getRelationship(String name) {
        for (IRelationship value : IRelationship.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return NONE;
    }

}
