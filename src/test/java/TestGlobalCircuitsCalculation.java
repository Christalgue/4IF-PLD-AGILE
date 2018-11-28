package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.ClusteringException;
import main.java.exception.DeliveryListNotCharged;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.utils.Deserializer;

class TestGlobalCircuitsCalculation {

	@Test
	void testCalculateCircuits() {
		try{			
			CircuitManagement CircuitManager = new CircuitManagement();
			CircuitManager.loadMap("resources/xml/grandPlan.xml");
			CircuitManager.loadDeliveryList("resources/xml/dl-grand-20.xml");
			//CircuitManager.loadMap("resources/tests/Global/xml/plan.xml");
			//CircuitManager.loadDeliveryList("resources/tests/Global/xml/delivery.xml");
			
			CircuitManager.calculateCircuits(3, false);
			
			
			for (Circuit c : CircuitManager.getCircuitsList()) {
				System.out.println("");
				System.out.println("Tournee");
				for (Delivery d : c.getDeliveryList()) {
					System.out.println("Livraison : "+d.toString());
				}
				for (AtomicPath a : c.getPath()) {
					for (Bow b : a.getPath()) {
						System.out.println("Route : "+b.getStartNode().toString()+"   ====>   "+b.getEndNode().toString());
					}
				}
				System.out.println("");
			}
			
		} catch (MapNotChargedException e) {
			e.printStackTrace();
		} catch (DeliveryListNotCharged e) {
			e.printStackTrace();
		} catch (ClusteringException e) {
			e.printStackTrace();
		} catch (DijkstraException e) {
			e.printStackTrace();
		} catch (NoRepositoryException e) {
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			e.printStackTrace();
		} catch (LoadMapException e) {
			e.printStackTrace();
		}
	}

}
