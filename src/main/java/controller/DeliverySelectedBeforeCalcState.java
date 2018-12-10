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

// TODO: Auto-generated Javadoc
/**
 * The Class DeliverySelectedBeforeCalcState.
 */
public class DeliverySelectedBeforeCalcState extends DefaultState {
	
	/** The node. */
	Node node;
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
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
				long id = controller.circuitManagement.getCurrentMap().getIdFromCorrespondingNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				controller.nodeSelectedBeforeCalcState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedBeforeCalcState);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
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
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
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
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
	
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
			controller.setCurrentState(controller.deliveryLoadedState);
			window.setMessage("");
			//window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			window.setErrorMessage("Fichier XML invalide");
			e.printStackTrace();
		}
	
	}

	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#calculateCircuits(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){
		commandsList.reset();
		try {
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.setMessage("");
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
	 * @see main.java.controller.DefaultState#deleteDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void deleteDelivery (Controller controller, Window window) {
		window.setMessage("");
		controller.deliveryDeletedBeforeCalcState.setNode(node);
		controller.setCurrentState(controller.deliveryDeletedBeforeCalcState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.DELETE, controller.getWindow());
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
