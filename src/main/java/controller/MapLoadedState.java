package main.java.controller;

import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.view.Window;

public class MapLoadedState extends DefaultState{
	
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			controller.circuitManagement.loadMap(filename);
			System.out.println(filename);
			window.drawMap();
			System.out.println("Chargement Map déclenché");
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			controller.setCurrentState(controller.deliveryLoadedState);
			System.out.println("deliveries charged");
			window.drawDeliveries();
		} catch (LoadDeliveryException l)
		{
			l.printStackTrace();
		}
		
	}

}
