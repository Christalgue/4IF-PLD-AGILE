package main.java.controller;

import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class AddDeliveryCommand implements Command {
	
	private Window window;
	private CircuitManagement circuitManagement;
	private Node node;
	private Node previousNode;
	private int duration;

	public AddDeliveryCommand(Window window, Node node, int duration, Node previousNode, CircuitManagement circuitManagement) {
		this.duration = duration;
		this.node = node;
		this.previousNode = previousNode;
		this.circuitManagement = circuitManagement;
		this.window = window;
	}
	
	@Override
	public void doCde() {
		circuitManagement.addDelivery(node, duration, previousNode);
		window.drawCircuits();
	}

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
