package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class DeliveryDeletedBeforeCalcState extends DefaultState {
	
	Node node;
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void validate (Controller controller, Window window) throws ManagementException{
		controller.circuitManagement.removeDeliveryInDeliveryList(node);
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
		window.drawDeliveries();
	}
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}

}
