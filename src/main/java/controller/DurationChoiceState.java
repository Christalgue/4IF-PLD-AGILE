package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DurationChoiceState.
 */
public class DurationChoiceState extends DefaultState {
	
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
	 * @see main.java.controller.DefaultState#validateDuration(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList) {
		controller.previousDeliverySelectedState.setNode(node);
		controller.previousDeliverySelectedState.setDuration(duration);
		window.disableButtonAddDelivery();
		window.setMessage("Veuillez choisir la livraison pr�c�dente");
		controller.setCurrentState(controller.previousDeliverySelectedState);
		
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.calcState);
	}

}
