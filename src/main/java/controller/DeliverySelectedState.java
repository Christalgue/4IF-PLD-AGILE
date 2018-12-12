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
 * The Class DeliverySelectedState.
 *  The State when the user has clicked on a delivery after the circuits calculation.
 */
public class DeliverySelectedState extends DefaultState {
	

	/** The node selected. */
	Node node;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/**
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.getCircuitManagement());

		if (node != null)
		{
			window.setMessage(controller.getCircuitManagement().getCurrentMap().displayIntersectionNode(node));
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			
			if (controller.getCircuitManagement().checkNodeInDeliveryList(node) && (!controller.getCircuitManagement().isRepository(node))) {
				window.enableButtonDeleteDelivery();
				controller.deliverySelectedState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedState);
			} else if (!controller.getCircuitManagement().isRepository(node)){
				window.disableButtonMoveDelivery();
				window.disableButtonDeleteDelivery();
				window.enableButtonAddDelivery();
				controller.nodeSelectedState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedState);
			} else {
				window.disableButtonAddDelivery();
				window.disableButtonDeleteDelivery();
				window.disableButtonMoveDelivery();
			}
		}
		
	}
	
	/**
	 * @see main.java.controller.DefaultState#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	@Override
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		if (!controller.getCircuitManagement().isRepository(deliverySelected.getPosition()))
		{
			window.setMessage(controller.getCircuitManagement().getCurrentMap().displayIntersectionNode(node));
			window.nodeSelected(deliverySelected);
			window.circuitSelected(deliverySelected);
			controller.deliverySelectedState.setNode(deliverySelected.getPosition());
			controller.setCurrentState(controller.deliverySelectedState);
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
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			try {
				controller.getCircuitManagement().loadMap(filename);
				window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			} catch (ForgivableXMLException e) {
				window.setWarningMessage(e.getMessage());
			}
			window.disableGenerateRoadmapButton();
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			window.disableButtonCalculateCircuit();
			window.calculateScale();
			window.drawMap();
			controller.getCircuitManagement().setCircuitsList(null);
			controller.getCircuitManagement().setDeliveryList(null);
			commandsList.reset();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException e)
		{
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
	
		try {
			controller.getCircuitManagement().loadDeliveryList(filename);
			window.disableGenerateRoadmapButton();
			window.setMessage("");
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			commandsList.reset();
			controller.setCurrentState(controller.deliveryLoadedState);
			window.drawDeliveries();
			controller.getCircuitManagement().setCircuitsList(null);
		} catch (LoadDeliveryException e)
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
		try {
			controller.getCircuitManagement().calculateCircuits(nbDeliveryMan, false);
			window.setMessage("");
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
		} catch (MapNotChargedException e) {
			window.setErrorMessage("Carte non chargee");
			e.printStackTrace();
		} catch (DijkstraException e) {
			window.setErrorMessage("Erreur lors du calcul des tournees");
			e.printStackTrace();
		} catch (NoRepositoryException e) {
			window.setErrorMessage("Pas d'entrepot");
			e.printStackTrace();
		}catch (TSPLimitTimeReachedException e) {
			window.setMessage("Si vous voulez continuer le calcul veuillez cliquer sur \"continuer le calcul\".");
			window.drawCircuits();
			window.enableButtonContinueCalculation();
			controller.setCurrentState(controller.calcState);
		} catch (DeliveriesNotLoadedException e) {
			window.setErrorMessage("Pas de livraisons chargées");
			e.printStackTrace();
		}
	
	}
	
	/**
	 * @see main.java.controller.DefaultState#deleteDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void deleteDelivery (Controller controller, Window window) {
		window.disableGenerateRoadmapButton();
		window.disableButtonContinueCalculation();
		controller.deliveryDeletedState.setNode(node);
		controller.setCurrentState(controller.deliveryDeletedState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.DELETE, controller.getWindow());
	}
	
	/**
	 * @see main.java.controller.DefaultState#moveDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void moveDelivery (Controller controller, Window window) {
		window.disableGenerateRoadmapButton();
		window.disableButtonContinueCalculation();
		window.enableCancelButton();
		window.disableButtonMoveDelivery();
		window.disableButtonDeleteDelivery();
		window.setMessage("Veuillez selectionner le point de livraison precedent");
		controller.selectedPreviousMovedState.setNode(node);
		controller.setCurrentState(controller.selectedPreviousMovedState);
		
	}

	


}
