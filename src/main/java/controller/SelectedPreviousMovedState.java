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
			Delivery isDelivery = controller.getCircuitManagement().isDelivery(previousNode);
			window.nodeSelected(isDelivery);
			window.circuitSelected(isDelivery);
			if (controller.circuitManagement.checkNodeInDeliveryList(previousNode)) {
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
	public void treeDeliverySelected(Controller controller, Window window, Delivery deliverySelected, CommandsList commandsList) {
		window.nodeSelected(deliverySelected);
		window.circuitSelected(deliverySelected);
		controller.deliveryMovedState.setNode(node);
		controller.deliveryMovedState.setPreviousNode(deliverySelected.getPosition());
		controller.setCurrentState(controller.deliveryMovedState);
		if(controller.getShowPopUp())
			controller.getWindow().getPopUpValue(PopUpType.MOVE, controller.getWindow());
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
