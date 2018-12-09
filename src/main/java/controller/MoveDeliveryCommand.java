package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class MoveDeliveryCommand implements Command {
	
	private Window window;
	private CircuitManagement circuitManagement;
	private Node node1;
	private Node node2;
	
	public MoveDeliveryCommand(Window window, Node node1, Node node2, CircuitManagement circuitManagement) {
		this.node1 = node1;
		this.node2 = node2;
		this.window = window;
		this.circuitManagement = circuitManagement;
	}

	@Override
	public void doCde() {
		try {
			circuitManagement.moveDelivery(node1, node2);
			window.drawCircuits();
		} catch (ManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void undoCde() {
		try {
			circuitManagement.moveDelivery(node2, node1);
			window.drawCircuits();
		} catch (ManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
