package cc.javajobs.factionsbridge.bridge.exceptions;

/**
 * BridgeAlreadyConnectedException is to be thrown when a third party tries to initialise the already initialised bridge.
 *
 * @author Callum Johnson
 * @since 16/04/2021 - 20:35
 */
public class BridgeAlreadyConnectedException extends RuntimeException {

    /**
     * Constructor to initialise a BridgeAlreadyConnectedException to be identifiable as a FactionsBridge fault.
     * @param message to print to console.
     */
    public BridgeAlreadyConnectedException(String message) {
        super(message);
    }

}
