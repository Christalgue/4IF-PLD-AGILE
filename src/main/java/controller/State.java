package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Interface State.
 */
public interface State {
	
	/**
	 * Load map.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param filename the filename
	 * @param commandsList the commands list
	 */
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList);
	
	/**
	 * Load delivery offer.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param filename the filename
	 * @param commandsList the commands list
	 */
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList);
	
	/**
	 * Calculate circuits.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param nbDeliveryMan the nb delivery man
	 * @param commandsList the commands list
	 */
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList);
	
	/**
	 * Undo.
	 *
	 * @param controller the controller
	 * @param commandsList the commands list
	 */
	public void undo(Controller controller, CommandsList commandsList);
	
	/**
	 * Redo.
	 *
	 * @param controller the controller
	 * @param commandsList the commands list
	 */
	public void redo(Controller controller, CommandsList commandsList);
	
	/**
	 * Adds the delivery.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void addDelivery(Controller controller, Window window);
	
	/**
	 * Validate.
	 *
	 * @param controler the controler
	 * @param window the window
	 * @param commandsList the commands list
	 * @throws ManagementException the management exception
	 */
	public void validate(Controller controler, Window window, CommandsList commandsList) throws ManagementException;
	
	/**
	 * Cancel.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void cancel(Controller controller, Window window);
	
	/**
	 * Move delivery.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void moveDelivery (Controller controller, Window window);
	
	/**
	 * Delete delivery.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void deleteDelivery(Controller controller, Window window);
	
	/**
	 * Right click.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void rightClick(Controller controller, Window window);
	
	/**
	 * Left click.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param point the point
	 */
	public void leftClick(Controller controller, Window window, Point point);
	
	/**
	 * Mouse moved.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param point the point
	 */
	public void mouseMoved(Controller controller, Window window, Point point);
	
	/**
	 * Validate duration.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param duration the duration
	 * @param commandsList the commands list
	 */
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList);
	
	/**
	 * Continue calculation.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param keepCalculating the keep calculating
	 */
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating);
}
