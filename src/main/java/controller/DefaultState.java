package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

public abstract class DefaultState implements State {
	
	public void loadMap(Controller controller, Window window, String filename) {}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){}
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan){}
	
	//public void undo();
	
	public void addDelivery(Controller controller, Window window){}
	
	public void validate(Controller controller, Window window) throws ManagementException{}
	
	public void cancel(Controller controller, Window window){}
	
	public void moveDelivery (Controller controller, Window window){}
	
	public void deleteDelivery(Controller controller, Window window){}
	
	
	public void rightClick(Controller controller, Window window){}
	
	public void leftClick(Controller controller, Window window, Point point){}
	
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating){}
	
	public void validateDuration (Controller controller, Window window, int duration) {}
	
	public void mouseMoved(Controller controller, Window window, Point point){}

	@Override
	public String toString() {
		return "State : "+getClass().getName().substring(21);
	}
	
	
}
