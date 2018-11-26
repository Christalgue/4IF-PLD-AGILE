package main.java.controller;

import main.java.exception.ClusteringException;
import main.java.exception.DeliveryListNotCharged;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.view.Window;

public class DeliveryLoadedState extends DefaultState {
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			controller.circuitManagement.loadMap(filename);
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan){
		try {
			controller.circuitManagement.calculateCircuits(nbDeliveryMan);
			controller.setCurrentState(controller.calcState);
		} catch (ClusteringException e)
		{
			e.printStackTrace();
		} catch (MapNotChargedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeliveryListNotCharged e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DijkstraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
