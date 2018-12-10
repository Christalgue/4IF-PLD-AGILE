package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryDeletedBeforeCalcState.
 */
public class DeliveryDeletedBeforeCalcState extends DefaultState {
	
	/** The node. */
	Node node;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	@Override
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException{
		commandsList.addCommand(new RemoveDeliveryCommand(window, node, controller.circuitManagement));
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
		window.drawDeliveries();
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}

}
