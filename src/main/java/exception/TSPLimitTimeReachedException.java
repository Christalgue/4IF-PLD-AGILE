package main.java.exception;

/**
 * The Class TSPLimitTimeReachedException is instantiated when the limit time set for the tsp calculation is reached before the end of this calculation.
 */
public class TSPLimitTimeReachedException extends Exception {


	/**
	 * Instantiates a new TSP limit time reached exception.
	 *
	 * @param message the message
	 */
	public TSPLimitTimeReachedException(String message) {
		super(message);
	}


}
