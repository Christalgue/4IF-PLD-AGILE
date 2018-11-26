package main.java.controller;

import main.java.entity.CircuitManagement;
import main.java.view.Window;

public class Controller {
	
	private CircuitManagement circuitManagement;
	private Window window;
	private State currentState;
	// Instances associees a chaque etat possible du controleur
	protected final InitialState initState = new InitialState();
	protected final CalcState  calcState = new  CalcState();
	protected final DeliveryAddedState deliveryAddedState = new DeliveryAddedState();
	protected final DeliveryDeletedState deliveryDeletedState = new DeliveryDeletedState();
	protected final DeliveryLoadedState deliveryLoadedState = new DeliveryLoadedState();
	protected final DeliverySelectedState deliverySelectedState  = new DeliverySelectedState ();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final DeliveryMovedState deliveryMovedState = new DeliveryMovedState();

	public Controller(CircuitManagement circuitManagement) {
		this.circuitManagement = circuitManagement;
		currentState = initState;
		//this.window = new Window(circuitManagement, this);
	}
	
	protected void setCurrentState(State state){
		currentState = state;
	}
	public void loadMap(String filename) {
		currentState.loadMap(this, window, filename);
		
	}
	
	public void loadDeliveryOffer(String filename) {
		currentState.loadDeliveryOffer(this, window, filename);
	}
	
	public void calculateCircuits() {
		currentState.calculateCircuits(this, window);
	}
	
	/*public void undo() {
		
	}*/
	
	public void addDelivery() {
		currentState.addDelivery(this, window);
	}
	
	public void validateAdd() {
		currentState.validate(this, window);
	}
	
	public void cancelAdd() {
		
		currentState.cancel(this, window);
		
	}
	
	public void moveDelivery () {
		currentState.moveDelivery(this, window);
	}
	
	public void validateMove() {
		currentState.validate(this, window);
	}
	
	public void cancelMove() {
		currentState.cancel(this, window);
	}
	
	public void deleteDelivery() {
		currentState.deleteDelivery(this, window);
	}
	
	public void validateDelete() {
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
	public void leftClick(){
		currentState.leftClick(this, window);
	}

	/**
	 * Methode appelee par fenetre apres un deplacement de la souris sur la vue graphique du plan
	 * Precondition : p != null
	 * @param p = coordonnees du plan correspondant a la position de la souris
	 */
	public void movedMouse() {
		currentState.movedMouse(this, window);
	}
}
