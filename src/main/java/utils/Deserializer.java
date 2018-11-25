package main.java.utils;
import java.io.File;
import java.io.IOException;
import java.util.*;

import main.java.entity.Map;
import main.java.entity.Node;
import main.java.entity.Bow;
import main.java.entity.Delivery;
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
	
	public static void loadDeliveries(String path, List<Delivery> deliveriesList)throws ParserConfigurationException, SAXException, IOException, XMLException{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
		    final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document document= builder.parse(path);
		    final Element root = document.getDocumentElement();
		    if (root.getNodeName().equals("demandeDeLivraisons")) 
		        fillDeliveries(root, deliveriesList);
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
	
	private static void fillMap(Element root, Map map){
		final NodeList nodes = root.getElementsByTagName("noeud");
		final NodeList bows = root.getElementsByTagName("troncon");
		final int nbNodes = nodes.getLength();
		final int nbBows = bows.getLength();
		
		for (int i = 0; i<nbNodes; i++) {
			Element element = (Element) nodes.item(i);
		    System.out.println(element.getNodeName());
		    System.out.println(element.getAttribute("id"));
		    System.out.println(element.getAttribute("latitude"));
		    System.out.println(element.getAttribute("longitude"));
		}
		
		for (int i = 0; i<nbBows; i++) {
			Element element = (Element) bows.item(i);
		    System.out.println(element.getNodeName());
		    System.out.println(element.getAttribute("destination"));
		    System.out.println(element.getAttribute("longueur"));
		    System.out.println(element.getAttribute("nomRue"));
		    System.out.println(element.getAttribute("origine"));
		}
	}
	
	private static void fillDeliveries(Element root, List<Delivery> deliveriesList) throws XMLException{
		final NodeList repositories = root.getElementsByTagName("entrepot");
		final NodeList deliveries = root.getElementsByTagName("livraison");
		final int nbRepositories = repositories.getLength();
		final int nbDelieveries = deliveries.getLength();
		
		if(nbRepositories!=1) {
			throw new XMLException("Nombre d'entrepot non conforme");
		}
		
		Element repository = (Element) repositories.item(0);
		System.out.println(repository.getNodeName());
	    System.out.println(repository.getAttribute("adresse"));
	    System.out.println(repository.getAttribute("heureDepart"));
		
		for (int i = 0; i<nbDelieveries; i++) {
			Element element = (Element) deliveries.item(i);
		    System.out.println(element.getNodeName());
		    System.out.println(element.getAttribute("adresse"));
		    System.out.println(element.getAttribute("duree"));
		}
	}
	
	public static void main (String[] args)
	{
		try {
			loadDeliveries("resources/xml/dl-petit-3.xml",null);
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
