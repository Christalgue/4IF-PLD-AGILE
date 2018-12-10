package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryMovedState.
 */
public class DeliveryMovedState extends DefaultState {
	
	/** The node. */
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
	
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException {
		window.setMessage("");
		commandsList.addCommand(new MoveDeliveryCommand(window,node,previousNode,controller.circuitManagement));
		controller.setCurrentState(controller.calcState);
	}


}
