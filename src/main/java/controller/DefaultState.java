package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

/**
 * The Class DefaultState.
 * The default State, which implements every method of State.
 */
public abstract class DefaultState implements State {
	
	/**
	 * @see main.java.controller.State#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {}
	
	/**
	 * @see main.java.controller.State#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){}
	
	/**
	 * @see main.java.controller.State#calculateCircuits(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){}
	
	/**
	 * @see main.java.controller.State#undo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	public void undo(Controller controller, CommandsList commandsList){}
	
	/**
	 * @see main.java.controller.State#redo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	public void redo(Controller controller, CommandsList commandsList){}
	
	/**
	 * @see main.java.controller.State#addDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void addDelivery(Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	public void validate(Controller controller, Window window, CommandsList commandsList) throws ManagementException{}
	
	/**
	 * @see main.java.controller.State#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel(Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#moveDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void moveDelivery (Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#deleteDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void deleteDelivery(Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, int indexCircuit, CommandsList commandsList) {}
	
	/**
	 * @see main.java.controller.State#rightClick(main.java.controller.Controller, main.java.view.Window)
	 */
	public void rightClick(Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void leftClick(Controller controller, Window window, Point point){}
	
	/**
	 * @see main.java.controller.State#continueCalculation(main.java.controller.Controller, main.java.view.Window)
	 */
	public void continueCalculation(Controller controller, Window window){}
	
	/**
	 * @see main.java.controller.State#validateDuration(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList) {}
	
	/**
	 * @see main.java.controller.State#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void mouseMoved(Controller controller, Window window, Point point){}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State : "+getClass().getName().substring(21);
	}
	
	
}
