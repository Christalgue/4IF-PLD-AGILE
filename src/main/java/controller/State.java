package main.java.controller;

import main.java.view.Window;

public interface State {
	
	public void loadMap(Controller controler, Window window, String filename);
	
	public void loadDeliveryOffer(Controller controler, Window window, String filename);
	
	public void calculateCircuits(Controller controler, Window window);
	
	//public void undo();
	
	public void addDelivery(Controller controler, Window window);
	
	public void validate(Controller controler, Window window);
	
	public void cancel(Controller controler, Window window);
	
	public void moveDelivery (Controller controler, Window window);
	
	public void deleteDelivery(Controller controler, Window window);
	
	
	public void rightClick(Controller controler, Window window);
	
	public void leftClick(Controller controler, Window window);
	
	public void movedMouse(Controller controler, Window window);
	
	
}
