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

// TODO: Auto-generated Javadoc
/**
 * The Class NodeSelectedState.
 */
public class NodeSelectedState extends DefaultState {
	
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
	 * @see main.java.controller.DefaultState#addDelivery(main.java.controller.Controller, main.java.view.Window)
	 */
	public void addDelivery(Controller controller, Window window) {
		controller.durationChoiceState.setNode(node);
		controller.setCurrentState(controller.durationChoiceState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.DURATION, controller.getWindow());
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void leftClick(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if (node != null)
		{
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				window.enableButtonMoveDelivery();
				window.enableButtonDeleteDelivery();
				window.disableButtonAddDelivery();
				controller.deliverySelectedState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedState);
			} else {
				window.disableButtonMoveDelivery();
				window.disableButtonDeleteDelivery();
				window.enableButtonAddDelivery();
				long id = controller.circuitManagement.getCurrentMap().getIdFromNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				controller.nodeSelectedState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedState);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#mouseMoved(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
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
		//	window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
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
			window.disableButtonMoveDelivery();
			window.disableButtonDeleteDelivery();
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
	

}
