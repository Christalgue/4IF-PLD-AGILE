package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;


/**
 * The Class Controller.
 * It's the class which controls the application, by changing its currentState depending on
 * the actions of the user, with a set of methods the View can call.
 */
public class Controller {
	
	/** The circuit management. */
	public CircuitManagement circuitManagement;
	
	/** The window. */
	private Window window;
	
	/** The current state. */
	private State currentState; // TURN BACK TO PRIVATE
	
	/** The commands list. */
	private CommandsList commandsList;
	
	/** The initial state. */
	
	// Instances associated to each possible state of the controller
	protected final InitialState initState = new InitialState();
	
	/** The calc state. */
	protected final CalcState  calcState = new  CalcState();
	
	/** The delivery added state. */
	protected final DeliveryAddedState deliveryAddedState = new DeliveryAddedState();
	
	/** The delivery deleted state. */
	protected final DeliveryDeletedState deliveryDeletedState = new DeliveryDeletedState();
	
	/** The delivery loaded state. */
	protected final DeliveryLoadedState deliveryLoadedState = new DeliveryLoadedState();
	
	/** The delivery selected state. */
	protected final DeliverySelectedState deliverySelectedState  = new DeliverySelectedState ();
	
	/** The map loaded state. */
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	
	/** The delivery moved state. */
	protected final DeliveryMovedState deliveryMovedState = new DeliveryMovedState();
	
	/** The calculating state. */
	protected final CalculatingState calculatingState = new CalculatingState();
	
	/** The duration choice state. */
	protected final DurationChoiceState durationChoiceState = new DurationChoiceState ();
	
	/** The previous delivery selected state. */
	protected final PreviousDeliverySelectedState previousDeliverySelectedState = new PreviousDeliverySelectedState ();
	
	/** The selected previous moved state. */
	protected final SelectedPreviousMovedState selectedPreviousMovedState = new SelectedPreviousMovedState (); 
	
	/** The node selected state. */
	protected final NodeSelectedState nodeSelectedState = new NodeSelectedState();
	
	/** The node selected before calc state. */
	protected final NodeSelectedBeforeCalcState nodeSelectedBeforeCalcState = new NodeSelectedBeforeCalcState ();
	
	/** The delivery selected before calc state. */
	protected final DeliverySelectedBeforeCalcState deliverySelectedBeforeCalcState = new DeliverySelectedBeforeCalcState ();
	
	/** The duration choice before calc state. */
	protected final DurationChoiceBeforeCalcState durationChoiceBeforeCalcState = new DurationChoiceBeforeCalcState ();
	
	/** The delivery deleted before calc state. */
	protected final DeliveryDeletedBeforeCalcState deliveryDeletedBeforeCalcState = new DeliveryDeletedBeforeCalcState();
	
	/** The show pop up boolean . */
	protected boolean showPopUp;
	
	/**
	 * Instantiates a new controller.
	 *
	 * @param circuitManagement the circuit management
	 */
	public Controller(CircuitManagement circuitManagement) {
		this.circuitManagement = circuitManagement;
		currentState = initState;
		this.window = new Window(this);
		showPopUp = true;
		commandsList = new CommandsList(this.window);
	}
	
	/**
	 * Sets the current state of the controller.
	 *
	 * @param state the new current state
	 */
	protected void setCurrentState(State state){
		currentState = state;
	}
	
	/**
	 * Gets the circuit management.
	 *
	 * @return the circuit management
	 */
	public CircuitManagement getCircuitManagement() {
		return circuitManagement;
	}
	
	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * Load the map.
	 *
	 * @param filename the filename
	 */
	public void loadMap(String filename) {
		currentState.loadMap(this, window, filename, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Load the delivery offer.
	 *
	 * @param filename the filename
	 */
	public void loadDeliveryOffer(String filename) {
		currentState.loadDeliveryOffer(this, window, filename, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Calculate the circuits.
	 *
	 * @param nbDeliveryMan the number of delivery man
	 */
	public void calculateCircuits(int nbDeliveryMan) {
		currentState.calculateCircuits(this, window, nbDeliveryMan, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Undo the previous action.
	 */
	public void undo() {
		currentState.undo(this, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Redo the previous action.
	 */
	public void redo() {
		currentState.redo(this, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Adds the delivery.
	 */
	public void addDelivery() {
		currentState.addDelivery(this, window);
		window.requestFocusInWindow();
	}
	
	
	/**
	 * Delete the delivery.
	 */
	public void deleteDelivery() {
		currentState.deleteDelivery(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Move the delivery.
	 */
	public void moveDelivery() {
		currentState.moveDelivery(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate the addition of the delivery.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateAdd() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel the addition of the delivery.
	 */
	public void cancelAdd() {
		
		currentState.cancel(this, window);
		window.requestFocusInWindow();
		
	}
	
	
	
	/**
	 * Validate the move of the delivery.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateMove() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel the move of the delivery.
	 */
	public void cancelMove() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	
	
	/**
	 * Validate the removal of the delivery.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateDelete() throws ManagementException {
		currentState.validate(this, window,commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel the removal of the delivery.
	 */
	public void cancelDelete() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate the continuation of the circuits calculation.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateContinue() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	
	/**
	 * Method called when the user makes a right click.
	 */
	public void rightClick() {
		currentState.rightClick(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Method called when the user makes a left click.
	 *
	 * @param point the point clicked
	 */
	public void leftClick(Point point){
		currentState.leftClick(this, window, point);
		window.requestFocusInWindow();
	}
	
	/**
	 * Method called when a node from the deliveries tree is selected.
	 *
	 * @param deliverySelected the delivery selected
	 */
	public void treeDeliverySelected(Delivery deliverySelected) {
		
		currentState.treeDeliverySelected(this, window, deliverySelected, commandsList);
		
	}
	
	/**
	 * Method called when the mouse is moved.
	 *
	 * @param point the point 
	 */
	public void mouseMoved( Point point) {
		currentState.mouseMoved( this, window, point);
		window.requestFocusInWindow();
	}
	
	/**
	 * Continue the circuits calculation.
	 *
	 * @param keepCalculating the boolean which indicates if the program must continue the circuits calculation
	 */
	public void continueCalculation(boolean keepCalculating) {
		currentState.continueCalculation(this, window, keepCalculating);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate the duration of a delivery.
	 *
	 * @param duration the duration of the delivery
	 */
	public void validateDuration (int duration) {
		currentState.validateDuration(this, window, duration, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel the duration of a delivery.
	 */
	public void cancelDuration() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Shows the pop up.
	 *
	 * @return the pop up
	 */
	public boolean getShowPopUp() {
		return showPopUp;
	}
	
	/**
	 * Sets the shown pop up.
	 *
	 * @param popUp the new shown pop up
	 */
	public void setShowPopUp(boolean popUp) {
		showPopUp = popUp;
	}
	
	/**
	 * Display the current state of the controller
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return currentState.toString();
	}
}
