package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class PreviousDeliverySelectedState extends DefaultState {
	
	Node node;
	int duration; 
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	protected void setDuration (int duration) {
		this.duration  = duration;
	}
	
	public void leftClick(Controller controller, Window window, Point point) {
		Node previousNode = PointUtil.pointToNode(point, controller.circuitManagement);
		if (previousNode != null) {
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				controller.deliveryAddedState.setNode(node);
				controller.deliveryAddedState.setPreviousNode(previousNode);
				controller.deliveryAddedState.setDuration(duration);
				controller.setCurrentState(controller.deliveryAddedState);
			} else {
				// rajouter erreur IHM
			}
		}
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	
	

}
