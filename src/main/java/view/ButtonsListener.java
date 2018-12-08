package main.java.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import main.java.controller.Controller;

/*
 * 
 */
public class ButtonsListener implements ActionListener {

	/**
	 * 
	 */
	private Controller controller;
	
	private String map;
	private String deliveryList; 
	private int deliveryMenNumber;
	
	public void setMap( String map) {
		this.map = map;
	}
	
	public void setDeliveryList( String deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	public void setDeliveryMenNumber( int deliveryMenNumber) {
		this.deliveryMenNumber = deliveryMenNumber;
	}
	
	
	/**
	 * 
	 * @param controller
	 */
	public ButtonsListener(Controller controller){
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		// Method called by the button listener each time a button is clicked
		// Send to controller a message associated to the clicked button
		switch (e.getActionCommand()){
		case Window.LOAD_MAP: 
			Window.getMapName();
			controller.loadMap(map);
			Window.loadDeliveryList.setEnabled(true);
			Window.calculateCircuitButton.setEnabled(false);
			
			/*controller.loadMap(controller.getWindow().getFile());
			Window.loadDeliveryList.setEnabled(true);
			Window.calculateCircuitButton.setEnabled(false);*/
			break;
		case Window.LOAD_DELIVERY_OFFER: 
			Window.getDeliveryListName();
			Window.calculateCircuitButton.setEnabled(true);
			controller.loadDeliveryOffer(deliveryList); 
			
			/*controller.loadDeliveryOffer(controller.getWindow().getFile());
			Window.calculateCircuitButton.setEnabled(true);*/
			break;
		case Window.CALCULATE_CIRCUITS: 
			Window.getDeliveryMenNumber();
			//int popUpValue = controller.getWindow().getPopUpValue(controller.getWindow().popUp.CONTINUE, controller.getWindow());
			controller.calculateCircuits(deliveryMenNumber); 
			break;
		//case Window.UNDO: controller.undo(); break;
		case Window.ADD_DELIVERY: 
			//controller.addDelivery(); 
			break;
		case Window.CONTINUE_CALCULATION:
			controller.continueCalculation(true);
			break;
		case Window.STOP_CALCULATION:
			controller.continueCalculation(false);
			break;
		/*case Window.VALIDATE_ADD: controller.validateAdd(); break;
		case Window.CANCEL_ADD: controller.cancelAdd(); break;
		case Window.MOVE_DELIVERY: controller.moveDelivery();break;
		case Window.VALIDATE_MOVE: controller.validateMove(); break;
		case Window.CANCEL_MOVE: controller.cancelMove(); break;
		case Window.DELETE_DELIVERY: controller.deleteDelivery(); break;
		case Window.VALIDATE_DELETE: controller.validateDelete(); break;
		case Window.CANCEL_DELETE: controller.cancelDelete(); break;*/

		}
	}
}
