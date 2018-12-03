package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class DeliveryAddedState extends DefaultState{
	
	Node node;
	
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	public void addDelivery(Controller controller, Window window, Point point , int duration, Point previousPoint){
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		Node previousNode = PointUtil.pointToNode(previousPoint, controller.circuitManagement);
		if (node != null && previousNode != null) {
			controller.circuitManagement.addDelivery(node, duration, previousNode);
			controller.setCurrentState(controller.calcState);
		}
		// rajouter appelle de fonction vue
		
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
