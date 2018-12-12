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
 * The Class CalcState.
 * The State when the circuits have been calculated.
 */
public class CalcState extends DefaultState {
	
	/** The number of delivery man. */
	private int nbDeliveryMan;
	
	/**
	 * @see main.java.controller.DefaultState#loadMap(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadMap(Controller controller, Window window, String filename, CommandsList commandsList) {
		
		try {
			controller.circuitManagement.getCircuitsList().clear();
			window.enableButtonAddDelivery();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.drawMap();
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			commandsList.reset();
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#loadDeliveryOffer(main.java.controller.Controller, main.java.view.Window, java.lang.String, main.java.controller.CommandsList)
	 */
	@Override
	public void loadDeliveryOffer(Controller controller, Window window, String filename, CommandsList commandsList){
		
		try {
			window.setMessage("");
			controller.circuitManagement.getCircuitsList().clear();
			window.enableButtonCalculateCircuit();
			controller.circuitManagement.loadDeliveryList(filename);
			commandsList.reset();
			controller.setCurrentState(controller.deliveryLoadedState);
			window.drawDeliveries();
		} catch (LoadDeliveryException l)
		{
			window.setErrorMessage("Fichier XML invalide");
			l.printStackTrace();
		}
		
	}
	
	/**
	 * @see main.java.controller.DefaultState#calculateCircuits(main.java.controller.Controller, main.java.view.Window, int, main.java.controller.CommandsList)
	 */
	@Override
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan, CommandsList commandsList){
		this.nbDeliveryMan = nbDeliveryMan;
		commandsList.reset();
		try {
			window.setMessage("");
			window.disableButtonContinueCalculation();
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
			window.enableButtonContinueCalculation();
			//controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
		}
	}
	
	
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating) {
		if(keepCalculating == true) {
			try {
				window.disableButtonContinueCalculation();
				controller.circuitManagement.calculateCircuits(controller.circuitManagement.getNbDeliveryMan(), keepCalculating);
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
				controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
			}
		} else {
			controller.setCurrentState(controller.calcState);
		}
	}
	
	
	/**
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		
		if (node != null){
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));
			
			if (controller.circuitManagement.checkNodeInDeliveryList(node) && (!controller.circuitManagement.isRepository(node))) {
				window.enableButtonDeleteDelivery();
				window.enableButtonMoveDelivery();
				controller.deliverySelectedState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedState);
			} else if (!controller.circuitManagement.isRepository(node)) {
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
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
		window.nodeSelected(deliverySelected);
		window.circuitSelected(deliverySelected);
		window.enableButtonDeleteDelivery();
		window.enableButtonMoveDelivery();
		controller.deliverySelectedState.setNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliverySelectedState);
	}
	
	/**)
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	@Override
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
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
