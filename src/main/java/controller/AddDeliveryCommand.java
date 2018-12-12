package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

/**
 * The Class AddDeliveryCommand.
 * The Command to add a delivery.
 */
public class AddDeliveryCommand implements Command {
	
	/** The window. */
	private Window window;
	
	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The node to add. */
	private Node node;
	
	/** The previous node. */
	private Node previousNode;
	
	/** The duration. */
	private int duration;
	
	/** The index of the circuit. */
	private int indexCircuit;

	/**
	 * Instantiates a new AddDeliveryCommand.
	 *
	 * @param window the window
	 * @param node the node to add
	 * @param duration the duration of the delivery to add
	 * @param previousNode the previous node
	 * @param circuitManagement the circuit management
	 */
	public AddDeliveryCommand(Window window, Node node, int duration, Node previousNode, int indexCircuit, CircuitManagement circuitManagement) {
		this.duration = duration;
		this.node = node;
		this.previousNode = previousNode;
		this.circuitManagement = circuitManagement;
		this.window = window;
		this.indexCircuit = indexCircuit;
	}
	
	/**
	 * @see main.java.controller.Command#doCde()
	 */
	@Override
	public void doCde() {
		circuitManagement.addDelivery(node, duration, previousNode, indexCircuit);
		window.drawCircuits();
	}

	/**
	 * @see main.java.controller.Command#undoCde()
	 */
	@Override
	public void undoCde() {
		try {
			previousNode = circuitManagement.getPreviousNode(node);
			circuitManagement.removeDelivery(node);
			if (circuitManagement.getCircuitsList()!= null) 
				window.drawCircuits();
			else
				window.drawDeliveries();
		} catch (ManagementException e) {
			e.printStackTrace();
		}
	}

}
