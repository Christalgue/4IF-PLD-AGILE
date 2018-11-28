package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public class DeliveryAddedState extends DefaultState{
	
	Node node;
	
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void addDelivery(Controller controller, Window window, Node node , int duration, Node previousNode){
		controller.circuitManagement.addDelivery(node, duration, previousNode);
		controller.setCurrentState(controller.calcState);
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}

}
