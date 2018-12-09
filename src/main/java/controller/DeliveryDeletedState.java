package main.java.controller;


import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class DeliveryDeletedState extends DefaultState{
	
	
	
	Node node;
	
	protected void setNode(Node node) {
		this.node = node;
	}
	
	public void validate (Controller controller, Window window) throws ManagementException{
		controller.circuitManagement.removeDelivery(node);
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
		window.drawDeliveries();
		window.drawCircuits();
	}
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
	}

	

}
