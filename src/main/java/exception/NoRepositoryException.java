package main.java.exception;

/**
 * The Class NoRepositoryException is instantiated when a method need the repository to function but there is no repository in the delivery list of the circuitManagement.
 */
public class NoRepositoryException extends Exception {


	/**
	 * Instantiates a new no repository exception.
	 *
	 * @param message the message
	 */
	public NoRepositoryException(String message) {
		super(message);
	}
}
