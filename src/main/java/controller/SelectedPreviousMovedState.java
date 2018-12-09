package main.java.controller;

import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.utils.PopUpType;
import main.java.view.Window;

public class SelectedPreviousMovedState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	
	public void leftClick(Controller controller, Window window, Point point) {
	
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);
		if (previousNode != null) {
			//window.nodeSelected(previousNode);
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliveryMovedState.setNode(node);
				controller.deliveryMovedState.setPreviousNode(previousNode);
				controller.setCurrentState(controller.deliveryMovedState);
				controller.getWindow().getPopUpValue(PopUpType.MOVE, controller.getWindow());
			} else {
				// rajouter erreur IHM
			}
		}
	}
	public void mouseMoved(Controller controller, Window window, Point point) {
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if(node!=null) {
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
			window.nodeHover(isDelivery);
		}else {
			window.nodeHover(null);
		}
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
