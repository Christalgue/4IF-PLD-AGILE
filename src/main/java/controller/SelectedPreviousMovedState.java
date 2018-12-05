package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class SelectedPreviousMovedState extends DefaultState {
	
	Node node;
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	
	public void leftClick(Controller controller, Window window, Point point) {
	
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);
		if (previousNode != null) {
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliveryMovedState.setNode(node);
				controller.deliveryMovedState.setPreviousNode(previousNode);
				controller.setCurrentState(controller.deliveryMovedState);
			} else {
				// rajouter erreur IHM
			}
		}
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
