package main.java.controller;

import main.java.exception.LoadMapException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class InitialState.
 */
public class InitialState extends DefaultState {
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.enableButtonLoadDeliveriesList();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			window.setMouseListener(window);
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}

}
