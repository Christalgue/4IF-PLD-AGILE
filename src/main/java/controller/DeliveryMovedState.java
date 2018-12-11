package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;


/**
 * The Class DeliveryMovedState.
 * The State when the user has to choose if he wants to validate the move of a delivery after the circuits calculation.
 */
public class DeliveryMovedState extends DefaultState {
	
	/** The node to move. */
	Node node;
	
	/** The previous node. */
	Node previousNode;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/**
	 * Sets the previous node.
	 *
	 * @param previousNode the new previous node
	 */
	protected void setPreviousNode (Node previousNode) {
		this.previousNode =  previousNode;
	}
	
	
	/**
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	
	/**
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	@Override
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException {
		window.setMessage("");
		window.emptySelectedNode();
		commandsList.addCommand(new MoveDeliveryCommand(window,node,previousNode,controller.getCircuitManagement()));
		controller.setCurrentState(controller.calcState);
	}


}
