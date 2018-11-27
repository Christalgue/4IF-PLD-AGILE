package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.DijkstraException;
import main.java.exception.LoadMapException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestAtomicPath {

	@Test
	void test() {
		try {
			Map map = new Map("resources/tests/AtomicPath/xml/plan.xml");
			List<Delivery> deliveries = Deserializer.loadDeliveries("resources/tests/AtomicPath/xml/delivery.xml", map);
			
			String s12="Route :\n1 => 2 (3.0)";
			String s13="Route :\n1 => 2 (3.0)\n2 => 3 (5.0)";
			String s14="Route :\n1 => 2 (3.0)\n2 => 3 (5.0)\n3 => 4 (1.0)";
			String s15="Route :\n1 => 5 (5.0)";
			
			HashMap<Delivery,AtomicPath> myPaths = map.findShortestPath(deliveries.get(0), deliveries);
			assertTrue(myPaths.get(deliveries.get(1)).toString().contains(s12),"Wrong path 1-2 : "+myPaths.get(deliveries.get(1)).toString());
			assertTrue(myPaths.get(deliveries.get(2)).toString().contains(s13),"Wrong path 1-3 : "+myPaths.get(deliveries.get(2)).toString());
			assertTrue(myPaths.get(deliveries.get(3)).toString().contains(s14),"Wrong path 1-4 : "+myPaths.get(deliveries.get(3)).toString());
			assertTrue(myPaths.get(deliveries.get(4)).toString().contains(s15),"Wrong path 1-5 : "+myPaths.get(deliveries.get(4)).toString());
			assertTrue(myPaths.get(deliveries.get(1)).getLength()==3,"Wrong length 1-2 : "+myPaths.get(deliveries.get(1)).getLength());
			assertTrue(myPaths.get(deliveries.get(2)).getLength()==8,"Wrong length 1-3 : "+myPaths.get(deliveries.get(2)).getLength());
			assertTrue(myPaths.get(deliveries.get(3)).getLength()==9,"Wrong length 1-4 : "+myPaths.get(deliveries.get(3)).getLength());
			assertTrue(myPaths.get(deliveries.get(4)).getLength()==5,"Wrong length 1-5 : "+myPaths.get(deliveries.get(4)).getLength());
			
			
			
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer");
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException, report to TestDeserializer");
		} catch (SAXException e) {
			fail("SAXException, report to TestDeserializer");
		} catch (IOException e) {
			fail("IOException, report to TestDeserializer");
		} catch (XMLException e) {
			fail("XMLException, report to TestDeserializer");
		} catch (DijkstraException e) {
			fail(e.getMessage());
		}
	}

}
