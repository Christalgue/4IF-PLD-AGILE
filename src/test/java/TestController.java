package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.entity.Point;

class TestController {

	@Test
	/**
	 * 
	 */
	void testChangeState() {
		CircuitManagement cm = new CircuitManagement();
		Controller c = new Controller (cm);
		Point newDelivery = new Point(4.877902,45.748894);
		Point previousDelivery = new Point(4.8593636,45.753654);
		Point delivery = new Point(4.8756394,45.759773);
		
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
		c.calculateCircuits(1);
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.currentState.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.currentState.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.currentState.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.currentState.toString()+">");
		
		c.calculateCircuits(1);
		c.leftClick(newDelivery);
		assertTrue(c.currentState.toString().contains("State : DurationChoiceState"),"Expected <State : DurationChoiceState>, got <"+c.currentState.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(newDelivery);
		c.validateDuration(20);
		assertTrue(c.currentState.toString().contains("State : PreviousDeliverySelectedState"),"Expected <State : PreviousDeliverySelectedState>, got <"+c.currentState.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(newDelivery);
		c.validateDuration(20);
		c.leftClick(previousDelivery);
		assertTrue(c.currentState.toString().contains("State : DeliveryAddedState"),"Expected <State : DeliveryAddedState>, got <"+c.currentState.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(newDelivery);
		c.validateDuration(20);
		c.leftClick(previousDelivery);
		c.addDelivery();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		assertTrue(c.currentState.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.currentState.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.currentState.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.currentState.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.currentState.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.currentState.toString()+">");
		
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.calculateCircuits(1);
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		c.leftClick(delivery);
		assertTrue(c.currentState.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(newDelivery);
		assertTrue(c.currentState.toString().contains("State : DurationChoiceState"),"Expected <State : DurationChoiceState>, got <"+c.currentState.toString()+">");
		
		c.cancelAdd();
		c.leftClick(delivery);
		c.deleteDelivery();
		assertTrue(c.currentState.toString().contains("State : DeletedChoiceState"),"Expected <State : DeletedChoiceState>, got <"+c.currentState.toString()+">");
		
		c.cancelDelete();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		c.deleteDelivery();
		c.validateDelete();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		c.moveDelivery();
		assertTrue(c.currentState.toString().contains("State : SelectedPreviousMovedState"),"Expected <State : SelectedPreviousMovedState>, got <"+c.currentState.toString()+">");
		
		c.cancelMove();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		c.moveDelivery();
		c.leftClick(previousDelivery);
		assertTrue(c.currentState.toString().contains("State : DeliveryMovedState"),"Expected <State : DeliveryMovedState>, got <"+c.currentState.toString()+">");
		
		c.cancelMove();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
		c.leftClick(delivery);
		c.moveDelivery();
		c.leftClick(previousDelivery);
		c.validateMove();
		assertTrue(c.currentState.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.currentState.toString()+">");
		
	}

}
