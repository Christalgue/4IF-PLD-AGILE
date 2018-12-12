package main.java.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.controller.Controller;
import main.java.utils.Serializer;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving buttons events.
 * The class that is interested in processing a buttons
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addButtonsListener<code> method. When
 * the buttons event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ButtonsEvent
 */
/*
 * 
 */
public class ButtonsListener implements ActionListener {

	/** The controller. */
	private Controller controller;
	
	/** The map. */
	private String map;
	
	/** The delivery list. */
	private String deliveryList; 
	
	/** The delivery men number. */
	private int deliveryMenNumber;
	
	/**
	 * Sets the map.
	 *
	 * @param map the new map
	 */
	public void setMap( String map) {
		this.map = map;
	}
	
	/**
	 * Sets the delivery list.
	 *
	 * @param deliveryList the new delivery list
	 */
	public void setDeliveryList( String deliveryList) {
		this.deliveryList = deliveryList;
	}
	
	/**
	 * Sets the delivery men number.
	 *
	 * @param deliveryMenNumber the new delivery men number
	 */
	public void setDeliveryMenNumber( int deliveryMenNumber) {
		this.deliveryMenNumber = deliveryMenNumber;
	}
	
	/**
	 * Instantiates a new buttons listener.
	 *
	 * @param controller the controller
	 */
	public ButtonsListener(Controller controller){
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
