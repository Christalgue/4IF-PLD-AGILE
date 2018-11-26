package main.java.controller;


import main.java.view.Window;

public class DeliveryDeletedState extends DefaultState{
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.deliverySelectedState);
	}
	
	public void validate (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}

	

}
