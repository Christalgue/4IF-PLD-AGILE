package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryAddedState.
 */
public class DeliveryAddedState extends DefaultState{
	
	/** The node. */
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
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#validate(main.java.controller.Controller, main.java.view.Window, main.java.controller.CommandsList)
	 */
	public void validate(Controller controller, Window window, CommandsList commandsList){
		commandsList.addCommand(new AddDeliveryCommand(window, node, duration, previousNode, controller.circuitManagement));
		//controller.circuitManagement.addDelivery(node, duration, previousNode);
		controller.setCurrentState(controller.calcState);
		//window.drawDeliveries();
		//window.drawCircuits();
		// rajouter appel de fonction vue
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
