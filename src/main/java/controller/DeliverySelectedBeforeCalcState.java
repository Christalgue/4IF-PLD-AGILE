package main.java.controller;

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

public class DeliverySelectedBeforeCalcState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));
		if (node != null)
		{
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliverySelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
			} else {
				window.disableButtonDeleteDelivery();
				window.enableButtonAddDelivery();
				long id = controller.circuitManagement.getCurrentMap().getIdFromNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				controller.nodeSelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedBeforeCalcState);
			}
		}
		
	}
	
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
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
	
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
			commandsList.reset();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
	
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
			controller.setCurrentState(controller.deliveryLoadedState);
			//window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
	
	}

	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){
		commandsList.reset();
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
		} catch (ClusteringException e){
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
	
	public void deleteDelivery (Controller controller, Window window) {
		
		controller.deliveryDeletedBeforeCalcState.setNode(node);
		controller.setCurrentState(controller.deliveryDeletedBeforeCalcState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.DELETE, controller.getWindow());
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
