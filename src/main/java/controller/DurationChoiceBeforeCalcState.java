package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public class DurationChoiceBeforeCalcState extends DefaultState {

	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	

	public void validateDuration (Controller controller, Window window, int duration) {
		window.disableButtonAddDelivery();
		controller.circuitManagement.addDeliveryInDeliveryList(node, duration);
		controller.setCurrentState(controller.deliveryLoadedState);
		
	}
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.deliveryLoadedState);
	}
}
