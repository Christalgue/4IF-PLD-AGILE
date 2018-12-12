package main.java.controller;

import main.java.exception.ForgivableXMLException;
import main.java.exception.LoadMapException;
import main.java.utils.PopUpType;
import main.java.view.Window;

/**
 * The Class InitialState.
 * The initial State of the Controller, when the user launches the application.
 */
public class InitialState extends DefaultState {
	
	/**
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.setMessage("");
			try {
				controller.getCircuitManagement().loadMap(filename);
				window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			} catch (ForgivableXMLException e) {
				window.setWarningMessage(e.getMessage());
			}
			window.enableDeZoomButton();
			window.enableZoomButton();
			window.enableArrows();
			window.enableButtonLoadDeliveriesList();
			window.calculateScale();
			window.drawMap();
			window.setMouseListener();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
	}

}
