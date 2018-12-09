package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public class DurationChoiceState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList) {
		controller.previousDeliverySelectedState.setNode(node);
		controller.previousDeliverySelectedState.setDuration(duration);
		window.disableButtonAddDelivery();
		window.setMessage("Veuillez choisir la livraison pr�c�dente");
		controller.setCurrentState(controller.previousDeliverySelectedState);
		
	}
	
	public void cancel (Controller controller, Window window) {
		window.disableButtonAddDelivery();
		controller.setCurrentState(controller.calcState);
	}

}
