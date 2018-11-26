package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import main.java.exception.XMLException;
import main.java.utils.Deserializer;

class TestDeserializer {

	@Test
	/**
	 * 1) Le document n'existe pas
	 * 2) Fichier non conforme
	 * 3) Fichier vide
	 * 4) Noeud d'un troncon n'existe pas
	 * 5) Longueur negative
	 * 6) Tout fonctionne
	 */
	void testLoadMap() {
		//1
		try {
			Deserializer.loadMap("resources/tests/xml/bonjour.xml", null);
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
			Deserializer.loadMap("resources/tests/xml/plan_non_conforme.xml", null);
		} catch (ParserConfigurationException e) {
			fail("2) Parser Exception");
		} catch (SAXException e) {
			fail("2) SAX Exception");
		} catch (IOException e) {
			fail("2) IO Exception");
		} catch (XMLException e) {
			if(!e.getMessage().equals("Document non conforme"))
			{
				fail("2)"+e.getMessage());
			}
		}
		
		//3
		try {
			Deserializer.loadMap("resources/tests/xml/plan_vide.xml", null);
		} catch (ParserConfigurationException e) {
			fail("3) Parser Exception");
		} catch (SAXException e) {
			fail("3) SAX Exception");
		} catch (IOException e) {
			fail("3) IO Exception");
		} catch (XMLException e) {
			if(!e.getMessage().equals("Fichier vide"))
			{
				fail("3)"+e.getMessage());
			}
		}
		
		//4
		try {
			Deserializer.loadMap("resources/tests/xml/plan_noeud_troncon_inexistant.xml", null);
		} catch (ParserConfigurationException e) {
			fail("4) Parser Exception");
		} catch (SAXException e) {
			fail("4) SAX Exception");
		} catch (IOException e) {
			fail("4) IO Exception");
		} catch (XMLException e) {
			if(!e.getMessage().equals("Noeud d'un troncon inexistant"))
			{
				fail("4)"+e.getMessage());
			}
		}
		
		//5
		try {
			Deserializer.loadMap("resources/tests/xml/plan_longueur_negative.xml", null);
		} catch (ParserConfigurationException e) {
			fail("5) Parser Exception");
		} catch (SAXException e) {
			fail("5) SAX Exception");
		} catch (IOException e) {
			fail("5) IO Exception");
		} catch (XMLException e) {
			if(!e.getMessage().equals("Longueur negative"))
			{
				fail("5)"+e.getMessage());
			}
		}
		
		//6
		try {
			Deserializer.loadMap("resources/tests/xml/plan_longueur_negative.xml", null);
		} catch (ParserConfigurationException e) {
			fail("6) Parser Exception");
		} catch (SAXException e) {
			fail("6) SAX Exception");
		} catch (IOException e) {
			fail("6) IO Exception");
		} catch (XMLException e) {
			fail("6)"+e.getMessage());
		}
	}

	@Test
	void testLoadDeliveries() {
		fail("Not yet implemented");
	}

}
