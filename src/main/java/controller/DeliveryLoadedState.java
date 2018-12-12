package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.DeliveriesNotLoadedException;
import main.java.exception.DijkstraException;
import main.java.exception.ForgivableXMLException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

/**
 * The Class DeliveryLoadedState.
 * The State when the user has loaded a delivery offer.
 */
public class DeliveryLoadedState extends DefaultState {
	
	/**
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			controller.getCircuitManagement().loadDeliveryList(filename);
			commandsList.reset();
			window.setMessage("");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			window.disableButtonCalculateCircuit();
			try {
				controller.getCircuitManagement().loadMap(filename);
				window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			} catch (ForgivableXMLException e) {
				window.setWarningMessage(e.getMessage());
			}
			window.calculateScale();
			window.drawMap();
			commandsList.reset();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @see main.java.controller.DefaultState#calculateCircuits(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	@Override
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){
		
		commandsList.reset();
		window.emptyColors();
		try {
			window.setMessage("");
			window.enableGenerateRoadmapButton();
			controller.getCircuitManagement().calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
		} catch (MapNotChargedException e) {
			window.setErrorMessage("Map non chargee");
			e.printStackTrace();
		} catch (DijkstraException e) {
			window.setErrorMessage("Erreur lors du calcul des tournees");
			e.printStackTrace();
		} catch (NoRepositoryException e) {
			window.setErrorMessage("Pas d'entrepot");
			e.printStackTrace();
		} catch (TSPLimitTimeReachedException e) {
			window.drawCircuits();
			controller.setCurrentState(controller.calculatingState);
			controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
		} catch (DeliveriesNotLoadedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.getCircuitManagement());


		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			window.setMessage(controller.getCircuitManagement().getCurrentMap().displayIntersectionNode(node));
			if (controller.getCircuitManagement().checkNodeInDeliveryList(node) && (!controller.getCircuitManagement().isRepository(node)) ) {
				window.enableButtonDeleteDelivery();
				controller.deliverySelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
			} else if ((!controller.getCircuitManagement().isRepository(node))) {
				window.enableButtonAddDelivery();
				controller.nodeSelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedBeforeCalcState);
				
			} else {
				window.disableButtonAddDelivery();
				window.disableButtonDeleteDelivery();
				window.disableButtonMoveDelivery();
			}
		}
	}
	/**
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		if (!controller.getCircuitManagement().isRepository(deliverySelected.getPosition())) {
			
			window.setMessage(controller.getCircuitManagement().getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
			window.nodeSelected(deliverySelected);
			window.enableButtonDeleteDelivery();
			controller.deliverySelectedBeforeCalcState.setNode(deliverySelected.getPosition());
			controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.getCircuitManagement());
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
		}else {
			window.nodeHover(null);
		}
	}

	/**
	 * @see main.java.controller.DefaultState#undo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	@Override
	public void undo(Controller controller, CommandsList commandsList) {
		commandsList.undo();
	}

	/**
	 * @see main.java.controller.DefaultState#redo(main.java.controller.Controller, main.java.controller.CommandsList)
	 */
	@Override
	public void redo(Controller controller, CommandsList commandsList) {
		commandsList.redo();
	}
	
	

}
