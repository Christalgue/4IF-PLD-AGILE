package main.java.controller;

import main.java.exception.LoadMapException;
import main.java.utils.Deserializer;
import main.java.view.Window;

public class InitialState extends DefaultState {
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			System.out.println(filename);
			controller.circuitManagement.loadMap(filename);
			window.drawMap();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}

}
