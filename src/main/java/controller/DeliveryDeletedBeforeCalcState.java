package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class DeliveryDeletedBeforeCalcState extends DefaultState {
	
	Node node;
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	@Override
	public void validate (Controller controller, Window window, CommandsList commandsList) throws ManagementException{
		commandsList.addCommand(new RemoveDeliveryCommand(window, node, controller.circuitManagement));
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
		window.drawDeliveries();
	}
	
	@Override
	public void cancel (Controller controller, Window window) {
		window.disableButtonDeleteDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}

}
