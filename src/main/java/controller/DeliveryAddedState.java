package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

/**
 * The Class DeliveryAddedState.
 * The State when the user has to choose if he wants to validate the addition of a delivery after the circuits calculation.
 */
public class DeliveryAddedState extends DefaultState{
	
	/** The node to add. */
	Node node;
	
	/** The previous node. */
	Node previousNode;
	
	/** The duration. */
	int duration;
	
	
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
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	protected void setDuration (int duration) {
		this.duration =  duration;
	}
	
	/** (non-Javadoc)
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	@Override
	public void validate(Controller controller, Window window, CommandsList commandsList){
		commandsList.addCommand(new AddDeliveryCommand(window, node, duration, previousNode, controller.getCircuitManagement()));
		controller.setCurrentState(controller.calcState);
	}
	
	/** 
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
