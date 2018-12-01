package main.java.controller;

import main.java.entity.Node;
import main.java.view.Window;

public interface State {
	
	public void loadMap(Controller controler, Window window, String filename);
	
	public void loadDeliveryOffer(Controller controler, Window window, String filename);
	
	public void calculateCircuits(Controller controler, Window window, int nbDeliveryMan);
	
	//public void undo();
	
	public void addDelivery(Controller controler, Window window, Node node, int duration, Node previousNode);
	
	//spublic void validate(Controller controler, Window window);
	
	public void cancel(Controller controler, Window window);
	
	public void moveDelivery (Controller controler, Window window, Node node,  Node previousNode);
	
	public void deleteDelivery(Controller controler, Window window, Node node);
	
	
	public void rightClick(Controller controler, Window window);
	
	public void leftClick(Controller controler, Window window, Node node, boolean exist);
	
	public void movedMouse(Controller controler, Window window);
	
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating);
}
