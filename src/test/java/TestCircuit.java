package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.java.entity.Circuit;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.LoadMapException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestCircuit {

	@Test
	void testAddDelivery() {
		fail("Not yet implemented");
	}
	
	@Test
	void testAddAtomicPath() {
		fail("Not yet implemented");
	}
	
	@Test
	void testRemoveDelivery() {
		fail("Not yet implemented");
	}
	
	@Test
	void testRemoveAtomicPath() {
		fail("Not yet implemented");
	}
	
	@Test
	void testNodeInCircuit() {
		fail("TODO");
		Map map;
		try {
			map = new Map("resources/tests/Circuit/xml/plan.xml");
			List<Delivery> deliveries = Deserializer.loadDeliveries("resources/tests/Circuit/xml/delivery.xml", map);
			
			Circuit c = new Circuit();
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer "+e.getMessage());
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException, report to TestDeserializer "+e.getMessage());
		} catch (SAXException e) {
			fail("SAXException, report to TestDeserializer "+e.getMessage());
		} catch (IOException e) {
			fail("IOException, report to TestDeserializer "+e.getMessage());
		} catch (XMLException e) {
			fail("XMLException, report to TestDeserializer "+e.getMessage());
		} 
		
	}

}
