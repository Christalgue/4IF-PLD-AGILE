package main.java.exception;

/**
 * The Class LoadMapException is instantiated when a problem occurred while loading the map and the map haven't been loaded.
 */
public class LoadMapException extends Exception{
	
	/**
	 * Instantiates a new load map exception.
	 *
	 * @param message the message
	 */
	public LoadMapException(String message) {
		super(message);
	}
}
