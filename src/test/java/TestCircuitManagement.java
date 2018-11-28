package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javafx.util.Pair;
import main.java.entity.Bow;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.exception.ClusteringException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestCircuitManagement {

	@Test
	void testLoadMap() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_simple.xml");
			
			Map map = circuitManager.getCurrentMap();
			
			assertTrue(map.getNodeMap().containsKey((long)1),"Nodes do not contain key 1");
			assertTrue(map.getNodeMap().containsKey((long)2),"Nodes do not contain key 2");
			assertTrue(map.getNodeMap().get((long)1).getId()==1,"wrong ID (1) : "+map.getNodeMap().get((long)1).getId());
			assertTrue(map.getNodeMap().get((long)1).getLatitude()==45.75406,"wrong latitude (45.75406) : "+map.getNodeMap().get((long)1).getLatitude());
			assertTrue(map.getNodeMap().get((long)1).getLongitude()==4.857418,"wrong longitude (4.857418) : "+map.getNodeMap().get((long)1).getLongitude());
			assertTrue(map.getNodeMap().get((long)2).getId()==2,"wrong ID (2) : "+map.getNodeMap().get((long)2).getId());
			assertTrue(map.getNodeMap().get((long)2).getLatitude()==45.750404,"wrong latitude (45.750404) : "+map.getNodeMap().get((long)2).getLatitude());
			assertTrue(map.getNodeMap().get((long)2).getLongitude()==4.8744674,"wrong longitude (4.8744674) : "+map.getNodeMap().get((long)2).getLongitude());
			assertTrue(map.getBowMap().containsKey((long)1),"Bows do not contain key");
			Bow first = map.getBowMap().get((long)1).iterator().next();
			assertTrue(first.getStartNode().getId()==1,"wrong start node (1) : "+first.getStartNode().getId());
			assertTrue(first.getEndNode().getId()==2,"wrong end node (2) : "+first.getEndNode().getId());
			assertTrue(first.getLength()==79.801414,"wrong length (79.801414) : "+first.getLength());
			assertTrue(first.getStreetName().contains("Rue Edouard Aynard"),"wrong street name (Rue Edouard Aynard) : "+first.getStreetName());
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		}
	}
	
	@Test
	void testLoadDeliveries() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_simple.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_simple.xml");
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			
			int UTC = TimeZone.getDefault().getRawOffset();
			assertTrue(deliveries.get(0).getClass().getName().contains("main.java.entity.Repository"),"8) First delivery is not repository");
			assertTrue(deliveries.get(0).getDuration()==0,"8) Wrong duration (0)"+deliveries.get(0).getDuration());
			assertTrue(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC==28800000,"8) Wrong hour of departure (28800000)"+(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC));
			assertTrue(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC==28800000,"8) Wrong hour of arrival (28800000)"+(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC));
			assertTrue(deliveries.get(0).getPosition().getId()==1,"8) Wrong node (1)"+deliveries.get(0).getPosition());
			assertTrue(deliveries.get(1).getDuration()==60,"8) Wrong duration (60)"+deliveries.get(1).getDuration());
			assertTrue(deliveries.get(1).getPosition().getId()==2,"8) Wrong node (2)"+deliveries.get(1).getPosition());
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException, report to TestDeserializer : "+e.getMessage());
		}
	}
	
	@Test
	void testCluster() {
		
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery.xml");
			
			circuitManager.setNbDeliveryMan(2);
	    	
	    	List<ArrayList<Delivery>> distribution = circuitManager.cluster();
	    	int nbDeliveries1 = distribution.get(0).size();
	    	int nbDeliveries2 = distribution.get(1).size();
	    	
	    	for (ArrayList<Delivery> deliveries : distribution) {
	    		System.out.println("");
	    		System.out.println("Livreur : ");
	    		for (Delivery del : deliveries) {
	    			System.out.println("Livraison "+del.getPosition().getId());
	    		}
	    	}
	    	
	    	assertTrue(nbDeliveries1<=nbDeliveries2+1 || nbDeliveries1>=nbDeliveries1-1, "Error, bad repartition of deliveries => "+nbDeliveries1+" vs "+nbDeliveries2);

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (ClusteringException e) {
			fail("ClusteringException"+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		}
	}
	
	void testAddDelivery() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_simple.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_simple.xml");
			
			//TODO

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (ClusteringException e) {
			fail("ClusteringException"+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		}
	}

}
