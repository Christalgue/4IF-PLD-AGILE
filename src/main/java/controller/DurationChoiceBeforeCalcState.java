package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;


/**
 * The Class DurationChoiceBeforeCalcState.
 *  The State when the user has to choose if he wants to validate the duration of a delivery before the circuits calculation.

 */
public class DurationChoiceBeforeCalcState extends DefaultState {

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
		window.disableButtonAddDelivery();
		commandsList.addCommand(new AddDeliveryCommand(window,node,duration,null,controller.circuitManagement));
		window.emptySelectedNode();
		controller.setCurrentState(controller.deliveryLoadedState);
	}
	
	/**
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}
}
