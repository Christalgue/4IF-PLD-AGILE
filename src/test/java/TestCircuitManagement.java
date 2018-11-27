package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javafx.util.Pair;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.LoadMapException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestCircuitManagement {

	@Test
	void testCluster() {
		
		try {
			Map map = new Map("resources/tests/Cluster/xml/plan.xml");
			List<Delivery> arrivalDeliveries = Deserializer.loadDeliveries("resources/tests/Cluster/xml/delivery.xml", map);
			
			CircuitManagement CircuitManager = new CircuitManagement();
			CircuitManager.setDeliveryList(arrivalDeliveries);
			CircuitManager.setNbDeliveryMan(2);
	    	
	    	List<ArrayList<Delivery>> distribution = CircuitManager.KmeansClustering();
	    	int nbDeliveries1 = distribution.get(1).size();
	    	int nbDeliveries2 = distribution.get(2).size();
	    	
	    	assertTrue(nbDeliveries1<=nbDeliveries2+1 || nbDeliveries1>=nbDeliveries1-1, "Error, bad repartition of deliveries => "+nbDeliveries1+" vs "+nbDeliveries2);

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException, report to TestDeserializer");
		} catch (SAXException e) {
			fail("SAXException, report to TestDeserializer");
		} catch (IOException e) {
			fail("IOException, report to TestDeserializer");
		} catch (XMLException e) {
			fail("XMLException, report to TestDeserializer : "+e.getMessage());
		}
	}

}