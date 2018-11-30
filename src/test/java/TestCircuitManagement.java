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
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.entity.Node;
import main.java.exception.ClusteringException;
import main.java.exception.DeliveryListNotCharged;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;
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
			assertTrue(first.getLength()==2.0,"wrong length (2.0) : "+first.getLength());
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
			assertTrue(deliveries.get(0).getClass().getName().contains("main.java.entity.Repository"),"First delivery is not repository");
			assertTrue(deliveries.get(0).getDuration()==0,"Wrong duration (0)"+deliveries.get(0).getDuration());
			assertTrue(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC==28800000,"Wrong hour of departure (28800000)"+(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC));
			assertTrue(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC==28800000,"rong hour of arrival (28800000)"+(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC));
			assertTrue(deliveries.get(0).getPosition().getId()==1,"Wrong node (1)"+deliveries.get(0).getPosition().getId());
			assertTrue(deliveries.get(1).getDuration()==60,"Wrong duration (60)"+deliveries.get(1).getDuration());
			assertTrue(deliveries.get(1).getPosition().getId()==2,"Wrong node (2)"+deliveries.get(1).getPosition());
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
	
	@Test
	void testCalculateCircuits() {
		
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_simple.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_simple.xml");
			circuitManager.calculateCircuits(1, false);
			
			//Assert new circuit has been correctly calculated
			String s12 = "Route :\n1 => 2 (2.0)";
			String s21 = "Route :\n2 => 1 (1.0)";
			
			Circuit circuit = circuitManager.getCircuitsList().get(0);
			assertTrue(circuit.getPath().get(0).toString().contains(s12),"Error, expected : {"+s12+"}, got : "+circuit.getPath().get(0).toString());
			assertTrue(circuit.getPath().get(1).toString().contains(s21),"Error, expected : {"+s21+"}, got : "+circuit.getPath().get(1).toString());

		} catch (LoadMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotChargedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeliveryListNotCharged e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClusteringException e) {
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
	
	@Test
	void testAddDelivery() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_add.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_add.xml");
			circuitManager.calculateCircuits(1, false);
			
			Node newDelivery = circuitManager.getCurrentMap().getNodeMap().get((long)3);
			Node previousDelivery = circuitManager.getDeliveryList().get(1).getPosition();
			circuitManager.addDelivery(newDelivery, 20, previousDelivery);
			
			//Assert new delivery has been added to the list
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			assertTrue(deliveries.size()==3, "delivery has not been added to the list (expected 3, got "+deliveries.size()+")");
			assertTrue(deliveries.get(0).toString().contains("Delivery [position=0, duration=0]"),"Error, expected : {Delivery [position=0, duration=0]}, got : "+deliveries.get(0).toString());
			assertTrue(deliveries.get(1).toString().contains("Delivery [position=1, duration=60]"),"Error, expected : {Delivery [position=1, duration=60]}, got : "+deliveries.get(1).toString());
			assertTrue(deliveries.get(2).toString().contains("Delivery [position=3, duration=20]"),"Error, expected : {Delivery [position=3, duration=20]}, got : "+deliveries.get(2).toString());
			
			//Assert new circuit has been correctly calculated
			String s01 = "Route :\n0 => 1 (1.0)";
			String s13 = "Route :\n1 => 2 (1.0)\n2 => 3 (1.0)";
			String s30 = "Route :\n3 => 0 (1.0)";
			
			Circuit circuit = circuitManager.getCircuitsList().get(0);
			assertTrue(circuit.getPath().size()==3, "AtomicPath has not been added to the list (expected 3, got "+circuit.getPath().size()+")");
			assertTrue(circuit.getPath().get(0).toString().contains(s01),"Error, expected : {"+s01+"}, got : "+circuit.getPath().get(0).toString());
			assertTrue(circuit.getPath().get(1).toString().contains(s13),"Error, expected : {"+s13+"}, got : "+circuit.getPath().get(1).toString());
			assertTrue(circuit.getPath().get(2).toString().contains(s30),"Error, expected : {"+s30+"}, got : "+circuit.getPath().get(2).toString());

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		} catch (MapNotChargedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeliveryListNotCharged e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClusteringException e) {
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
