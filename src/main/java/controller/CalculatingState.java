package main.java.controller;

import javax.swing.JOptionPane;

import main.java.exception.ClusteringException;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.ManagementException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.PopUpType;
import main.java.view.Window;

public class CalculatingState extends DefaultState {

	public void continueCalculation(Controller controller, Window window, boolean keepCalculating) {
		if(keepCalculating == true) {
			try {
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
				window.drawCircuits();
				//controller.setCurrentState(controller.calculatingState);
				controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
			}
		} else {
			controller.setCurrentState(controller.calcState);
		}
	}
}
