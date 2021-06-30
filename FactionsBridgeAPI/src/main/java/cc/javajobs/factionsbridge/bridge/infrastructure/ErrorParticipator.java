package cc.javajobs.factionsbridge.bridge.infrastructure;

import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodException;
import cc.javajobs.factionsbridge.bridge.exceptions.BridgeMethodUnsupportedException;
import org.jetbrains.annotations.NotNull;

/**
 * The ErrorParticipator class is a simple interface which allows for the definition of callable method to create Exceptions.
 * <p>
 *     I have created this to ensure that all messages use the correct format when handling Faction Data.
 * </p>
 *
 * @author Callum Johnson
 * @since 05/06/2021 - 11:23
 */
public interface ErrorParticipator {

    /**
     * Method to throw {@link BridgeMethodUnsupportedException}.
     *
     * @param name of the Provider which the method is unsupported for.
     * @param method which is unsupported by the Provider.
     * @return nothing.
     * @throws BridgeMethodUnsupportedException with the given parameters
     */
    default Object unsupported(@NotNull String name, @NotNull String method) {
        throw new BridgeMethodUnsupportedException(name + " doesn't support " + method + "!");
    }

    /**
     * Method to throw {@link BridgeMethodException}.
     *
     * @param clazz which has an error.
     * @param method which had an error.
     * @param reason for the error.
     * @return nothing.
     * @throws BridgeMethodException with the given parameters.
     */
    default Object methodError(@NotNull Class<?> clazz, @NotNull String method, @NotNull String reason) {
        throw new BridgeMethodException(clazz, method, reason);
    }

}
