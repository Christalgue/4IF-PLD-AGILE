package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class Controller {
	
	public CircuitManagement circuitManagement;
	private Window window;
	public State currentState; // TURN BACK TO PRIVATE
	// Instances associated to each possible state of the controller
	protected final InitialState initState = new InitialState();
	protected final CalcState  calcState = new  CalcState();
	protected final DeliveryAddedState deliveryAddedState = new DeliveryAddedState();
	protected final DeliveryDeletedState deliveryDeletedState = new DeliveryDeletedState();
	protected final DeliveryLoadedState deliveryLoadedState = new DeliveryLoadedState();
	protected final DeliverySelectedState deliverySelectedState  = new DeliverySelectedState ();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final DeliveryMovedState deliveryMovedState = new DeliveryMovedState();
	protected final CalculatingState calculatingState = new CalculatingState();
	protected final DurationChoiceState durationChoiceState = new DurationChoiceState ();
	protected final PreviousDeliverySelectedState previousDeliverySelectedState = new PreviousDeliverySelectedState ();
	protected final SelectedPreviousMovedState selectedPreviousMovedState = new SelectedPreviousMovedState (); 
	protected final NodeSelectedState nodeSelectedState = new NodeSelectedState();
	
	public Controller(CircuitManagement circuitManagement) {
		this.circuitManagement = circuitManagement;
		currentState = initState;
		this.window = new Window(this);
	}
	
	protected void setCurrentState(State state){
		currentState = state;
	}
	
	public CircuitManagement getCircuitManagement() {
		return circuitManagement;
	}
	
	public Window getWindow() {
		return window;
	}

	/*public void setWindow(Window window) {
		this.window = window;
	}*/

	public void loadMap(String filename) {
		currentState.loadMap(this, window, filename);
		
	}
	
	public void loadDeliveryOffer(String filename) {
		currentState.loadDeliveryOffer(this, window, filename);
	}
	
	public void calculateCircuits(int nbDeliveryMan) {
		currentState.calculateCircuits(this, window, nbDeliveryMan);
	}
	
	/*public void undo() {
		
	}*/
	
	public void addDelivery() {
		currentState.addDelivery(this, window);
	}
	
	
	public void deleteDelivery() {
		currentState.deleteDelivery(this, window);
	}
	
	public void moveDelivery() {
		currentState.moveDelivery(this, window);
	}
	
	public void validateAdd() throws ManagementException {
		currentState.validate(this, window);
	}
	
	public void cancelAdd() {
		
		currentState.cancel(this, window);
		
	}
	
	
	
	public void validateMove() throws ManagementException {
		currentState.validate(this, window);
	}
	
	public void cancelMove() {
		currentState.cancel(this, window);
	}
	
	
	
	public void validateDelete() throws ManagementException {
		currentState.validate(this, window);
	}
	
	public void cancelDelete() {
		currentState.cancel(this, window);
	}
	
	public void rightClick() {
		currentState.rightClick(this, window);
	}
	/**
	 * 
	 */
	public void leftClick(Point point){
		currentState.leftClick(this, window, point);
	}
	
	public void continueCalculation(boolean keepCalculating) {
		currentState.continueCalculation(this, window, keepCalculating);
	}
	
	public void validateDuration (int duration) {
		currentState.validateDuration(this, window, duration);
	}
	
	public void cancelDuration() {
		currentState.cancel(this, window);
	}

	/**
	 * Method called by window after the mouse has moved in the graphical view of the plan
	 * Precondition : p != null
	 * @param p = coordinates in the plan of the mouse position
	 */
	public void movedMouse() {
		currentState.movedMouse(this, window);
	}
}
