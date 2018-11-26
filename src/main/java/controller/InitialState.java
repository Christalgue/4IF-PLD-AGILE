package main.java.controller;

import main.java.view.Window;

public class InitialState extends DefaultState {
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		
		controller.setCurrentState(controller.mapLoadedState);
	}
	

}
