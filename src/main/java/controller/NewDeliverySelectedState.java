package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public class NewDeliverySelectedState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void addDelivery(Controller controller, Window window) {
		controller.durationChoiceState.setNode(node);
		controller.setCurrentState(controller.durationChoiceState);
	}
	
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.calcState);
	}

}
