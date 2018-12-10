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
			window.enableButtonLoadDeliveriesList();
			try {
				controller.circuitManagement.loadMap(filename);
			} catch (ForgivableXMLException e) {
				window.setErrorMessage(e.getMessage());
				if(controller.getShowPopUp())
					window.getPopUpValue(PopUpType.ERROR, window);
			}
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			Window.setMouseListener(window);
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
	}

}
