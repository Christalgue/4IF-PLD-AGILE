package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;


/**
 * The Class MoveDeliveryCommand.
 * The Command to move a delivery
 */
public class MoveDeliveryCommand implements Command {
	
	/** The window. */
	private Window window;
	
	/** The circuit management. */
	private CircuitManagement circuitManagement;
	
	/** The node to move. */
	private Node node1;
	
	/** The previous node selected by the user. */
	private Node node2;
	
	/** The initial previous node. */
	private Node initialPreviousNode;
	
	/** The index of the circuit. */
	private int indexCircuit;
	
	/** The index of the initial circuit. */
	private int initialIndexCircuit;
	
	/**
	 * Instantiates a new MoveDeliveryCommand.
	 *
	 * @param window the window
	 * @param node1 the node to move
	 * @param node2 the previous node
	 * @param circuitManagement the circuit management
	 */
	public MoveDeliveryCommand(Window window, Node node1, Node node2, int indexCircuit, CircuitManagement circuitManagement) {
		this.node1 = node1;
		this.node2 = node2;
		this.initialPreviousNode = circuitManagement.getPreviousNode(node1);
		this.indexCircuit = indexCircuit;
		this.window = window;
		this.circuitManagement = circuitManagement;
		this.initialIndexCircuit = circuitManagement.getCircuitIndexByNode(circuitManagement.isDelivery(node1));
	}

	/**
	 * @see main.java.controller.Command#doCde()
	 */
	@Override
	public void doCde() {
		try {
			circuitManagement.moveDelivery(node1, node2, indexCircuit);
			window.drawCircuits();
		} catch (ManagementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see main.java.controller.Command#undoCde()
	 */
	@Override
	public void undoCde() {
		try {
			circuitManagement.moveDelivery(node1, initialPreviousNode, initialIndexCircuit);
			window.drawCircuits();
		} catch (ManagementException e) {
			e.printStackTrace();
		}
	}

}
