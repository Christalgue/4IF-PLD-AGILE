package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoveDeliveryCommand.
 */
public class RemoveDeliveryCommand implements Command {

	/** The window. */
	private Window window;
	
	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The node. */
	private Node node;
	
	/** The previous node. */
	private Node previousNode;
	
	/** The duration. */
	private int duration;

	/**
	 * Instantiates a new removes the delivery command.
	 *
	 * @param window the window
	 * @param node the node
	 * @param circuitManagement the circuit management
	 */
	public RemoveDeliveryCommand(Window window, Node node, CircuitManagement circuitManagement) {
		this.node = node;
		this.circuitManagement = circuitManagement;
		this.duration = circuitManagement.getDeliveryByNode(node).getDuration();
		this.previousNode = circuitManagement.getPreviousNode(node);
		this.window = window;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.Command#doCde()
	 */
	@Override
	public void doCde() {
		try {
			circuitManagement.removeDelivery(node);
			window.drawCircuits();
		} catch (ManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see main.java.controller.Command#undoCde()
	 */
	@Override
	public void undoCde() {
		circuitManagement.addDelivery(node,duration, previousNode);
		window.drawCircuits();
	}

}
