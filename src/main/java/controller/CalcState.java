package main.java.controller;

import javax.swing.JOptionPane;

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

public class CalcState extends DefaultState {
	
	public void loadMap(Controller controller, Window window, String filename) {
		
		try {
			window.enableButtonAddDelivery();
			window.disableButtonCalculateCircuit();
			controller.circuitManagement.loadMap(filename);
			window.drawMap();
			window.setMessage("Veuillez selectionner un fichier de demande de livraisons");
			controller.setCurrentState(controller.mapLoadedState);
		} catch (LoadMapException l)
		{
			l.printStackTrace();
		}
	}
	
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
		
		try {
			window.enableButtonCalculateCircuit();
			controller.circuitManagement.loadDeliveryList(filename);
			controller.setCurrentState(controller.deliveryLoadedState);
			window.setMessage("Veuillez rentrer le nombre de livreurs et appuyer sur \"Calculer les tournees\"");
			window.drawDeliveries();
		} catch (LoadDeliveryException l)
		{
			l.printStackTrace();
		}
		
	}
	
	public void calculateCircuits(Controller controller, Window window, int nbDeliveryMan){
		try {
			controller.circuitManagement.calculateCircuits(nbDeliveryMan, false);
			window.drawCircuits();
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
			int popUpValue = controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
			if(popUpValue == JOptionPane.NO_OPTION) {
				window.drawCircuits();
				controller.setCurrentState(controller.calculatingState);
				System.out.println("*********************************************************************");
			}
			else {
				window.drawCircuits();
				controller.setCurrentState(controller.calcState);
			}
		}
		
	}
	
	
	public void leftClick(Controller controller, Window window, Point point) {
		System.out.println("vfdjkfjhrguigreui");
		Node node = PointUtil.pointToNode(point, controller.circuitManagement);
		if (node != null)
		{
			window.nodeSelected(node);
			if (controller.circuitManagement.checkNodeInDeliveryList(node)) {
				System.out.println("HEYYOUYOUEXIST");
				window.enableButtonDeleteDelivery();
				window.enableButtonMoveDelivery();
				controller.deliverySelectedState.setNode(node);
				controller.setCurrentState(controller.deliverySelectedState);
			} else {
				System.out.println("wesh t'es un nouveau");
				long id = controller.circuitManagement.getCurrentMap().getIdFromNode(point.getX(), point.getY());
				Node newNode = new Node (id, point.getX(), point.getY());
				window.enableButtonAddDelivery();
				controller.nodeSelectedState.setNode(node);
				controller.setCurrentState(controller.nodeSelectedState);
				
			}
		} else {
			System.out.println("RIEN LOL");
		}
		
	}
}
