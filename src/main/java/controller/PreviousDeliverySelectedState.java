package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class PreviousDeliverySelectedState.
 */
public class PreviousDeliverySelectedState extends DefaultState {
	
	/** The node. */
	Node node;
	
	/** The duration. */
	int duration; 
	
	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	protected void setDuration (int duration) {
		this.duration  = duration;
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#leftClick(main.java.controller.Controller, main.java.view.Window, main.java.entity.Point)
	 */
	public void leftClick(Controller controller, Window window, Point point) {
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));
		if (previousNode != null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			
			if (controller.circuitManagement.checkNodeInDeliveryList(previousNode)) {
				controller.deliveryAddedState.setNode(node);
				controller.deliveryAddedState.setPreviousNode(previousNode);
				controller.deliveryAddedState.setDuration(duration);
				window.setMessage("");
				controller.setCurrentState(controller.deliveryAddedState);
				if(controller.getShowPopUp())
					controller.getWindow().getPopUpValue(PopUpType.ADD, controller.getWindow());
			} else {
				// rajouter erreur IHM
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see main.java.controller.DefaultState#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
		window.circuitSelected(deliverySelected);
		window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
		controller.deliveryAddedState.setNode(node);
		controller.deliveryAddedState.setDuration(duration);
		controller.deliveryAddedState.setPreviousNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliveryAddedState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.ADD, controller.getWindow());
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
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	
	

}
