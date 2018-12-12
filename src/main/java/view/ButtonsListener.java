package main.java.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.controller.Controller;
import main.java.utils.Serializer;

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
			case  Window.LOAD_MAP:
				String mapName = controller.getWindow().getFile();
				if (mapName != "")
					controller.loadMap(mapName);
				break;
				
			case Window.LOAD_DELIVERY_OFFER:
				String deliveryListName = controller.getWindow().getFile();
				if (deliveryListName != "")
					controller.loadDeliveryOffer(deliveryListName);
				break;
				
			case Window.CALCULATE_CIRCUITS: 
				if (controller.getWindow().getDeliveryMenNumber())
					controller.calculateCircuits(deliveryMenNumber); 
				break;
				
			case Window.ADD_DELIVERY: 
				controller.addDelivery(); 
				break;
				
			case Window.CONTINUE_CALCULATION:
				controller.continueCalculation(true);
				break;
				
			case Window.UNDO:
				controller.undo();
				break;
			
			case Window.REDO:
				controller.redo();
				break;
				
			case Window.ZOOM:
				controller.getWindow().zoom();
				controller.getWindow().enableResetScaleButton();
				break;
			
			case Window.UNZOOM:
				controller.getWindow().unZoom();
				controller.getWindow().enableResetScaleButton();
				break;
				
			case Window.CANCEL:
				controller.cancelAdd();
				break;
				
			case Window.RESET_SCALE:
				controller.getWindow().resetScale();
				controller.getWindow().disableResetScaleButton();
				controller.getWindow().drawCircuits();
				break;
				
			case Window.GENERATE_ROADMAP:
				String folderPath = controller.getWindow().getFolder();
				if (folderPath != "")
					Serializer.serializer(folderPath, controller.getCircuitManagement());
				break;
				
			case Window.MOVE_DELIVERY: 
				controller.moveDelivery();
				break;
				
			case Window.DELETE_DELIVERY: 
				controller.deleteDelivery(); 
				break;
				
			case Window.UP:
				controller.getWindow().verticalShift(-50);
				controller.getWindow().enableResetScaleButton();
				break;
			
			case Window.DOWN:
				controller.getWindow().verticalShift(50);
				controller.getWindow().enableResetScaleButton();
				break;
				
			case Window.RIGHT:
				controller.getWindow().horizontalShift(50);
				controller.getWindow().enableResetScaleButton();
				break;
			
			case Window.LEFT:
				controller.getWindow().horizontalShift(-50);
				controller.getWindow().enableResetScaleButton();
				break;
		}
	}
}
