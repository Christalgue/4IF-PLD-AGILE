package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;

class TestController {

	@Test
	/**
	 * 
	 */
	void testChangeState() {
		CircuitManagement cm = new CircuitManagement();
		Controller c = new Controller (cm);
		assertTrue(c.currentState.toString().contains("State : InitialState"),"Expected <State : InitialState>, got <"+c.currentState.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.currentState.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.currentState.toString()+">");
		
		c.loadMap("resources/xml/grandPlan.xml");
		assertTrue(c.currentState.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.currentState.toString()+">");

		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.currentState.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.currentState.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.currentState.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.currentState.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.currentState.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.currentState.toString()+">");
	}

}
