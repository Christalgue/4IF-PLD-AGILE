package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

public interface State {
	
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList);
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList);
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList);
	
	public void undo(Controller controller, CommandsList commandsList);
	
	public void redo(Controller controller, CommandsList commandsList);
	
	public void addDelivery(Controller controller, Window window);
	
	public void validate(Controller controler, Window window, CommandsList commandsList) throws ManagementException;
	
	public void cancel(Controller controller, Window window);
	
	public void moveDelivery (Controller controller, Window window);
	
	public void deleteDelivery(Controller controller, Window window);
	
	public void rightClick(Controller controller, Window window);
	
	public void leftClick(Controller controller, Window window, Point point);
	
	public void mouseMoved(Controller controller, Window window, Point point);
	
	public void validateDuration (Controller controller, Window window, int duration, CommandsList commandsList);
	
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating);
	
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList);
}
