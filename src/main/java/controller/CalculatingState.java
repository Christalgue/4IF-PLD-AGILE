package main.java.controller;

import main.java.exception.DeliveriesNotLoadedException;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.PopUpType;
import main.java.view.Window;


/**
 * The Class CalculatingState.
 * The State when the circuits are being calculated
 */
public class CalculatingState extends DefaultState {

	/**
	 * @see main.java.controller.DefaultState#continueCalculation(main.java.controller.Controller, main.java.view.Window, boolean)
	 */
	@Override
	public void continueCalculation(Controller controller, Window window, boolean keepCalculating) {
		if(keepCalculating == true) {
			try {
				controller.circuitManagement.calculateCircuits(controller.circuitManagement.getNbDeliveryMan(), keepCalculating);
				controller.setCurrentState(controller.calcState);
			} catch (MapNotChargedException e) {
				window.setErrorMessage("Carte non chargee");
				e.printStackTrace();
			} catch (DijkstraException e) {
				window.setErrorMessage("Erreur lors du calcul des tournees");
				e.printStackTrace();
			} catch (NoRepositoryException e) {
				window.setErrorMessage("Pas d'entrepot");
				e.printStackTrace();
			} catch (TSPLimitTimeReachedException e) {
				window.drawCircuits();
				controller.getWindow().getPopUpValue(PopUpType.CONTINUE, controller.getWindow());
			} catch (DeliveriesNotLoadedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			controller.setCurrentState(controller.calcState);
		}
	}
}
