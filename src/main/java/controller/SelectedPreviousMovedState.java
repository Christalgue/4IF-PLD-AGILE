package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

/**
 * The Class SelectedPreviousMovedState.
 * The State when the user has to click on the previous delivery to move a delivery after the circuits calculation.

 */
public class SelectedPreviousMovedState extends DefaultState {
	
	/** The node to move. */
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
	
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);	
		if (previousNode != null && previousNode != node) {
			window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(node));
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(previousNode);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			if (controller.circuitManagement.checkNodeInDeliveryList(previousNode)) {
				controller.deliveryMovedState.setNode(node);
				controller.deliveryMovedState.setPreviousNode(previousNode);
				controller.setCurrentState(controller.deliveryMovedState);
				if(controller.getShowPopUp())
					controller.getWindow().getPopUpValue(PopUpType.MOVE, controller.getWindow());
			}
		}
	}

	/**
	 * @see main.java.controller.DefaultState#treeDeliverySelected(main.java.controller.Controller, main.java.view.Window, main.java.entity.Delivery, main.java.controller.CommandsList)
	 */
	@Override
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		if (deliverySelected.getPosition() != node)
		{
			window.nodeSelected(deliverySelected);
			window.circuitSelected(deliverySelected);
			window.setMessage(controller.circuitManagement.getCurrentMap().displayIntersectionNode(deliverySelected.getPosition()));
			controller.deliveryMovedState.setNode(node);
			controller.deliveryMovedState.setPreviousNode(deliverySelected.getPosition());
			controller.setCurrentState(controller.deliveryMovedState);
			if(controller.getShowPopUp())
				controller.getWindow().getPopUpValue(PopUpType.MOVE, controller.getWindow());
		}
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
		}else {
			window.nodeHover(null);
		}
	}
	
	/**
	 * @see main.java.controller.DefaultState#cancel(main.java.controller.Controller, main.java.view.Window)
	 */
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
