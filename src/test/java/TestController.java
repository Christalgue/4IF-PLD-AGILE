package test.java;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;

public class TestController {
	
	public static void main (String [] args) {
		CircuitManagement cm = new CircuitManagement();
		Controller c = new Controller (cm);
		c.loadMap("resources/xml/grandPlan.xml");
		System.out.println("Etat desir�  : mapLoaded, etat obtenu :" + c.currentState.getClass().getName());
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		System.out.println("Etat desir�  : deliveryLoaded, etat obtenu :" + c.currentState.getClass().getName()); 
		//c.calculateCircuits(1);
		//System.out.println("Etat desir�  : calc, etat obtenu :" + c.currentState.getClass().getName()); 
		
		
	}

}
