package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectedPreviousMovedState.
 */
public class SelectedPreviousMovedState extends DefaultState {
	
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
	
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);
		if (previousNode != null) {
			//window.nodeSelected(previousNode);
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliveryMovedState.setNode(node);
				controller.deliveryMovedState.setPreviousNode(previousNode);
				controller.setCurrentState(controller.deliveryMovedState);
				if(controller.getShowPopUp())
					controller.getWindow().getPopUpValue(PopUpType.MOVE, controller.getWindow());
			} else {
				// rajouter erreur IHM
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
