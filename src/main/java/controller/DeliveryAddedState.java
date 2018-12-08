package main.java.controller;

import main.java.entity.Node;
import main.java.entity.Point;
import main.java.utils.PointUtil;
import main.java.view.Window;

public class DeliveryAddedState extends DefaultState{
	
	Node node;
	Node previousNode;
	int duration;
	
	
	protected void setNode (Node node) {
		this.node =  node;
	}
	
	
	protected void setPreviousNode (Node previousNode) {
		this.previousNode =  previousNode;
	}
	
	
	protected void setDuration (int duration) {
		this.duration =  duration;
	}
	
	public void validate(Controller controller, Window window){
		controller.circuitManagement.addDelivery(node, duration, previousNode);
		controller.setCurrentState(controller.calcState);
		// rajouter appel de fonction vue
	}
	
	public void cancel (Controller controller, Window window) {
		controller.setCurrentState(controller.calcState);
	}
	

}
