package main.java.exception;

/**
 * The Class DijkstraException is instantiated when a problem occurs in the algorithm to find the shortest path.
 */
public class DijkstraException extends Exception {

	/**
	 * Instantiates a new dijkstra exception.
	 *
	 * @param message the message
	 */
	public DijkstraException(String message) {
		super(message);
	}

}
