package cc.javajobs.factionsbridge.bridge.exceptions;

/**
 * BridgeMethodException is to be thrown when a Method fails to use Reflection properly.
 *
 * @author Callum Johnson
 * @since 27/02/2021 - 09:18
 */
public class BridgeMethodException extends RuntimeException {

    /**
     * Constructor to initialise a BridgeMethodException to be identifiable as a FactionsBridge fault.
     *
     * @param message to print to console.
     * @param location of class.
     * @param method which failed.
     */
    public BridgeMethodException(Class<?> location, String method, String message) {
        super("Method failed for " + location.getSimpleName() + ". Method:\t" + method + ".\t" + message);
    }

}
