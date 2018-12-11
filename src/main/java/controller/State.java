package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

/**
 * The Interface State.
 * The Interface that will define the different states of the application.
 */
public interface State {
	
	/**
	 * Load a map from a XML file.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param filename the filename
	 * @param commandsList the commands list
	 */
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList);
	
	/**
	 * Load a delivery offer from a XML file.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param filename the filename
	 * @param commandsList the commands list
	 */
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList);
	
	/**
	 * Calculate the circuits.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param nbDeliveryMan the number of delivery man
	 * @param commandsList the commands list
	 */
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList);
	
	/**
	 * Undo the last action.
	 *
	 * @param controller the controller
	 * @param commandsList the commands list
	 */
	public void undo(Controller controller, CommandsList commandsList);
	
	/**
	 * Redo the last action.
	 *
	 * @param controller the controller
	 * @param commandsList the commands list
	 */
	public void redo(Controller controller, CommandsList commandsList);
	
	/**
	 * Method called when the user clicks on "Ajouter une livraison". Change the current state.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void addDelivery(Controller controller, Window window);
	
	/**
	 * Validate an action (add a delivery, delete a delivery ..).
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param commandsList the commands list
	 * @throws ManagementException the management exception
	 */
	public void validate(Controller controller, Window window, CommandsList commandsList) throws ManagementException;
	
	/**
	 * Cancel an action.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void cancel(Controller controller, Window window);
	
	/**
	 * Method called when the user clicks on "Deplacer la livraison". Change the current state. 
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void moveDelivery (Controller controller, Window window);
	
	/**
	 * Method called when the user clicks on "Supprimer la livraison". Change the current state.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void deleteDelivery(Controller controller, Window window);
	
	/**
	 * Method called when the user do a right click.
	 *
	 * @param controller the controller
	 * @param window the window
	 */
	public void rightClick(Controller controller, Window window);
	
	/**
	 * Method called when the user do a left click.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param point the point
	 */
	public void leftClick(Controller controller, Window window, Point point);
	
	/**
	 * Method called when the mouse move.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param point the point
	 */
	public void mouseMoved(Controller controller, Window window, Point point);
	
	/**
	 * Validate the duration of a delivery.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param duration the duration
	 * @param commandsList the commands list
	 */
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList);
	
	/**
	 * Continue the circuits calculation.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param keepCalculating the keep calculating
	 */
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating);
	

	/**
	 * Method called when the user clicks on a delivery on the tree of deliveries.
	 *
	 * @param controller the controller
	 * @param window the window
	 * @param commandsList the commands list
	 */
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList);
}
