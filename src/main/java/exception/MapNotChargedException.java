package main.java.exception;

/**
 * The Class MapNotChargedException when a method needs the map to be loaded it is not.
 */
public class MapNotChargedException extends Exception{
	
	/**
	 * Instantiates a new map not charged exception.
	 *
	 * @param message the message
	 */
	public MapNotChargedException(String message) {
		super(message);
	}
}