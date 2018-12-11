package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

/**
 * The Class DeliveryDeletedBeforeCalcState.
 * The State when the user has to choose if he wants to validate the removal of a delivery before the circuits calculation.
 */
public class DeliveryDeletedBeforeCalcState extends DefaultState {
	
	/** The node to delete. */
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
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	@Override
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException{
		window.emptySelectedNode();
		commandsList.addCommand(new RemoveDeliveryCommand(window, node, controller.getCircuitManagement()));
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}
	
	/**
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}

}
