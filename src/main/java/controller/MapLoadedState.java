package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class MapLoadedState extends DefaultState{
	
	
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.enableButtonLoadDeliveriesList();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			window.enableButtonCalculateCircuit();
			controller.circuitManagement.loadDeliveryList(filename);
			window.setMessage("");
			//window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			controller.setCurrentState(controller.deliveryLoadedState);
			window.drawDeliveries();
		} catch (LoadDeliveryException l)
		{
			l.printStackTrace();
		}
	}

}
