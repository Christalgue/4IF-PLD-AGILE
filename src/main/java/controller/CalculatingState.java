package main.java.controller;

import main.java.exception.ClusteringException;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.view.Window;

public class CalculatingState extends DefaultState {

	public void continueCalculation(Controller controller, Window window, boolean keepCalculating) {
		if(keepCalculating == true) {
			try {
				System.out.println(keepCalculating);
				System.out.println("///////////////////////////////////////////////////////////////////////////////////////////////////////");
				controller.circuitManagement.calculateCircuits(controller.circuitManagement.getNbDeliveryMan(), keepCalculating);
				controller.setCurrentState(controller.calcState);
			} catch (MapNotChargedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoadDeliveryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClusteringException e) {
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
				controller.setCurrentState(controller.calculatingState);
			}
		}
	}
	
	// just to have a gateway while the implementation is not finished
	// to delete afterwards
	public void loadDeliveryOffer(Controller controller, Window window, String filename){
		
		try {
			controller.circuitManagement.loadDeliveryList(filename);
			window.drawDeliveries();
		} catch (LoadDeliveryException e)
		{
			e.printStackTrace();
		}
		
	}
}
