package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
import main.java.utils.Serializer;

class TestSerializer {

	@Test
	void testSerializer() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/xml/moyenPlan.xml");
			circuitManager.loadDeliveryList("resources/xml/dl-moyen-9.xml");
			circuitManager.calculateCircuits(2, false);
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			
			String path = "../../sorties";
			
			Serializer.serializer(path, circuitManager);
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException, report to TestDeserializer : "+e.getMessage());
		} catch (MapNotChargedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DijkstraException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoRepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TSPLimitTimeReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
