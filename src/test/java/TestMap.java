package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.java.entity.AtomicPath;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.DijkstraException;
import main.java.exception.ForgivableXMLException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestMap {

	@Test
	/**
	 * Test if the map find the shortest path from a node to another node
	 */
	void testFindShortestPath() {
		Map map = new Map();
		List<Delivery> deliveries;
		
		HashMap<Delivery,AtomicPath> shortPath;
		
		try {
			try {
				Deserializer.loadMap("resources/tests/Map/xml/plan_shortest_path.xml", map);
			} catch (ForgivableXMLException e) {}
			deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Map/xml/delivery_shortest_path.xml", map));

			shortPath = new HashMap<Delivery,AtomicPath>(map.findShortestPath(deliveries.get(0), deliveries));
			assertTrue(shortPath.containsKey(deliveries.get(1)),"Error shortPath does not contain 1-2");
			assertTrue(shortPath.containsKey(deliveries.get(2)),"Error shortPath does not contain 1-3");
			assertTrue(shortPath.containsKey(deliveries.get(3)),"Error shortPath does not contain 1-4");
			
			shortPath = new HashMap<Delivery,AtomicPath>(map.findShortestPath(deliveries.get(1), deliveries));
			assertTrue(shortPath.containsKey(deliveries.get(0)),"Error shortPath does not contain 2-1");
			assertTrue(shortPath.containsKey(deliveries.get(2)),"Error shortPath does not contain 2-3");
			assertTrue(shortPath.containsKey(deliveries.get(3)),"Error shortPath does not contain 2-4");
			
			shortPath = new HashMap<Delivery,AtomicPath>(map.findShortestPath(deliveries.get(2), deliveries));
			assertTrue(shortPath.containsKey(deliveries.get(0)),"Error shortPath does not contain 3-1");
			assertTrue(shortPath.containsKey(deliveries.get(1)),"Error shortPath does not contain 3-2");
			assertTrue(shortPath.containsKey(deliveries.get(3)),"Error shortPath does not contain 3-4");
			
			shortPath = new HashMap<Delivery,AtomicPath>(map.findShortestPath(deliveries.get(3), deliveries));
			assertTrue(shortPath.containsKey(deliveries.get(0)),"Error shortPath does not contain 4-1");
			assertTrue(shortPath.containsKey(deliveries.get(1)),"Error shortPath does not contain 4-2");
			assertTrue(shortPath.containsKey(deliveries.get(2)),"Error shortPath does not contain 4-3");
			
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException, report to TestDeserializer");
		} catch (SAXException e) {
			fail("SAXException, report to TestDeserializer");
		} catch (IOException e) {
			fail("IOException, report to TestDeserializer");
		} catch (XMLException e) {
			fail("XMLException, report to TestDeserializer");
		} catch (DijkstraException e) {
			fail("DijkstraException : "+e.getMessage());
		}
	}

	@Test
	/**
	 * Test if the method return the correct id of a node if we send its coordinates
	 */
	void testGetIdFromNode() {
		Map map = new Map();
		
		try {
			try {
				Deserializer.loadMap("resources/tests/Map/xml/plan_conforme.xml", map);
			} catch (ForgivableXMLException e) {}
			
			long id = map.getIdFromCorrespondingNode(1, 1);
			assertTrue(id==(long)1,"Error expected 1, got : "+id);
			
			id = map.getIdFromCorrespondingNode(2, 2);
			assertTrue(id==(long)2,"Error expected 2, got : "+id);
			
			id = map.getIdFromCorrespondingNode(3, 3);
			assertTrue(id==(long)3,"Error expected 3, got : "+id);
			
			id = map.getIdFromCorrespondingNode(4, 4);
			assertTrue(id==(long)4,"Error expected 4, got : "+id);
			
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException, report to TestDeserializer");
		} catch (SAXException e) {
			fail("SAXException, report to TestDeserializer");
		} catch (IOException e) {
			fail("IOException, report to TestDeserializer");
		} catch (XMLException e) {
			fail("XMLException, report to TestDeserializer");
		}
	}

}
