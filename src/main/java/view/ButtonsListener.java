package main.java.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.controller.Controller;
import main.java.utils.Serializer;

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
	
	/** The delivery men number. */
	private int deliveryMenNumber;
	
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
	 * 
	 * Method called by the button listener each time a button is clicked.
	 * Each case call either a method of the controller or a method of the window.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
			// When the user wants to load the map.
			case  Window.LOAD_MAP:
				String mapName = controller.getWindow().getFilePath();
				if (mapName != "")
					controller.loadMap(mapName);
				break;
			
			// When the user wants to load the list of the deliveries.
			case Window.LOAD_DELIVERY_OFFER:
				String deliveryListName = controller.getWindow().getFilePath();
				if (deliveryListName != "")
					controller.loadDeliveryOffer(deliveryListName);
				break;
				
			// When the user wants to calculate the circuits.
			case Window.CALCULATE_CIRCUITS: 
				if (controller.getWindow().getDeliveryMenNumber())
					controller.calculateCircuits(deliveryMenNumber); 
				break;
				
			// When the user wants to add a delivery.
			case Window.ADD_DELIVERY: 
				controller.addDelivery(); 
				break;
				
			// When the user want to find a better circuit after the timeout in the Travelling Salesman Problem.
			case Window.CONTINUE_CALCULATION:
				controller.continueCalculation(true);
				break;
				
			// When the user wants to undo his previous action.
			case Window.UNDO:
				controller.undo();
				break;
			
			// When the user wants to redo an action he undid.
			case Window.REDO:
				controller.redo();
				break;
				
			// When the user wants to zoom using the button.
			case Window.ZOOM:
				controller.getWindow().zoom();
				controller.getWindow().enableResetScaleButton();
				break;
			
			// When the user wants to unzoom using the button.
			case Window.UNZOOM:
				controller.getWindow().unZoom();
				controller.getWindow().enableResetScaleButton();
				break;
				
			// When the user wants to cancel the add or the move of a delivery when he has to select the previous delivery 
			// to place the added or the moved delivery in the chosen circuit.
			case Window.CANCEL:
				controller.cancelAdd();
				break;
				
			// When he user wants to reset the moves, zooms and unzooms he did on the graphic view and go to the initial display
			case Window.RESET_SCALE:
				controller.getWindow().resetScale();
				controller.getWindow().disableResetScaleButton();
				controller.getWindow().drawCircuits();
				break;
				
			// When the user wants to generate files which contain the road map for the delivery men.
			case Window.GENERATE_ROADMAP:
				String folderPath = controller.getWindow().getFolderPath();
				if (folderPath != "")
					Serializer.serializer(folderPath, controller.getCircuitManagement());
				break;
				
			// When the user wants to move a delivery after selected it.
			case Window.MOVE_DELIVERY: 
				controller.moveDelivery();
				break;
				
			// When the user wants to delete a delivery after selected it.
			case Window.DELETE_DELIVERY: 
				controller.deleteDelivery(); 
				break;
				
			// When the user wants to go up on the map.
			case Window.UP:
				// Do an upward shift of 50 pixels.
				controller.getWindow().verticalShift(-50);
				controller.getWindow().enableResetScaleButton();
				break;
			
			// When the user wants to go down on the map.
			case Window.DOWN:
				// Do a downward shift of 50 pixels.
				controller.getWindow().verticalShift(50);
				controller.getWindow().enableResetScaleButton();
				break;
				
			// When the user wants to go right on the map.
			case Window.RIGHT:
				// Do a right shift of 50 pixels.
				controller.getWindow().horizontalShift(50);
				controller.getWindow().enableResetScaleButton();
				break;
			
			// When the user wants to go left on the map.
			case Window.LEFT:
				// Do a left shift of 50 pixels.
				controller.getWindow().horizontalShift(-50);
				controller.getWindow().enableResetScaleButton();
				break;
		}
	}
}
