package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
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
	
	/** The init state. */
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
	
	/** The show pop up. */
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
		commandsList = new CommandsList();
	}
	
	/**
	 * Sets the current state.
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

	/*public void setWindow(Window window) {
		this.window = window;
	}*/

	/**
	 * Load map.
	 *
	 * @param filename the filename
	 */
	public void loadMap(String filename) {
		currentState.loadMap(this, window, filename, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Load delivery offer.
	 *
	 * @param filename the filename
	 */
	public void loadDeliveryOffer(String filename) {
		currentState.loadDeliveryOffer(this, window, filename, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Calculate circuits.
	 *
	 * @param nbDeliveryMan the nb delivery man
	 */
	public void calculateCircuits(int nbDeliveryMan) {
		currentState.calculateCircuits(this, window, nbDeliveryMan, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Undo.
	 */
	public void undo() {
		currentState.undo(this, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Redo.
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
	 * Delete delivery.
	 */
	public void deleteDelivery() {
		currentState.deleteDelivery(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Move delivery.
	 */
	public void moveDelivery() {
		currentState.moveDelivery(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate add.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateAdd() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel add.
	 */
	public void cancelAdd() {
		
		currentState.cancel(this, window);
		window.requestFocusInWindow();
		
	}
	
	
	
	/**
	 * Validate move.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateMove() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel move.
	 */
	public void cancelMove() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	
	
	/**
	 * Validate delete.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateDelete() throws ManagementException {
		currentState.validate(this, window,commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel delete.
	 */
	public void cancelDelete() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate continue.
	 *
	 * @throws ManagementException the management exception
	 */
	public void validateContinue() throws ManagementException {
		currentState.validate(this, window, commandsList);
		window.requestFocusInWindow();
	}
	
	
	/**
	 * Right click.
	 */
	public void rightClick() {
		currentState.rightClick(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Left click.
	 *
	 * @param point the point
	 */
	public void leftClick(Point point){
		currentState.leftClick(this, window, point);
		window.requestFocusInWindow();
		
		//Point point = graphicView.pointToLatLong(point);
		//Node node = graphicView.pointToNode(point);
		//Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
		//window.nodeSelected(isDelivery);
		
		//si tournees deja calculees
		// window.circuitSelected(isDelivery);
	}
	
	/**
	 * Tree delivery selected.
	 *
	 * @param deliverySelected the delivery selected
	 */
	public void treeDeliverySelected(Delivery deliverySelected) {
		
		currentState.treeDeliverySelected(this, window, deliverySelected, commandsList);
		
	}
	
	/**
	 * Mouse moved.
	 *
	 * @param point the point
	 */
	public void mouseMoved( Point point) {
		currentState.mouseMoved( this, window, point);
		window.requestFocusInWindow();
		
		//Point point = graphicView.pointToLatLong(point);
		//Node node = graphicView.pointToNode(point);
		//Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
		//window.nodeHover(isDelivery);
	}
	
	/**
	 * Continue calculation.
	 *
	 * @param keepCalculating the keep calculating
	 */
	public void continueCalculation(boolean keepCalculating) {
		currentState.continueCalculation(this, window, keepCalculating);
		window.requestFocusInWindow();
	}
	
	/**
	 * Validate duration.
	 *
	 * @param duration the duration
	 */
	public void validateDuration (int duration) {
		currentState.validateDuration(this, window, duration, commandsList);
		window.requestFocusInWindow();
	}
	
	/**
	 * Cancel duration.
	 */
	public void cancelDuration() {
		currentState.cancel(this, window);
		window.requestFocusInWindow();
	}
	
	/**
	 * Gets the show pop up.
	 *
	 * @return the show pop up
	 */
	public boolean getShowPopUp() {
		return showPopUp;
	}
	
	/**
	 * Sets the show pop up.
	 *
	 * @param popUp the new show pop up
	 */
	public void setShowPopUp(boolean popUp) {
		showPopUp = popUp;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return currentState.toString();
	}
}
