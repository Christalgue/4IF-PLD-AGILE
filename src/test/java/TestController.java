package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.controller.Controller;
import main.java.entity.CircuitManagement;
import main.java.entity.Node;
import main.java.entity.Point;
import main.java.exception.ManagementException;

class TestController {

	@Test
	/**
	 * 
	 */
	void testChangeState() {
		CircuitManagement cm = new CircuitManagement();
		Controller c = new Controller (cm);
		c.setShowPopUp(false);
		
		Point newDelivery = new Point(4.877902,45.748894);
		Point previousDelivery = new Point(4.8593636,45.753654);
		Point delivery = new Point(4.8756394,45.759773);
		
		assertTrue(c.toString().contains("State : InitialState"),"Expected <State : InitialState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/grandPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");

		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-6.xml");
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedBeforeCalcState"),"Expected <State : NodeSelectedBeforeCalcState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/grandPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");

		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.leftClick(newDelivery);
		c.leftClick(delivery);
		assertTrue(c.toString().contains("State : DeliverySelectedBeforeCalcState"),"Expected <State : DeliverySelectedBeforeCalcState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedBeforeCalcState"),"Expected <State : NodeSelectedBeforeCalcState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedBeforeCalcState"),"Expected <State : DeliverySelectedBeforeCalcState>, got <"+c.toString()+">");

		c.leftClick(newDelivery);
		c.calculateCircuits(1);
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");

		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.addDelivery();
		assertTrue(c.toString().contains("State : DurationChoiceBeforeCalcState"),"Expected <State : DurationChoiceBeforeCalcState>, got <"+c.toString()+">");
		
		c.cancelDuration();
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.addDelivery();
		c.validateDuration(20);
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		assertTrue(c.toString().contains("State : DeliverySelectedBeforeCalcState"),"Expected <State : DeliverySelectedBeforeCalcState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");

		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedBeforeCalcState"),"Expected <State : DeliverySelectedBeforeCalcState>, got <"+c.toString()+">");
				
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedBeforeCalcState"),"Expected <State : DeliverySelectedBeforeCalcState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.leftClick(delivery);
		c.calculateCircuits(1);
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.leftClick(delivery);
		c.deleteDelivery();
		assertTrue(c.toString().contains("State : DeliveryDeletedBeforeCalcState"),"Expected <State : DeliveryDeletedBeforeCalcState>, got <"+c.toString()+">");
		
		c.cancelDelete();
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.deleteDelivery();
		try {
			c.validateDelete();
		} catch (ManagementException e1) {
			fail("validateDelete in DeliveryDeletedBeforeCalcState");
		}
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.calculateCircuits(1);
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedState"),"Expected <State : NodeSelectedState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(newDelivery);
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.calculateCircuits(1);
		c.leftClick(newDelivery);
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedState"),"Expected <State : NodeSelectedState>, got <"+c.toString()+">");
		
		c.calculateCircuits(1);
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.leftClick(delivery);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedState"),"Expected <State : NodeSelectedState>, got <"+c.toString()+">");
		
		c.addDelivery();
		assertTrue(c.toString().contains("State : DurationChoiceState"),"Expected <State : DurationChoiceState>, got <"+c.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.addDelivery();
		c.validateDuration(20);
		assertTrue(c.toString().contains("State : PreviousDeliverySelectedState"),"Expected <State : PreviousDeliverySelectedState>, got <"+c.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.addDelivery();
		c.validateDuration(20);
		c.leftClick(previousDelivery);
		assertTrue(c.toString().contains("State : DeliveryAddedState"),"Expected <State : DeliveryAddedState>, got <"+c.toString()+">");
		
		c.cancelAdd();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		c.addDelivery();
		c.validateDuration(20);
		c.leftClick(previousDelivery);
		try {
			c.validateAdd();
		} catch (ManagementException e) {
			fail("Management exception for add : "+e.getMessage());
		}
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
		
		c.loadMap("resources/xml/petitPlan.xml");
		assertTrue(c.toString().contains("State : MapLoadedState"),"Expected <State : MapLoadedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		assertTrue(c.toString().contains("State : DeliveryLoadedState"),"Expected <State : DeliveryLoadedState>, got <"+c.toString()+">");
		
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.calculateCircuits(1);
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.leftClick(delivery);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
		
		c.leftClick(newDelivery);
		assertTrue(c.toString().contains("State : NodeSelectedState"),"Expected <State : NodeSelectedState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.deleteDelivery();
		assertTrue(c.toString().contains("State : DeliveryDeletedState"),"Expected <State : DeliveryDeletedState>, got <"+c.toString()+">");
		
		c.cancelDelete();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.deleteDelivery();
		try {
			c.validateDelete();
		} catch (ManagementException e) {
			fail("Management exception for delete : "+e.getMessage());
		}
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.moveDelivery();
		assertTrue(c.toString().contains("State : SelectedPreviousMovedState"),"Expected <State : SelectedPreviousMovedState>, got <"+c.toString()+">");
		
		c.cancelMove();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.moveDelivery();
		c.leftClick(previousDelivery);
		assertTrue(c.toString().contains("State : DeliveryMovedState"),"Expected <State : DeliveryMovedState>, got <"+c.toString()+">");
		
		c.cancelMove();
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");
		
		c.leftClick(delivery);
		c.moveDelivery();
		c.leftClick(previousDelivery);
		try {
			c.validateMove();
		} catch (ManagementException e) {
			fail("Management exception for move : "+e.getMessage());
		}
		assertTrue(c.toString().contains("State : CalcState"),"Expected <State : CalcState>, got <"+c.toString()+">");	
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(delivery);
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
		
		c.loadDeliveryOffer("resources/xml/dl-petit-3.xml");
		c.calculateCircuits(1);
		c.leftClick(newDelivery);
		c.treeDeliverySelected(cm.getDeliveryList().get(1),-1);
		assertTrue(c.toString().contains("State : DeliverySelectedState"),"Expected <State : DeliverySelectedState>, got <"+c.toString()+">");
	}

}
