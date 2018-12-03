package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public class DurationChoiceState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void validateDuration (Controller controller, Window window, int duration) {
		controller.previousDeliverySelectedState.setNode(node);
		controller.previousDeliverySelectedState.setDuration(duration);
		controller.setCurrentState(controller.previousDeliverySelectedState);
		
	}

}
