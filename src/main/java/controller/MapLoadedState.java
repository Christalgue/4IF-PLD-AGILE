package main.java.controller;

import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class MapLoadedState.
 * The State when the user has loaded a map.
 */
public class MapLoadedState extends DefaultState{
	
	
	/**
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.enableButtonLoadDeliveriesList();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
		} catch (LoadMapException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			
			window.enableButtonCalculateCircuit();
			controller.circuitManagement.loadDeliveryList(filename);
			window.setMessage("");
			controller.setCurrentState(controller.deliveryLoadedState);
			window.drawDeliveries();

		} catch (LoadDeliveryException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
	}

}
