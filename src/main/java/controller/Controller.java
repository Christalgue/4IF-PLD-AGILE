package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;
import main.java.view.Window;

public class Controller {
	
	public CircuitManagement circuitManagement;
	private Window window;
	public State currentState; // TURN BACK TO PRIVATE
	private CommandsList commandsList;
	
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
	protected final NodeSelectedBeforeCalcState nodeSelectedBeforeCalcState = new NodeSelectedBeforeCalcState ();
	protected final DeliverySelectedBeforeCalcState deliverySelectedBeforeCalcState = new DeliverySelectedBeforeCalcState ();
	protected final DurationChoiceBeforeCalcState durationChoiceBeforeCalcState = new DurationChoiceBeforeCalcState ();
	protected final DeliveryDeletedBeforeCalcState deliveryDeletedBeforeCalcState = new DeliveryDeletedBeforeCalcState();
	
	protected boolean showPopUp;
	
	public Controller(CircuitManagement circuitManagement) {
		this.circuitManagement = circuitManagement;
		currentState = initState;
		this.window = new Window(this);
		showPopUp = true;
		commandsList = new CommandsList();
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
	
	public void undo() {
		commandsList.undo();
	}
	
	public void redo() {
		commandsList.redo();
	}
	
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
		currentState.validate(this, window, commandsList);
	}
	
	public void cancelAdd() {
		
		currentState.cancel(this, window);
		
	}
	
	
	
	public void validateMove() throws ManagementException {
		currentState.validate(this, window, commandsList);
	}
	
	public void cancelMove() {
		currentState.cancel(this, window);
	}
	
	
	
	public void validateDelete() throws ManagementException {
		currentState.validate(this, window,commandsList);
	}
	
	public void cancelDelete() {
		currentState.cancel(this, window);
	}
	
	public void validateContinue() throws ManagementException {
		currentState.validate(this, window, commandsList);
	}
	
	
	public void rightClick() {
		currentState.rightClick(this, window);
	}
	/**
	 * 
	 */
	public void leftClick(Point point){
		currentState.leftClick(this, window, point);
		
		//Point point = graphicView.pointToLatLong(point);
		//Node node = graphicView.pointToNode(point);
		//Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
		//window.nodeSelected(isDelivery);
		
		//si tournees deja calculees
		// window.circuitSelected(isDelivery);
	}
	
	public void treeDeliverySelected(Delivery deliverySelected) {
		
	}
	
	public void mouseMoved( Point point) {
		currentState.mouseMoved( this, window, point);
		
		//Point point = graphicView.pointToLatLong(point);
		//Node node = graphicView.pointToNode(point);
		//Delivery isDelivery = controller.getCircuitManagement().isDelivery(node);
		//window.nodeHover(isDelivery);
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
	
	public boolean getShowPopUp() {
		return showPopUp;
	}
	
	public void setShowPopUp(boolean popUp) {
		showPopUp = popUp;
	}
}
