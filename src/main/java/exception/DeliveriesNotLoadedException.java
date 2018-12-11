package main.java.exception;

/**
 * The Class DeliveriesNotLoadedException when a method needs the map to be loaded it is not.
 */
public class DeliveriesNotLoadedException extends Exception {

	/**
	 * Instantiates a new deliveries not loaded exception.
	 *
	 * @param message the message
	 */
	public DeliveriesNotLoadedException(String message) {
		super(message);
	}
}
