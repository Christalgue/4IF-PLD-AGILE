package main.java.utils;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Bow;
import main.java.entity.Delivery;
import main.java.entity.Repository;
import main.java.exception.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Deserializer {
	
	
	public static void loadMap(String path, Map map)throws ParserConfigurationException, SAXException, IOException, XMLException{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
		    final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document document= builder.parse(path);
		    final Element root = document.getDocumentElement();
		    if (root.getNodeName().equals("reseau")) 
		        fillMap(root, map);
		    else
		    	throw new XMLException("Document non conforme");
		}
		catch (final ParserConfigurationException e) {
		    e.printStackTrace();
		}
		catch (final SAXException e) {
		    e.printStackTrace();
		}
		catch (final IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void loadDeliveries(String path, List<Delivery> deliveriesList, Map map)throws ParserConfigurationException, SAXException, IOException, XMLException{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
		    final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document document= builder.parse(path);
		    final Element root = document.getDocumentElement();
		    if (root.getNodeName().equals("demandeDeLivraisons")) 
		        fillDeliveries(root, deliveriesList, map);
		    else
		    	throw new XMLException("Document non conforme");
		}
		catch (final ParserConfigurationException e) {
		    e.printStackTrace();
		}
		catch (final SAXException e) {
		    e.printStackTrace();
		}
		catch (final IOException e) {
		    e.printStackTrace();
		}
	}
	
	private static void fillMap(Element root, Map map) throws XMLException{
		final NodeList nodes = root.getElementsByTagName("noeud");
		final NodeList bows = root.getElementsByTagName("troncon");
		final int nbNodes = nodes.getLength();
		final int nbBows = bows.getLength();
		
		HashMap<Long, Node> tempNodeMap = new HashMap<Long, Node>();
		HashMap<Long,Set<Bow>> tempBowMap = new HashMap<Long,Set<Bow>>();
		
		for (int i = 0; i<nbNodes; i++) {
			Element element = (Element) nodes.item(i);
			tempNodeMap.put(Long.parseLong(element.getAttribute("id")), new Node(Long.parseLong(element.getAttribute("id")),Double.parseDouble(element.getAttribute("latitude")),Double.parseDouble(element.getAttribute("longitude"))));
		}
		
		for (int i = 0; i<nbBows; i++) {
			Element element = (Element) bows.item(i);
			
			long origin = Long.parseLong(element.getAttribute("origine"));
			long arrival = Long.parseLong(element.getAttribute("destination"));
			double length = Double.parseDouble(element.getAttribute("longueur"));
			if (length < 0) {
				throw new XMLException("Longueur négative");
			}
			String streetName = element.getAttribute("nomRue");
			
			if(!tempBowMap.containsKey(origin)){
				tempBowMap.put(origin, new HashSet<Bow>());
			}
			tempBowMap.get(origin).add(new Bow(tempNodeMap.get(origin),tempNodeMap.get(arrival),streetName,length));
		}
		
		map.setNodeMap(tempNodeMap);
		map.setBowMap(tempBowMap);
	}
	
	private static void fillDeliveries(Element root, List<Delivery> deliveriesList, Map map) throws XMLException{
		final NodeList repositories = root.getElementsByTagName("entrepot");
		final NodeList deliveries = root.getElementsByTagName("livraison");
		final int nbRepositories = repositories.getLength();
		final int nbDelieveries = deliveries.getLength();
		
		HashMap<Long, Node> nodes = map.getNodeMap();
		List<Delivery> tempDeliveriesList = new ArrayList<Delivery>();
		
		if(nbRepositories!=1) {
			throw new XMLException("Nombre d'entrepot non conforme");
		}
		
		Element repository = (Element) repositories.item(0);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(repository.getAttribute("heureDepart")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (nodes.get(repository.getAttribute("adresse")) == null) {
			throw new XMLException("Lieu de livraison inexistant");
		}
		tempDeliveriesList.add(new Repository(nodes.get(repository.getAttribute("adresse")),cal));
		
		for (int i = 0; i<nbDelieveries; i++) {
			Element element = (Element) deliveries.item(i);
			tempDeliveriesList.add(new Delivery(nodes.get(element.getAttribute("adresse")),Integer.parseInt(element.getAttribute("duree"))));
		}
		
		deliveriesList = new ArrayList<Delivery>(tempDeliveriesList);
	}
	
	
	public static void main (String[] args)
	{
		try {
			Map map = new Map();
			List<Delivery> deliveries = new ArrayList<Delivery>();
			loadMap("resources/xml/petitPlan.xml",map);
			loadDeliveries("resources/xml/dl-petit-3.xml",deliveries,map);
			
			System.out.println(map.toString());
			
			for(Delivery d : deliveries){
				System.out.println(d.toString());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
