package main.java.controller;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class AddDeliveryCommand.
 */
public class AddDeliveryCommand implements Command {
	
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
	 * Instantiates a new adds the delivery command.
	 *
	 * @param window the window
	 * @param node the node
	 * @param duration the duration
	 * @param previousNode the previous node
	 * @param circuitManagement the circuit management
	 */
	public AddDeliveryCommand(Window window, Node node, int duration, Node previousNode, CircuitManagement circuitManagement) {
		this.duration = duration;
		this.node = node;
		this.previousNode = previousNode;
		this.circuitManagement = circuitManagement;
		this.window = window;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.Command#doCde()
	 */
	@Override
	public void doCde() {
		circuitManagement.addDelivery(node, duration, previousNode);
		window.drawCircuits();
	}

	/* (non-Javadoc)
	 * @see main.java.controller.Command#undoCde()
	 */
	@Override
	public void undoCde() {
		try {
			previousNode = circuitManagement.getPreviousNode(node);
			circuitManagement.removeDelivery(node);
			window.drawDeliveries();
			window.drawCircuits();
		} catch (ManagementException e) {
			e.printStackTrace();
		}
	}

}
