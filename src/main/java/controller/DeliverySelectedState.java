package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ClusteringException;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class DeliverySelectedState extends DefaultState {
	

	Node node;
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if (node != null)
		{
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliverySelectedState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedState);
			} else {
				window.disableButtonMoveDelivery();
				window.disableButtonDeleteDelivery();
				window.enableButtonAddDelivery();
				long id = controller.circuitManagement.getCurrentMap().getIdFromNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				controller.newDeliverySelectedState.setNode(node);
				controller.setCurrentState(controller.durationChoiceState);
			}
		}
		
	}
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
	
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.loadDeliveryList(filename);
			controller.setCurrentState(controller.deliveryLoadedState);
			window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
	
	}

	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan){
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
		} catch (ClusteringException e)
		{
			e.printStackTrace();
		} catch (MapNotChargedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DijkstraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoRepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TSPLimitTimeReachedException e) {
			System.out.println(e.getMessage());
			controller.setCurrentState(controller.calculatingState);
			window.drawCircuits();
		}
	
	}
	
	public void deleteDelivery (Controller controller, Window window) {
		
		controller.deliveryDeletedState.setNode(node);
		controller.setCurrentState(controller.deliveryDeletedState);
	}
	
	public void moveDelivery (Controller controller, Window window) {
		window.disableButtonMoveDelivery();
		window.disableButtonDeleteDelivery();
		window.setMessage("Veuillez selectionner le point de livraison precedent");
		controller.selectedPreviousMovedState.setNode(node);
		controller.setCurrentState(controller.selectedPreviousMovedState);
	}

	


}
