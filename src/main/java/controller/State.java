package main.java.controller;

import main.java.view.Window;

public interface State {
	
	public void loadMap();
	
	public void loadDeliveryOffer();
	
	public void calculateCircuits();
	
	public void undo();
	
	public void addDelivery();
	
	public void validate();
	
	public void cancel();
	
	public void moveDelivery ();
	
	public void deleteDelivery();
}
