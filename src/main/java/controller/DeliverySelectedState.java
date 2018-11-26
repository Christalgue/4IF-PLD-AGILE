package main.java.controller;

import main.java.entity.Node;
import main.java.exception.ClusteringException;
import main.java.exception.DeliveryListNotCharged;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.view.Window;

public class DeliverySelectedState extends DefaultState {
	

	Node node;
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void leftClick(Controller controller, Window window, Node node, boolean exist) {
		
		if (exist)
		{
			controller.deliverySelectedState.setNode(node);
		} else {
			controller.deliveryAddedState.setNode(node);
			controller.setCurrentState(controller.deliveryAddedState);
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
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
	
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			controller.setCurrentState(controller.deliveryLoadedState);
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
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
		}
	
	}
	
	public void deleteDelivery (Controller controller, Window window) {
		controller.setCurrentState(controller.deliveryDeletedState);
	}
	
	public void moveDelivery (Controller controller, Window window) {
		controller.setCurrentState(controller.deliveryMovedState);
	}

	


}
