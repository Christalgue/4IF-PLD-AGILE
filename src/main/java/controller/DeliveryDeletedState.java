package main.java.controller;


import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class DeliveryDeletedState extends DefaultState{
	
	
	
	Node node;
	
	protected void setNode(Node node) {
		this.node = node;
	}
	
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException{
		commandsList.addCommand(new RemoveDeliveryCommand(window,node,controller.circuitManagement));
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
	}
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		window.disableButtonMoveDelivery();
		controller.setCurrentState(controller.calcState);
	}

	

}
