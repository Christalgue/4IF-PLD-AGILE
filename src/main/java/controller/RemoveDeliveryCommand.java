package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class RemoveDeliveryCommand implements Command {

	private Window window;
	private CircuitManagement circuitManagement;
	private Node node;
	private Node previousNode;
	private int duration;

	public RemoveDeliveryCommand(Window window, Node node, CircuitManagement circuitManagement) {
		this.node = node;
		this.circuitManagement = circuitManagement;
		this.duration = circuitManagement.getDeliveryByNode(node).getDuration();
		this.previousNode = circuitManagement.getPreviousNode(node);
		this.window = window;
	}
	
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

	@Override
	public void undoCde() {
		circuitManagement.addDelivery(node,duration, previousNode);
		window.drawCircuits();
	}

}
