package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;


import org.junit.jupiter.api.Test;

import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.entity.Node;
import main.java.exception.ClusteringException;
import main.java.exception.DijkstraException;
import main.java.exception.LoadDeliveryException;
import main.java.exception.LoadMapException;
import main.java.exception.ManagementException;
import main.java.exception.MapNotChargedException;
import main.java.exception.NoRepositoryException;
import main.java.exception.TSPLimitTimeReachedException;

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
			assertTrue(map.getNodeMap().get((long)3).getId()==3,"wrong ID (3) : "+map.getNodeMap().get((long)3).getId());
			assertTrue(map.getNodeMap().get((long)3).getLatitude()==45.76000,"wrong latitude (45.76000) : "+map.getNodeMap().get((long)3).getLatitude());
			assertTrue(map.getNodeMap().get((long)3).getLongitude()==4.89,"wrong longitude (4.89) : "+map.getNodeMap().get((long)3).getLongitude());
			assertTrue(map.getBowMap().containsKey((long)1),"Bows do not contain key 1");
			assertTrue(map.getBowMap().containsKey((long)2),"Bows do not contain key 2");
			assertTrue(map.getBowMap().containsKey((long)3),"Bows do not contain key 3");
			
			for(Bow tempBow : map.getBowMap().get((long)1))
			{
				if(tempBow.getEndNode().getId()==3){
					assertTrue(tempBow.getStartNode().getId()==1,"wrong start node (1) : "+tempBow.getStartNode().getId());
					assertTrue(tempBow.getLength()==1.0,"wrong length (1.0) : "+tempBow.getLength());
					assertTrue(tempBow.getStreetName().contains("Rue machin"),"wrong street name (Rue machin) : "+tempBow.getStreetName());
				}
				
				else if(tempBow.getEndNode().getId()==2){
					assertTrue(tempBow.getStartNode().getId()==1,"wrong start node (1) : "+tempBow.getStartNode().getId());
					assertTrue(tempBow.getLength()==2.0,"wrong length (2.0) : "+tempBow.getLength());
					assertTrue(tempBow.getStreetName().contains("Rue Edouard Aynard"),"wrong street name (Rue Edouard Aynard) : "+tempBow.getStreetName());
				}
				
				else {
					fail("wrong end node for bow 1 (2,3) : "+tempBow.getEndNode().getId());
				}
			}
			
			for(Bow tempBow : map.getBowMap().get((long)2))
			{
				if(tempBow.getEndNode().getId()==3){
					assertTrue(tempBow.getStartNode().getId()==2,"wrong start node (2) : "+tempBow.getStartNode().getId());
					assertTrue(tempBow.getLength()==5.0,"wrong length (5.0) : "+tempBow.getLength());
					assertTrue(tempBow.getStreetName().contains("Avenue Polygon"),"wrong street name (Avenue Polygon) : "+tempBow.getStreetName());
				}
				
				else if(tempBow.getEndNode().getId()==1){
					assertTrue(tempBow.getStartNode().getId()==2,"wrong start node (2) : "+tempBow.getStartNode().getId());
					assertTrue(tempBow.getLength()==1.0,"wrong length (1.0) : "+tempBow.getLength());
					assertTrue(tempBow.getStreetName().contains("Rue Edouard Aynard"),"wrong street name (Rue Edouard Aynard) : "+tempBow.getStreetName());
				}
				
				else {
					fail("wrong end node for bow 2 (2,3) : "+tempBow.getEndNode().getId());
				}
			}
			
			Bow tempBow = map.getBowMap().get((long)3).iterator().next();
			assertTrue(tempBow.getStartNode().getId()==3,"wrong start node (3) : "+tempBow.getStartNode().getId());
			assertTrue(tempBow.getEndNode().getId()==1,"wrong end node (1) : "+tempBow.getEndNode().getId());
			assertTrue(tempBow.getLength()==1.0,"wrong length (1.0) : "+tempBow.getLength());
			assertTrue(tempBow.getStreetName().contains("Rue machin"),"wrong street name (Rue machin) : "+tempBow.getStreetName());
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
	    	
	    	List<Long> positions1 = new ArrayList<Long>();
	    	positions1.add((long)0);
	    	positions1.add((long)3);
	    	positions1.add((long)8);
	    	positions1.add((long)9);
	    	
	    	List<Long> positions2 = new ArrayList<Long>();
	    	positions2.add((long)0);
	    	positions2.add((long)5);
	    	positions2.add((long)10);
	    	
	    	assertTrue(nbDeliveries1<=nbDeliveries2+1 || nbDeliveries1>=nbDeliveries1-1, "Error, bad repartition of deliveries => "+nbDeliveries1+" vs "+nbDeliveries2);
	    	
	    	ArrayList<Delivery> deliveries = distribution.get(0);
	    	if(deliveries.size()==4) {
		    	assertTrue(positions1.contains(deliveries.get(0).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(0).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(1).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(1).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(2).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(2).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(3).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(3).getPosition().getId());
		    	
		    	deliveries = distribution.get(1);
		    	assertTrue(positions2.contains(deliveries.get(0).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(0).getPosition().getId());
		    	assertTrue(positions2.contains(deliveries.get(1).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(1).getPosition().getId());
		    	assertTrue(positions2.contains(deliveries.get(2).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(2).getPosition().getId());
	    	}
	    	else {
	    		assertTrue(positions2.contains(deliveries.get(0).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(0).getPosition().getId());
		    	assertTrue(positions2.contains(deliveries.get(1).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(1).getPosition().getId());
		    	assertTrue(positions2.contains(deliveries.get(2).getPosition().getId()),"Error, delivery 2 should contains {0,5,10}, got :"+deliveries.get(2).getPosition().getId());
		    	
		    	deliveries = distribution.get(1);
		    	assertTrue(positions1.contains(deliveries.get(0).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(0).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(1).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(1).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(2).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(2).getPosition().getId());
		    	assertTrue(positions1.contains(deliveries.get(3).getPosition().getId()),"Error, delivery 1 should contains {0,3,8,9}, got :"+deliveries.get(3).getPosition().getId());
	    	}

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (ClusteringException e) {
			fail("ClusteringException"+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		}
	}
	
	@Test
	void checkNodeInDeliveryList() {
		try {
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery.xml");
			
			Node inList = circuitManager.getCurrentMap().getNodeMap().get((long)5);
			Node notInList = circuitManager.getCurrentMap().getNodeMap().get((long)7);
			
			assertTrue(circuitManager.checkNodeInDeliveryList(inList),"Error, expected True for the delivery 5");
			assertFalse(circuitManager.checkNodeInDeliveryList(notInList),"Error, expected False for the delivery 7");
			

		} catch (LoadMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String s13 = "Route :\n1 => 3 (1.0)";
			String s32 = "Route :\n3 => 1 (1.0)\n1 => 2 (2.0)";
			String s21 = "Route :\n2 => 1 (1.0)";
			
			Circuit circuit = circuitManager.getCircuitsList().get(0);
			assertTrue(circuit.getPath().size()==3,"Error, size of paths is incorrect, expected : 3 got : "+circuit.getPath().size());
			assertTrue(circuit.getPath().get(0).toString().contains(s13),"Error, expected : {"+s13+"}, got : "+circuit.getPath().get(0).toString());
			assertTrue(circuit.getPath().get(1).toString().contains(s32),"Error, expected : {"+s32+"}, got : "+circuit.getPath().get(1).toString());
			assertTrue(circuit.getPath().get(2).toString().contains(s21),"Error, expected : {"+s21+"}, got : "+circuit.getPath().get(2).toString());

		} catch (LoadMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoadDeliveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotChargedException e) {
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
			//Trying to put a delivery at the beginning or in the middle
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_add.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_add.xml");
			circuitManager.calculateCircuits(1, false);
			
			Node newDelivery = circuitManager.getCurrentMap().getNodeMap().get((long)3);
			Node previousDelivery = circuitManager.getDeliveryList().get(0).getPosition();
			circuitManager.addDelivery(newDelivery, 20, previousDelivery);
			
			//Assert new delivery has been added to the list
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			assertTrue(deliveries.size()==3, "delivery has not been added to the list (expected 3, got "+deliveries.size()+")");
			assertTrue(deliveries.get(0).toString().contains("Delivery [position=0, duration=0]"),"Error, expected : {Delivery [position=0, duration=0]}, got : "+deliveries.get(0).toString());
			assertTrue(deliveries.get(1).toString().contains("Delivery [position=1, duration=60]"),"Error, expected : {Delivery [position=1, duration=60]}, got : "+deliveries.get(1).toString());
			assertTrue(deliveries.get(2).toString().contains("Delivery [position=3, duration=20]"),"Error, expected : {Delivery [position=3, duration=20]}, got : "+deliveries.get(2).toString());
			
			//Assert new circuit has been correctly calculated
			String s01 = "Route :\n0 => 3 (2.0)";
			String s13 = "Route :\n3 => 0 (1.0)\n0 => 1 (1.0)";
			String s30 = "Route :\n1 => 0 (1.0)";
			
			Circuit circuit = circuitManager.getCircuitsList().get(0);
			assertTrue(circuit.getPath().size()==3, "AtomicPath has not been added to the list (expected 3, got "+circuit.getPath().size()+")");
			assertTrue(circuit.getPath().get(0).toString().contains(s01),"Error, expected : {"+s01+"}, got : "+circuit.getPath().get(0).toString());
			assertTrue(circuit.getPath().get(1).toString().contains(s13),"Error, expected : {"+s13+"}, got : "+circuit.getPath().get(1).toString());
			assertTrue(circuit.getPath().get(2).toString().contains(s30),"Error, expected : {"+s30+"}, got : "+circuit.getPath().get(2).toString());
			
			
			//Trying to put a delivery at the end
			circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_add.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_add.xml");
			circuitManager.calculateCircuits(1, false);
			
			newDelivery = circuitManager.getCurrentMap().getNodeMap().get((long)3);
			previousDelivery = circuitManager.getDeliveryList().get(1).getPosition();
			circuitManager.addDelivery(newDelivery, 20, previousDelivery);
			
			//Assert new delivery has been added to the list
			deliveries = circuitManager.getDeliveryList();
			assertTrue(deliveries.size()==3, "delivery has not been added to the list (expected 3, got "+deliveries.size()+")");
			assertTrue(deliveries.get(0).toString().contains("Delivery [position=0, duration=0]"),"Error, expected : {Delivery [position=0, duration=0]}, got : "+deliveries.get(0).toString());
			assertTrue(deliveries.get(1).toString().contains("Delivery [position=1, duration=60]"),"Error, expected : {Delivery [position=1, duration=60]}, got : "+deliveries.get(1).toString());
			assertTrue(deliveries.get(2).toString().contains("Delivery [position=3, duration=20]"),"Error, expected : {Delivery [position=3, duration=20]}, got : "+deliveries.get(2).toString());
			
			//Assert new circuit has been correctly calculated
			s01 = "Route :\n0 => 1 (1.0)";
			s13 = "Route :\n1 => 2 (1.0)\n2 => 3 (1.0)";
			s30 = "Route :\n3 => 0 (1.0)";
			
			circuit = circuitManager.getCircuitsList().get(0);
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
	void testRemoveDelivery() {
		try {
			//Trying to remove a delivery
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_add.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_remove.xml");
			circuitManager.calculateCircuits(1, false);
			
			Node oldDelivery = circuitManager.getCurrentMap().getNodeMap().get((long)3);
			circuitManager.removeDelivery(oldDelivery);
			
			//Assert new delivery has been removed from the list
			List<Delivery> deliveries = circuitManager.getDeliveryList();
			assertTrue(deliveries.size()==2, "delivery has not been removed from the list (expected 2, got "+deliveries.size()+")");
			assertTrue(deliveries.get(0).toString().contains("Delivery [position=0, duration=0]"),"Error, expected : {Delivery [position=0, duration=0]}, got : "+deliveries.get(0).toString());
			assertTrue(deliveries.get(1).toString().contains("Delivery [position=1, duration=60]"),"Error, expected : {Delivery [position=1, duration=60]}, got : "+deliveries.get(1).toString());
			
			//Assert new circuit has been correctly calculated
			String s01 = "Route :\n0 => 1 (1.0)";
			String s13 = "Route :\n1 => 0 (1.0)";
			
			Circuit circuit = circuitManager.getCircuitsList().get(0);
			assertTrue(circuit.getPath().size()==2, "AtomicPath has not been removed from the list (expected 2, got "+circuit.getPath().size()+")");
			assertTrue(circuit.getPath().get(0).toString().contains(s01),"Error, expected : {"+s01+"}, got : "+circuit.getPath().get(0).toString());
			assertTrue(circuit.getPath().get(1).toString().contains(s13),"Error, expected : {"+s13+"}, got : "+circuit.getPath().get(1).toString());
		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		} catch (MapNotChargedException e) {
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
		} catch (ManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {

			//Trying to remove the repository
			CircuitManagement circuitManager = new CircuitManagement();
			circuitManager.loadMap("resources/tests/Global/xml/plan_add.xml");
			circuitManager.loadDeliveryList("resources/tests/Global/xml/delivery_add.xml");
			circuitManager.calculateCircuits(1, false);
			
			Node oldDelivery = circuitManager.getCurrentMap().getNodeMap().get((long)0);
			circuitManager.removeDelivery(oldDelivery);
			
			fail("No error were sent for removing the repository");

		} catch (LoadMapException e) {
			fail("LoadMapException, report to TestDeserializer : "+e.getMessage());
		} catch (LoadDeliveryException e) {
			fail("LoadDeliveryException"+e.getMessage());
		} catch (MapNotChargedException e) {
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
		} catch (ManagementException e) {
			assertTrue(e.getMessage().contains("You cannot remove a repository"),"The ManagementException is not the one expected, got : "+e.getMessage());
		}
	}

}
