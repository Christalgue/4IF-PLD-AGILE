package main.java.exception;

/**
 * The Class LoadDeliveryException is instantiated when a problem occurred while loading the deliveries and the deliveries haven't been loaded.
 */
public class LoadDeliveryException extends Exception{
	
	/**
	 * Instantiates a new load delivery exception.
	 *
	 * @param message the message
	 */
	public LoadDeliveryException(String message) {
		super(message);
	}
}
