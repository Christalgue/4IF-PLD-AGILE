package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.java.entity.Bow;
import main.java.entity.Delivery;
import main.java.entity.Map;
import main.java.entity.Node;
import main.java.exception.LoadMapException;
import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestDeserializer {

	@Test
	/**
	 * 1) The file does not exist
	 * 2) The file is not valid
	 * 3) The file is empty
	 * 4) The node of a bow does not exist
	 * 5) The length of a bow is negative
	 * 6) Duplicate bow detected
	 * 7) Everything is good
	 */
	void testLoadMap() {
		Map map = new Map();
		
		//1
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/bonjour.xml", map);
			fail("1) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("1) Parser Exception");
		} catch (SAXException e) {
			fail("1) SAX Exception");
		} catch (IOException e) {
		} catch (XMLException e) {
			fail("1) XML Exception");
		}
		
		//2
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_non_conforme.xml", map);
			fail("2) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("2) Parser Exception");
		} catch (SAXException e) {
			fail("2) SAX Exception");
		} catch (IOException e) {
			fail("2) IO Exception");
		} catch (XMLException e) {
			assertTrue(e.getMessage().contains("The file is not valid"),"2) Wrong XMLException"+e.getMessage());
		}
		
		//3
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_vide.xml", map);
			fail("3) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("3) Parser Exception");
		} catch (SAXException e) {
			fail("3) SAX Exception");
		} catch (IOException e) {
			fail("3) IO Exception");
		} catch (XMLException e) {
			assertTrue(e.getMessage().contains("The file is empty"),"3) Wrong XMLException"+e.getMessage());
		}
		
		//4
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_noeud_troncon_inexistant.xml", map);
			fail("4) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("4) Parser Exception");
		} catch (SAXException e) {
			fail("4) SAX Exception");
		} catch (IOException e) {
			fail("4) IO Exception");
		} catch (XMLException e) {
			assertTrue(e.getMessage().contains("The node of a bow does not exist"),"4) Wrong XMLException"+e.getMessage());
		}
		
		//5
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_longueur_negative.xml", map);
			fail("5) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("5) Parser Exception");
		} catch (SAXException e) {
			fail("5) SAX Exception");
		} catch (IOException e) {
			fail("5) IO Exception");
		} catch (XMLException e) {
			assertTrue(e.getMessage().contains("The length of a bow is negative"),"5) Wrong XMLException"+e.getMessage());
		}
		
		//6
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_troncon_doublon.xml", map);
			fail("6) No exception thrown");
		} catch (ParserConfigurationException e) {
			fail("6) Parser Exception");
		} catch (SAXException e) {
			fail("6) SAX Exception");
		} catch (IOException e) {
			fail("6) IO Exception");
		} catch (XMLException e) {
			assertTrue(e.getMessage().contains("Duplicate bow detected"),"5) Wrong XMLException"+e.getMessage());
		}
		
		//7
		try {
			Deserializer.loadMap("resources/tests/Deserializer/xml/plan_conforme.xml", map);
		} catch (ParserConfigurationException e) {
			fail("7) Parser Exception");
		} catch (SAXException e) {
			fail("7) SAX Exception");
		} catch (IOException e) {
			fail("7) IO Exception");
		} catch (XMLException e) {
			fail("7)"+e.getMessage());
		}
		assertTrue(map.getNodeMap().containsKey((long)1),"7) Nodes do not contain key 1");
		assertTrue(map.getNodeMap().containsKey((long)2),"7) Nodes do not contain key 2");
		assertTrue(map.getNodeMap().get((long)1).getId()==1,"7) wrong ID (1) : "+map.getNodeMap().get((long)1).getId());
		assertTrue(map.getNodeMap().get((long)1).getLatitude()==45.75406,"7) wrong latitude (45.75406) : "+map.getNodeMap().get((long)1).getLatitude());
		assertTrue(map.getNodeMap().get((long)1).getLongitude()==4.857418,"7) wrong longitude (4.857418) : "+map.getNodeMap().get((long)1).getLongitude());
		assertTrue(map.getNodeMap().get((long)2).getId()==2,"7) wrong ID (2) : "+map.getNodeMap().get((long)2).getId());
		assertTrue(map.getNodeMap().get((long)2).getLatitude()==45.750404,"7) wrong latitude (45.750404) : "+map.getNodeMap().get((long)2).getLatitude());
		assertTrue(map.getNodeMap().get((long)2).getLongitude()==4.8744674,"7) wrong longitude (4.8744674) : "+map.getNodeMap().get((long)2).getLongitude());
		assertTrue(map.getBowMap().containsKey((long)1),"7) Bows do not contain key");
		Bow first = map.getBowMap().get((long)1).iterator().next();
		assertTrue(first.getStartNode().getId()==1,"7) wrong start node (1) : "+first.getStartNode().getId());
		assertTrue(first.getEndNode().getId()==2,"7) wrong end node (2) : "+first.getEndNode().getId());
		assertTrue(first.getLength()==79.801414,"7) wrong length (79.801414) : "+first.getLength());
		assertTrue(first.getStreetName().contains("Rue Édouard Aynard"),"7) wrong street name (Rue Édouard Aynard) : "+first.getStreetName());
		
	}

	@Test
	/**
	 * 1) The file does not exist
	 * 2) The file is not valid
	 * 3) The file has an incorrect number of repository
	 * 4) The node of a delivery doesn't match to the map
	 * 5) The duration of a delivery is negative
	 * 6) Duplicate delivery detected
	 * 7) Hour of departure has not the correct format
	 * 8) Everything is good
	 */
	void testLoadDeliveries() {
		List<Delivery> deliveries;
		Map map;
		try {
			map = new Map("resources/tests/Deserializer/xml/plan_conforme2.xml");
		
			//1
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/bonjour.xml", map));
				fail("1) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("1) Parser Exception");
			} catch (SAXException e) {
				fail("1) SAX Exception");
			} catch (IOException e) {
			} catch (XMLException e) {
				fail("1) XML Exception");
			}
			
			//2
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_non_conforme.xml", map));
				fail("2) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("2) Parser Exception");
			} catch (SAXException e) {
				fail("2) SAX Exception");
			} catch (IOException e) {
				fail("2) IO Exception");
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("The file is not valid"),"2) Wrong XMLException"+e.getMessage());
			}
			
			//3
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_mauvais_nombre_entrepot.xml", map));
				fail("3) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("3) Parser Exception");
			} catch (SAXException e) {
				fail("3) SAX Exception");
			} catch (IOException e) {
				fail("3) IO Exception");
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("Number of repository not valid"),"3) Wrong XMLException : "+e.getMessage());
			}
			
			//4
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_noeud_inexistant.xml", map));
				fail("4) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("4) Parser Exception");
			} catch (SAXException e) {
				fail("4) SAX Exception");
			} catch (IOException e) {
				fail("4) IO Exception");
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("Delivery place does not exist"),"4) Wrong XMLException : "+e.getMessage());
			}
			
			//5
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_duree_negative.xml", map));
				fail("5) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("5) Parser Exception");
			} catch (SAXException e) {
				fail("5) SAX Exception");
			} catch (IOException e) {
				fail("5) IO Exception");
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("A duration is negative"),"5) Wrong XMLException : "+e.getMessage());
			}
			
			//6
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_doublon.xml", map));
				fail("6) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("6) Parser Exception");
			} catch (SAXException e) {
				fail("6) SAX Exception");
			} catch (IOException e) {
				fail("6) IO Exception");
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("Duplicate address detected"),"6) Wrong XMLException : "+e.getMessage());
			}
			
			//7
			try {
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_mauvais_format_heure.xml", map));
				fail("7) No exception thrown");
			} catch (ParserConfigurationException e) {
				fail("7) Parser Exception");
			} catch (SAXException e) {
				fail("7) SAX Exception");
			} catch (IOException e) {
				e.printStackTrace();
				fail("7) IO Exception "+e.getMessage());
			} catch (XMLException e) {
				assertTrue(e.getMessage().contains("Wrong format for hour of departure"),"7) Wrong XMLException : "+e.getMessage());
			}
			
			//8
			try {
				int UTC = TimeZone.getDefault().getRawOffset();
				deliveries = new ArrayList<Delivery>(Deserializer.loadDeliveries("resources/tests/Deserializer/xml/delivery_conforme.xml", map));
				assertTrue(deliveries.get(0).getClass().getName().contains("main.java.entity.Repository"),"8) First delivery is not repository");
				assertTrue(deliveries.get(0).getDuration()==0,"8) Wrong duration (0)"+deliveries.get(0).getDuration());
				assertTrue(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC==28800000,"8) Wrong hour of departure (28800000)"+(deliveries.get(0).getHourOfArrival().getTimeInMillis()+UTC));
				assertTrue(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC==28800000,"8) Wrong hour of arrival (28800000)"+(deliveries.get(0).getHourOfDeparture().getTimeInMillis()+UTC));
				assertTrue(deliveries.get(0).getPosition().getId()==1,"8) Wrong node (1)"+deliveries.get(0).getPosition());
				assertTrue(deliveries.get(1).getDuration()==60,"8) Wrong duration (60)"+deliveries.get(1).getDuration());
				assertTrue(deliveries.get(1).getPosition().getId()==2,"8) Wrong node (2)"+deliveries.get(1).getPosition());
			} catch (ParserConfigurationException e) {
				fail("8) Parser Exception");
			} catch (SAXException e) {
				fail("8) SAX Exception");
			} catch (IOException e) {
				fail("8) IO Exception");
			} catch (XMLException e) {
				fail("8) "+e.getMessage());
			}
			
			
			
		} catch (LoadMapException e1) {
			e1.printStackTrace();
		}
	}

}
