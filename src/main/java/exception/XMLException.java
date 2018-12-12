package main.java.exception;

/**
 * The Class XMLException is instantiated when a problem occurs while loading the data of an xml file (map or delivery list).
 */
public class XMLException extends Exception{
	
	/**
	 * Instantiates a new XML exception.
	 *
	 * @param message the message
	 */
	public XMLException(String message) {
		super(message);
	}
}
