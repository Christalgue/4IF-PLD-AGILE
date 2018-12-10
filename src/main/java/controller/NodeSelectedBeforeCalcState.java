package main.java.controller;

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

/**
 * The Class NodeSelectedBeforeCalcState.
 * The State when the user has clicked on a node before the circuits calculation.
 */
public class NodeSelectedBeforeCalcState extends DefaultState {
	
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
	 * @see main.java.controller.DefaultState#addDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	@Override
	public void addDelivery(Controller controller, Window window) {
		controller.durationChoiceBeforeCalcState.setNode(node);
		controller.setCurrentState(controller.durationChoiceBeforeCalcState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.DURATION, controller.getWindow());
	}
	
	/**
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));
		if (node != null)
		{
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				window.enableButtonDeleteDelivery();
				window.disableButtonAddDelivery();
				controller.deliverySelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
			} else {
				window.disableButtonDeleteDelivery();
				window.enableButtonAddDelivery();
				controller.nodeSelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedBeforeCalcState);
			}
		}
		
	}
	
	/**
	 * @see main.java.controller.DefaultState#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	@Override
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
		window.enableButtonDeleteDelivery();
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
		controller.deliverySelectedBeforeCalcState.setNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliverySelectedBeforeCalcState);
	}
	
	/**
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {

		try {
			commandsList.reset();
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			window.drawMap();
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
			window.setMessage("");
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
			controller.setCurrentState(controller.deliveryLoadedState);
			window.drawDeliveries();
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
			window.setMessage("");
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
			controller.setCurrentState(controller.calcState);
		} catch (MapNotChargedException e) {
			window.setErrorMessage("Carte non chargee");
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			window.setErrorMessage("Fichier XML invalide");
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
