package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ClusteringException;
import main.java.exception.DeliveryListNotCharged;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.view.Window;

public class CalcState extends DefaultState {
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			controller.circuitManagement.loadMap(filename);
			window.drawMap();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			controller.setCurrentState(controller.deliveryLoadedState);
		} catch (LoadDeliveryException l)
		{
			l.printStackTrace();
		}
		
	}
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan){
		try {
			controller.circuitManagement.calculateCircuits(nbDeliveryMan);
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
		} catch (NoRepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void leftClick(Controller controller, Window window, Node node, boolean exist) {
		
		if (exist)
		{
			controller.deliverySelectedState.setNode(node);
			controller.setCurrentState(controller.deliverySelectedState);
		} else {
			controller.deliveryAddedState.setNode(node);
			controller.setCurrentState(controller.deliveryAddedState);
		}
		
	}

}
