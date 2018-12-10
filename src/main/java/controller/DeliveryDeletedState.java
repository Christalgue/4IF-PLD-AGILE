package main.java.controller;


import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryDeletedState.
 */
public class DeliveryDeletedState extends DefaultState{
	
	
	
	/** The node. */
	Node node;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode(Node node) {
		this.node = node;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException{
		commandsList.addCommand(new RemoveDeliveryCommand(window,node,controller.circuitManagement));
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
	}

	

}
