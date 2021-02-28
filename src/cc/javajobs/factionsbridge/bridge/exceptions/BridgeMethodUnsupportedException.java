package cc.javajobs.factionsbridge.bridge.exceptions;

import cc.javajobs.factionsbridge.util.Communicator;

/**
 * This class is an exception is thrown when an implementation of the API isn't supported by the Provider.
 * @author Callum Johnson
 * @since 27/02/2021 - 08:49
 */
public class BridgeMethodUnsupportedException extends UnsupportedOperationException implements Communicator {

    /**
     * Constructor to initialise the Exception with a non-{@code null} message.
     * @param message to print.
     */
    public BridgeMethodUnsupportedException(String message) {
        super(message);
    }

}
