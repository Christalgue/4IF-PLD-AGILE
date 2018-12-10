package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;


/**
 * The Class DurationChoiceState.
 *  The State when the user has to choose if he wants to validate the duration of a delivery after the circuits calculation.

 */
public class DurationChoiceState extends DefaultState {
	
	/** The node to add. */
	Node node;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/**
	 * @see main.java.controller.DefaultState#validateDuration(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	@Override
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList) {
		controller.previousDeliverySelectedState.setNode(node);
		controller.previousDeliverySelectedState.setDuration(duration);
		window.disableButtonAddDelivery();
		window.setMessage("Veuillez choisir la livraison precedente");
		controller.setCurrentState(controller.previousDeliverySelectedState);
		
	}
	
	/**
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.calcState);
	}

}
