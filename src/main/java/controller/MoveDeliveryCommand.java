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
	
	/** The previous node. */
	private Node node2;
	
	/**
	 * Instantiates a new MoveDeliveryCommand.
	 *
	 * @param window the window
	 * @param node1 the node to move
	 * @param node2 the previous node
	 * @param circuitManagement the circuit management
	 */
	public MoveDeliveryCommand(Window window, Node node1, Node node2, CircuitManagement circuitManagement) {
		this.node1 = node1;
		this.node2 = node2;
		this.window = window;
		this.circuitManagement = circuitManagement;
	}

	/**
	 * @see main.java.controller.Command#doCde()
	 */
	@Override
	public void doCde() {
		try {
			circuitManagement.moveDelivery(node1, node2);
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
			circuitManagement.moveDelivery(node2, node1);
			window.drawCircuits();
		} catch (ManagementException e) {
			e.printStackTrace();
		}
	}

}
