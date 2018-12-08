package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class DeliveryMovedState extends DefaultState {
	
	Node node;
	Node previousNode;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	protected void setPreviousNode (Node previousNode) {
		this.previousNode =  previousNode;
	}
	
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	
	public void validate (Controller controller, Window window) throws ManagementException {
		window.setMessage("");
		controller.circuitManagement.moveDelivery(node, previousNode);
		controller.setCurrentState(controller.calcState);
	}


}
