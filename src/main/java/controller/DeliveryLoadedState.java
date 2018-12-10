package main.java.controller;

import javax.swing.JOptionPane;

import main.java.entity.Delivery;
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
import main.java.utils.PopUpType;
import main.java.view.Window;

public class DeliveryLoadedState extends DefaultState {
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
			window.setMessage("");
		//	window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			//window.enableButtonLoadDeliveriesList();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			commandsList.reset();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
	}
	
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){

		commandsList.reset();
		try {
			window.setMessage("");
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
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
			window.drawCircuits();
			controller.setCurrentState(controller.calculatingState);
			//System.err.println("*********************************************************************");
			controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
		}
	}
	
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);

		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));

		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			
			//Rajoute pour ajout/supp depuis la liste de dl loaded
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				window.enableButtonDeleteDelivery();
				controller.deliverySelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
			} else {
				long id = controller.circuitManagement.getCurrentMap().getIdFromNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				window.enableButtonAddDelivery();
				controller.nodeSelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedBeforeCalcState);
				
			}
		}
	}
	
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.setMessage("");
		window.nodeSelected(deliverySelected);
		window.enableButtonDeleteDelivery();
		controller.deliverySelectedBeforeCalcState.setNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
	}
	
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
		}else {
			window.nodeHover(null);
		}
	}

	@Override
	public void undo(Controller controller, CommandsList commandsList) {
		commandsList.undo();
	}

	@Override
	public void redo(Controller controller, CommandsList commandsList) {
		commandsList.redo();
	}
	
	

}
