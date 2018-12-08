package main.java.controller;

import main.java.exception.LoadMapException;
import main.java.view.Window;

public class InitialState extends DefaultState {
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			window.enableButtonLoadDeliveriesList();
			System.out.println(filename);
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}

}
