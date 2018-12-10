package main.java.controller;

import javax.swing.JOptionPane;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class DeliveryLoadedState.
 */
public class DeliveryLoadedState extends DefaultState {
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
		//	window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
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
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#calculateCircuits(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){

		commandsList.reset();
		try {
			window.setMessage("");
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
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
			window.drawCircuits();
			controller.setCurrentState(controller.calculatingState);
			controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
		}
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);

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
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
		window.enableButtonDeleteDelivery();
		controller.deliverySelectedBeforeCalcState.setNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
		}else {
			window.nodeHover(null);
		}
	}

	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#undo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	@Override
	public void undo(Controller controller, CommandsList commandsList) {
		commandsList.undo();
	}

	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#redo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	@Override
	public void redo(Controller controller, CommandsList commandsList) {
		commandsList.redo();
	}
	
	

}
